package com.mlany.app.ws.services.mlany.problem;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlany.app.persistence.entity.enumeration.common.ColumnInputOutputEnum;
import com.mlany.app.persistence.entity.enumeration.common.ColumnTypeEnum;
import com.mlany.app.persistence.entity.enumeration.model.ModelTrainingStatusEnum;
import com.mlany.app.persistence.entity.enumeration.problem.ProblemSolvingStatusEnum;
import com.mlany.app.persistence.entity.enumeration.problem.ProblemSolvingTypeEnum;
import com.mlany.app.persistence.entity.mlany.model.ModelTraining;
import com.mlany.app.persistence.entity.mlany.problem.Problem;
import com.mlany.app.persistence.entity.mlany.problem.ProblemSolving;
import com.mlany.app.persistence.entity.mlany.problem.ProblemSolvingColumn;
import com.mlany.app.persistence.repository.mlany.dataset.DatasetRepository;
import com.mlany.app.persistence.repository.mlany.problem.ProblemRepository;
import com.mlany.app.persistence.repository.mlany.problem.ProblemSolvingRepository;
import com.mlany.app.ws.bean.mlany.problem.ProblemSolvingBean;
import com.mlany.app.ws.services.mlany.model.ModelTrainingService;

@Service
public class ProblemSolvingService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int MAX_SOLVING_ATTEMPTS = 10;
	private static final long MS_TIME_BETWEEN_SOLVING_ATTEMPT = 500;

	/* =============== SERVICES =============== */

	@Autowired
	private ModelTrainingService modelTrainingService;

	@Autowired
	private ProblemSolvingColumnService problemSolvingColumnService;

	/* ============= REPOSITORIES ============= */

	@Autowired
	private DatasetRepository datasetRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private ProblemSolvingRepository problemSolvingRepository;

	@Transactional
	public ProblemSolving createProblemSolving(ProblemSolvingBean problemSolvingBean) {
		ProblemSolving problemSolving = new ProblemSolving();

		problemSolving.setProblem(problemRepository.findById(problemSolvingBean.getProblemId()).orElse(null));
		problemSolving.setDataset(datasetRepository.findById(problemSolvingBean.getDatasetId()).orElse(null));

		problemSolving.setStatus(ProblemSolvingStatusEnum.IN_PROGRESS.name());

		// Save entity to generate id
		problemSolvingRepository.save(problemSolving);

		// Save input and output columns
		problemSolvingBean.getProblemSolvingColumns().forEach(contextColumn -> {
			contextColumn.setProblemSolvingId(problemSolving.getId());
			problemSolving.getProblemSolvingColumns().add(problemSolvingColumnService.save(contextColumn));
		});

		// Save entity with associated columns
		problemSolvingRepository.save(problemSolving);

		logger.info("Initialized and saved problemSolving#{}", problemSolving.getId());

		// Start problem solving in an asynchronous way
		new Thread(() -> this.solve(problemSolving.getId(), problemSolving.getProblem(), MAX_SOLVING_ATTEMPTS)).start();

		return problemSolving;
	}

	public Optional<ProblemSolving> getProblemSolving(Long id) {
		return problemSolvingRepository.findById(id);
	}

	private void solve(Long problemSolvingId, Problem problem, int attemptsLeft) {
		logger.info("Started problemSolving#{} solving problem#{} - {}", problemSolvingId, problem.getId(),
				problem.getName());
		Optional<ProblemSolving> optProblemSolving = problemSolvingRepository.findById(problemSolvingId);

		if (optProblemSolving.isPresent()) {
			ProblemSolving problemSolving = optProblemSolving.get();
			ProblemSolvingTypeEnum problemSolvingType = getProblemSolvingType(problemSolving.getId());

			// Generate various model training instances
			List<ModelTraining> modelTrainings = modelTrainingService.generateTrainings(problemSolving,
					problemSolvingType);
			problemSolving.setModelTrainings(modelTrainings);
			problemSolvingRepository.save(problemSolving);

			// Start training
			modelTrainingService.startTraining(problemSolving.getModelTrainings());
		} else if (attemptsLeft > 0) {
			logger.info("problemSolving#{} was not found, retry {}/{}", problemSolvingId,
					MAX_SOLVING_ATTEMPTS - attemptsLeft + 1, MAX_SOLVING_ATTEMPTS);
			try {
				Thread.sleep(MS_TIME_BETWEEN_SOLVING_ATTEMPT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.solve(problemSolvingId, problem, attemptsLeft - 1);
		}
	}

	private ProblemSolvingTypeEnum getProblemSolvingType(Long problemSolvingId) {
		ProblemSolvingTypeEnum problemSolvingType = ProblemSolvingTypeEnum.MULTI_CLASSIFICATION;

		// Retrieve columns associated to given problem solving
		List<ProblemSolvingColumn> problemSolvingColumns = problemSolvingColumnService
				.getColumnsByProblemSolvingId(problemSolvingId);

		// Retrieve type from designated output column
		Optional<ProblemSolvingColumn> optionalOutputColumn = problemSolvingColumns.stream()
				.filter(problemSolvingColumn -> ColumnInputOutputEnum.OUTPUT.name()
						.equals(problemSolvingColumn.getInputOutput()))
				.findFirst();
		if (optionalOutputColumn.isPresent()) {
			// Determine problem solving type from output column type
			String outputColumnType = optionalOutputColumn.get().getType();

			ColumnTypeEnum outputColumnTypeEnum = ColumnTypeEnum.valueOf(outputColumnType);
			switch (outputColumnTypeEnum) {
			case NUMBER:
				problemSolvingType = ProblemSolvingTypeEnum.REGRESSION;
				break;
			default:
				break;
			}
		}

		return problemSolvingType;
	}

	@Transactional
	public void refreshProblemSolvingStatus(Long problemSolvingId) {
		Optional<ProblemSolving> optionalProblemSolving = problemSolvingRepository.findById(problemSolvingId);

		if (optionalProblemSolving.isPresent()) {
			ProblemSolving problemSolving = optionalProblemSolving.get();

			List<String> modelTrainingStatusList = problemSolving.getModelTrainings().stream()
					.map(ModelTraining::getStatus).toList();

			boolean statusListContainsFailed = modelTrainingStatusList.contains(ModelTrainingStatusEnum.FAILED.name());
			boolean statusListContainsInProgress = modelTrainingStatusList
					.contains(ModelTrainingStatusEnum.IN_PROGRESS.name());
			boolean statusListContainsFinished = modelTrainingStatusList
					.contains(ModelTrainingStatusEnum.FINISHED.name());

			if (statusListContainsInProgress) {
				problemSolving.setStatus(ProblemSolvingStatusEnum.IN_PROGRESS.name());
			} else if (statusListContainsFailed && statusListContainsFinished) {
				problemSolving.setStatus(ProblemSolvingStatusEnum.FINISHED.name());
			} else if (statusListContainsFailed) {
				problemSolving.setStatus(ProblemSolvingStatusEnum.FAILED.name());
			} else {
				problemSolving.setStatus(ProblemSolvingStatusEnum.FINISHED.name());
			}

			problemSolvingRepository.save(problemSolving);
			logger.info("Updated status of problemSolving#{} to {}", problemSolving.getId(),
					problemSolving.getStatus());
		} else {
			logger.error("Could not refresh status of problemSolving#{}", problemSolvingId);
		}
	}
}
