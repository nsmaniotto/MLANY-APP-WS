package com.mlany.app.persistence.repository.mlany.problem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.problem.ProblemSolvingColumn;

@Repository
public interface ProblemSolvingColumnRepository
		extends CrudRepository<ProblemSolvingColumn, Long>, JpaSpecificationExecutor<ProblemSolvingColumn> {
	public List<ProblemSolvingColumn> findByProblemSolvingId(Long problemSolvingId);
}