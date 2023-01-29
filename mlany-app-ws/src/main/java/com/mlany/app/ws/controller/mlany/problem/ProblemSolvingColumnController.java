package com.mlany.app.ws.controller.mlany.problem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.services.mlany.problem.ProblemSolvingColumnService;
import com.mlany.app.persistence.entity.Beanables;
import com.mlany.app.ws.bean.mlany.problem.ProblemSolvingColumnBean;

@RestController
@RequestMapping("problem/solving/column")
public class ProblemSolvingColumnController {

	@Autowired
	private ProblemSolvingColumnService problemSolvingColumnService;

	@GetMapping(value = "problemSolvingId/{problemSolvingId}")
	public List<ProblemSolvingColumnBean> getColumnsByProblemSolvingId(@PathVariable Long problemSolvingId) {
		return Beanables.toBeanList(problemSolvingColumnService.getColumnsByProblemSolvingId(problemSolvingId));
	}
}
