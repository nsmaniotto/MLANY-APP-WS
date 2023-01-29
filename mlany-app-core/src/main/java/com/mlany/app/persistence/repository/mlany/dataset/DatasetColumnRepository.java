package com.mlany.app.persistence.repository.mlany.dataset;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.dataset.DatasetColumn;

@Repository
public interface DatasetColumnRepository
		extends CrudRepository<DatasetColumn, Long>, JpaSpecificationExecutor<DatasetColumn> {

}