package com.mlany.app.persistence.repository.mlany.prediction;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.prediction.Prediction;

@Repository
public interface PredictionRepository extends CrudRepository<Prediction, Long>, JpaSpecificationExecutor<Prediction> {

}