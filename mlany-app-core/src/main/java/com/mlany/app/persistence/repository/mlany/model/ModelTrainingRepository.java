package com.mlany.app.persistence.repository.mlany.model;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.model.ModelTraining;

@Repository
public interface ModelTrainingRepository
		extends CrudRepository<ModelTraining, Long>, JpaSpecificationExecutor<ModelTraining> {

}