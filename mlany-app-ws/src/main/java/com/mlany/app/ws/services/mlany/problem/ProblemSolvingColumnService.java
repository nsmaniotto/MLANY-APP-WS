package com.mlany.app.ws.services.mlany.problem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlany.app.persistence.entity.mlany.problem.ProblemSolvingColumn;
import com.mlany.app.persistence.repository.mlany.problem.ProblemSolvingColumnRepository;
import com.mlany.app.persistence.repository.mlany.problem.ProblemSolvingRepository;
import com.mlany.app.ws.bean.mlany.common.ColumnRawBean;
import com.mlany.app.ws.bean.mlany.problem.ProblemSolvingColumnBean;

@Service
public class ProblemSolvingColumnService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private ProblemSolvingColumnRepository problemSolvingColumnRepository;

	@Autowired
	private ProblemSolvingRepository problemSolvingRepository;

	@Transactional
	public ProblemSolvingColumn save(ProblemSolvingColumnBean problemSolvingColumnBean) {
		ProblemSolvingColumn problemSolvingColumn = new ProblemSolvingColumn();

		problemSolvingColumn.setProblemSolving(
				problemSolvingRepository.findById(problemSolvingColumnBean.getProblemSolvingId()).orElse(null));
		problemSolvingColumn.setName(problemSolvingColumnBean.getName());
		problemSolvingColumn.setType(problemSolvingColumnBean.getType());
		problemSolvingColumn.setInputOutput(problemSolvingColumnBean.getInputOutput());

		return problemSolvingColumnRepository.save(problemSolvingColumn);
	}

	@Transactional
	public List<ColumnRawBean> getColumnRawBeansByProblemSolvingId(Long problemSolvingId) {
		List<ColumnRawBean> columnRawBeans = new ArrayList<>();

		List<ProblemSolvingColumn> problemSolvingColumns = problemSolvingColumnRepository
				.findByProblemSolvingId(problemSolvingId);

		columnRawBeans.addAll(problemSolvingColumns.stream().map(ProblemSolvingColumn::toRawBean).toList());

		return columnRawBeans;
	}

	public List<ProblemSolvingColumn> getColumnsByProblemSolvingId(Long problemSolvingId) {
		return problemSolvingColumnRepository.findByProblemSolvingId(problemSolvingId);
	}
}
