package com.mlany.app.ws.services.mlany.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlany.app.ws.services.common.filer.FilerService;
import com.mlany.app.ws.services.external.ExternalMlanyMlService;
import com.mlany.app.ws.services.mlany.problem.ProblemService;
import com.mlany.app.ws.services.mlany.problem.ProblemSolvingService;
import com.mlany.app.persistence.entity.enumeration.model.ModelFamilyEnum;
import com.mlany.app.persistence.entity.enumeration.model.ModelTrainingStatusEnum;
import com.mlany.app.persistence.entity.enumeration.problem.ProblemTypeEnum;
import com.mlany.app.persistence.entity.mlany.model.Model;
import com.mlany.app.persistence.entity.mlany.model.ModelTraining;
import com.mlany.app.persistence.entity.mlany.problem.ProblemSolving;
import com.mlany.app.persistence.repository.mlany.model.ModelRepository;
import com.mlany.app.persistence.repository.mlany.model.ModelTrainingRepository;

@Service
public class ModelTrainingService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String VECTORIZER_DIRECTORY = "vectorizer";
	public static final String VECTORIZER_FILE_NAME = "vectorizer";
	public static final String VECTORIZER_FILE_EXTENSION = "pkl";

	public static final int MAX_HANDLING_TRAINING_FAILURE_ATTEMPTS = 10;
	private static final long MS_TIME_BETWEEN_HANDLING_TRAINING_FAILURE_ATTEMPT = 500;

	public static final int MAX_HANDLING_TRAINING_SUCCESS_ATTEMPTS = 10;
	private static final long MS_TIME_BETWEEN_HANDLING_TRAINING_SUCCESS_ATTEMPT = 500;

	/* =============== SERVICES =============== */

	@Autowired
	private ExternalMlanyMlService externalMlanyMlService;

	@Autowired
	private FilerService filerService;

	@Autowired
	private ModelInstanceService modelInstanceService;

	@Autowired
	private ProblemService problemService;

	@Autowired
	private ProblemSolvingService problemSolvingService;

	/* ============= REPOSITORIES ============= */

	@Autowired
	private ModelTrainingRepository modelTrainingRepository;

	@Autowired
	private ModelRepository modelRepository;

	public Long deployModelFromTraining(Long modelTrainingId) {
		Long updatedProblemId = null;

		Optional<ModelTraining> optionalModelTraining = modelTrainingRepository.findById(modelTrainingId);

		if (optionalModelTraining.isPresent()) {
			updatedProblemId = problemService.updateDeployedModelFromTraining(
					optionalModelTraining.get().getProblemSolving().getProblem().getId(), optionalModelTraining.get());
		}

		return updatedProblemId;
	}

	public List<ModelTraining> generateTrainings(ProblemSolving problemSolving) {
		List<ModelTraining> generatedTrainings = new ArrayList<>();

		ProblemTypeEnum problemTypeEnum = ProblemTypeEnum.valueOf(problemSolving.getProblem().getType());

		switch (problemTypeEnum) {
		case MULTI_CLASSIFICATION:
			generatedTrainings.addAll(generateMultiClassClassificationTraining());
			break;
		case CLASSIFICATION:
			generatedTrainings.addAll(generateClassificationTraining());
			break;
		case REGRESSION:
			generatedTrainings.addAll(generateRegressionTrainings());
			break;
		default:
			break;
		}

		generatedTrainings.forEach(modelTraining -> {
			modelTraining.setProblemSolving(problemSolving);
			String vectorizerFileNameWithExtension = new StringBuilder().append(VECTORIZER_FILE_NAME).append(".")
					.append(VECTORIZER_FILE_EXTENSION).toString();
			modelTraining.setVectorizerFileInfo(
					filerService.generateFileInfo(VECTORIZER_DIRECTORY, vectorizerFileNameWithExtension));
			modelTrainingRepository.save(modelTraining);
		});

		return generatedTrainings;
	}

	private List<ModelTraining> generateMultiClassClassificationTraining() {
		List<ModelTraining> generatedTrainings = new ArrayList<>();

		List<Model> models = modelRepository.findByFamily(ModelFamilyEnum.MULTICLASS_CLASSIFICATION.name());

		models.stream().forEach(model -> generatedTrainings.add(generateModelTraining(model)));

		return generatedTrainings;
	}

	private List<ModelTraining> generateClassificationTraining() {
		List<ModelTraining> generatedTrainings = new ArrayList<>();
		// TODO
		logger.error("CLASSIFICATION model familly not supported by model training service");
		return generatedTrainings;
	}

	private List<ModelTraining> generateRegressionTrainings() {
		List<ModelTraining> generatedTrainings = new ArrayList<>();
		// TODO
		logger.error("REGRESSION model familly not supported by model training service");
		return generatedTrainings;
	}

	private ModelTraining generateModelTraining(Model model) {
		ModelTraining modelTraining = new ModelTraining();
		modelTraining.setModelInstance(modelInstanceService.generateModelInstance(model));
		return modelTraining;
	}

	public void startTraining(List<ModelTraining> modelTrainings) {
		modelTrainings.forEach(this::startTraining);
	}

	private void startTraining(ModelTraining modelTraining) {
		modelTraining.setStatus(ModelTrainingStatusEnum.IN_PROGRESS.name());
		externalMlanyMlService.startTraining(modelTraining);
		modelTrainingRepository.save(modelTraining);
	}

	public void handleModelTrainingFailure(Long modelTrainingId, int attemptsLeft) {
		Optional<ModelTraining> optionalModelTraining = modelTrainingRepository.findById(modelTrainingId);

		if (optionalModelTraining.isPresent()) {
			ModelTraining modelTraining = optionalModelTraining.get();
			modelTraining.setStatus(ModelTrainingStatusEnum.FAILED.name());
			modelTrainingRepository.save(modelTraining);
			problemSolvingService.refreshProblemSolvingStatus(modelTraining.getProblemSolving().getId());
			logger.info("Status for modelTraining#{} updated to {}", modelTraining.getId(), modelTraining.getStatus());
		} else if (attemptsLeft > 0) {
			logger.info("modelTraining#{} was not found, retry {}/{}", modelTrainingId,
					MAX_HANDLING_TRAINING_FAILURE_ATTEMPTS - attemptsLeft + 1, MAX_HANDLING_TRAINING_FAILURE_ATTEMPTS);
			try {
				Thread.sleep(MS_TIME_BETWEEN_HANDLING_TRAINING_FAILURE_ATTEMPT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handleModelTrainingFailure(modelTrainingId, attemptsLeft - 1);
		}
	}

	public void handleModelTrainingSuccess(Long modelTrainingId, int attemptsLeft) {
		Optional<ModelTraining> optionalModelTraining = modelTrainingRepository.findById(modelTrainingId);

		if (optionalModelTraining.isPresent()) {
			ModelTraining modelTraining = optionalModelTraining.get();
			modelTraining.setStatus(ModelTrainingStatusEnum.FINISHED.name());
			modelTrainingRepository.save(modelTraining);
			problemSolvingService.refreshProblemSolvingStatus(modelTraining.getProblemSolving().getId());
			logger.info("Status for modelTraining#{} updated to {}", modelTraining.getId(), modelTraining.getStatus());
		} else if (attemptsLeft > 0) {
			logger.info("modelTraining#{} was not found, retry {}/{}", modelTrainingId,
					MAX_HANDLING_TRAINING_SUCCESS_ATTEMPTS - attemptsLeft + 1, MAX_HANDLING_TRAINING_SUCCESS_ATTEMPTS);
			try {
				Thread.sleep(MS_TIME_BETWEEN_HANDLING_TRAINING_SUCCESS_ATTEMPT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handleModelTrainingSuccess(modelTrainingId, attemptsLeft - 1);
		}
	}
}
