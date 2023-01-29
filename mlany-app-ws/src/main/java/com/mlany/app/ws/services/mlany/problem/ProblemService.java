package com.mlany.app.ws.services.mlany.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlany.app.persistence.entity.enumeration.problem.ProblemTypeEnum;
import com.mlany.app.persistence.entity.mlany.dataset.Dataset;
import com.mlany.app.persistence.entity.mlany.model.ModelTraining;
import com.mlany.app.persistence.entity.mlany.problem.Problem;
import com.mlany.app.persistence.repository.mlany.problem.ProblemRepository;
import com.mlany.app.ws.bean.mlany.problem.ProblemBean;

@Service
public class ProblemService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private ProblemRepository problemRepository;

	@Transactional
	public Problem createProblem(ProblemBean problemBean) {
		Problem problem = new Problem();

		problem.setName(problemBean.getName());

		Optional<ProblemTypeEnum> problemTypeEnum = ProblemTypeEnum.findProblemTypeEnumByLabel(problemBean.getType());
		problem.setType(problemTypeEnum.isPresent() ? problemTypeEnum.get().toString() : null);

		return problemRepository.save(problem);
	}

	public Optional<Problem> getProblem(Long id) {
		return problemRepository.findById(id);
	}

	public List<Dataset> getLinkedDatasets(Long problemId) {
		List<Dataset> linkedDatasets = new ArrayList<>();

		Optional<Problem> problem = this.getProblem(problemId);

		if (problem.isPresent()) {
			linkedDatasets = problem.get().getLinkedDatasets();
		}

		return linkedDatasets;
	}

	public Iterable<Problem> getAll() {
		return problemRepository.findAllByOrderByIdAsc();
	}

	public List<String> getProblemTypeLabels() {
		return new ArrayList<>(
				Arrays.asList(ProblemTypeEnum.values()).stream().map(ProblemTypeEnum::getLabel).toList());
	}

	public Long updateDeployedModelFromTraining(Long problemId, ModelTraining modelTraining) {
		Long updatedProblemId = null;

		Optional<Problem> optionalProblem = problemRepository.findById(problemId);

		if (optionalProblem.isPresent()) {
			Problem problem = optionalProblem.get();
			problem.setDeployedModelFromTraining(modelTraining);
			updatedProblemId = problemRepository.save(problem).getId();
		}

		return updatedProblemId;
	}

	public Problem updateEndpoint(Long problemId, String endPoint) {
		Problem problem = null;

		Optional<Problem> optionalProblem = problemRepository.findById(problemId);

		if (optionalProblem.isPresent()) {
			problem = optionalProblem.get();
			problem.setEndPoint(endPoint);
			problemRepository.save(problem).getId();
		}

		return problem;
	}

	public Optional<Problem> findByEndPoint(String endPoint) {
		Optional<Problem> optionalProblem = Optional.empty();

		List<Problem> foundProblems = problemRepository.findByEndPoint(endPoint);

		if (foundProblems != null && !foundProblems.isEmpty()) {
			optionalProblem = foundProblems.stream().findFirst();
		}

		return optionalProblem;
	}
}
