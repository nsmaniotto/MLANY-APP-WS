package com.mlany.app.persistence.repository.mlany.dataset;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.dataset.Dataset;

@Repository
public interface DatasetRepository extends CrudRepository<Dataset, Long>, JpaSpecificationExecutor<Dataset> {
	public List<Dataset> findAllByOrderByIdAsc();
}