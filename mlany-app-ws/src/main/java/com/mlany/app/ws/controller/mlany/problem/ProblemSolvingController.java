package com.mlany.app.ws.controller.mlany.problem;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.services.mlany.problem.ProblemSolvingService;
import com.mlany.app.persistence.entity.mlany.problem.ProblemSolving;
import com.mlany.app.ws.bean.mlany.problem.ProblemSolvingBean;

@RestController
@RequestMapping("problem/solving")
public class ProblemSolvingController {

	@Autowired
	private ProblemSolvingService problemSolvingService;

	@PostMapping(value = "create")
	public ProblemSolvingBean createProblemSolving(@RequestBody ProblemSolvingBean problemSolvingBean) {
		return problemSolvingService.createProblemSolving(problemSolvingBean).toBean();
	}

	@GetMapping(value = "{id}")
	public ProblemSolvingBean getProblem(@PathVariable Long id) {
		Optional<ProblemSolving> problemSolving = problemSolvingService.getProblemSolving(id);
		return problemSolving.isPresent() ? problemSolving.get().toBean() : null;
	}
}
