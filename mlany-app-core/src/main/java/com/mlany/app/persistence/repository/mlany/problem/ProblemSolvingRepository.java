package com.mlany.app.persistence.repository.mlany.problem;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.problem.ProblemSolving;

@Repository
public interface ProblemSolvingRepository
		extends CrudRepository<ProblemSolving, Long>, JpaSpecificationExecutor<ProblemSolving> {

}