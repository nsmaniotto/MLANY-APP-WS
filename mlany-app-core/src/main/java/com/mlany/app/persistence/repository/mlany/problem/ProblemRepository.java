package com.mlany.app.persistence.repository.mlany.problem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.problem.Problem;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, Long>, JpaSpecificationExecutor<Problem> {
	public List<Problem> findAllByOrderByIdAsc();

	public List<Problem> findByEndPoint(String endPoint);
}