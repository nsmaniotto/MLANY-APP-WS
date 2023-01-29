package com.mlany.app.ws.controller.mlany.problem;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.services.mlany.problem.ProblemService;
import com.mlany.app.persistence.entity.Beanables;
import com.mlany.app.persistence.entity.mlany.dataset.Dataset;
import com.mlany.app.persistence.entity.mlany.problem.Problem;
import com.mlany.app.ws.bean.mlany.dataset.DatasetBean;
import com.mlany.app.ws.bean.mlany.problem.ProblemBean;

@RestController
@RequestMapping("problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@PostMapping(value = "create")
	public Long createProblem(@RequestBody ProblemBean problemBean) {
		return problemService.createProblem(problemBean).getId();
	}

	@GetMapping(value = "{id}")
	public ProblemBean getProblem(@PathVariable Long id) {
		Optional<Problem> problem = problemService.getProblem(id);
		return problem.isPresent() ? problem.get().toBean() : null;
	}

	@GetMapping(value = "{problemId}/linkedDatasets")
	public List<DatasetBean> getLinkedDatasets(@PathVariable Long problemId) {
		List<Dataset> linkedDatasets = problemService.getLinkedDatasets(problemId);
		return Beanables.toBeanList(linkedDatasets);
	}

	@GetMapping(value = "")
	public List<ProblemBean> getAll() {
		return Beanables.toBeanList(problemService.getAll());
	}

	@GetMapping(value = "type/label")
	public List<String> getProblemTypeLabels() {
		return problemService.getProblemTypeLabels();
	}

	@PostMapping(value = "{id}/endPoint/update")
	public ProblemBean updateEndpoint(@PathVariable Long id, @RequestBody String endPoint) {
		Problem problem = problemService.updateEndpoint(id, endPoint);
		return problem != null ? problem.toBean() : null;
	}
}
