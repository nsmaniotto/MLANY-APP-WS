package com.mlany.app.persistence.entity.mlany.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.bean.mlany.model.ModelTrainingResultBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODEL_TRAINING_RESULT")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class ModelTrainingResult extends MlanyEntity implements Beanable<ModelTrainingResultBean> {

	private static final long serialVersionUID = 1L;

	@Column(name = "MEAN_SCORE", precision = 10, scale = 9, nullable = false)
	private Float meanScore;

	@Column(name = "STANDARD_DEVIATION", precision = 10, scale = 9, nullable = false)
	private Float standardDeviation;

	@Column(name = "TRAINING_ACCURACY", precision = 10, scale = 9, nullable = false)
	private Float trainingAccuracy;

	@Column(name = "VALIDATION_ACCURACY", precision = 10, scale = 9, nullable = false)
	private Float validationAccuracy;

	@Column(name = "AVERAGE_PREDICTION_TIME_MS", nullable = false)
	private Long averagePredictionTimeMs;

	@Override
	public ModelTrainingResultBean toBean() {
		ModelTrainingResultBean bean = new ModelTrainingResultBean();
		bean.setId(getId());
		bean.setMeanScore(getMeanScore());
		bean.setStandardDeviation(getStandardDeviation());
		bean.setTrainingAccuracy(getTrainingAccuracy());
		bean.setValidationAccuracy(getValidationAccuracy());
		bean.setAveragePredictionTimeMs(getAveragePredictionTimeMs());
		return bean;
	}

}
