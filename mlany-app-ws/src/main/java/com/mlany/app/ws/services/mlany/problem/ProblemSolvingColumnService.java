package com.mlany.app.ws.services.mlany.problem;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlany.app.persistence.entity.mlany.dataset.DatasetColumn;
import com.mlany.app.persistence.entity.mlany.problem.ProblemSolving;
import com.mlany.app.persistence.entity.mlany.problem.ProblemSolvingColumn;
import com.mlany.app.persistence.repository.mlany.problem.ProblemSolvingColumnRepository;
import com.mlany.app.ws.bean.mlany.common.ColumnRawBean;

@Service
public class ProblemSolvingColumnService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private ProblemSolvingColumnRepository problemSolvingColumnRepository;

	@Transactional
	public ProblemSolvingColumn createCopyFromDatasetColumn(ProblemSolving problemSolving,
			DatasetColumn datasetColumn) {
		ProblemSolvingColumn problemSolvingColumn = new ProblemSolvingColumn();

		problemSolvingColumn.setProblemSolving(problemSolving);
		problemSolvingColumn.setName(datasetColumn.getName());
		problemSolvingColumn.setType(datasetColumn.getType());
		problemSolvingColumn.setInputOutput(datasetColumn.getInputOutput());

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
