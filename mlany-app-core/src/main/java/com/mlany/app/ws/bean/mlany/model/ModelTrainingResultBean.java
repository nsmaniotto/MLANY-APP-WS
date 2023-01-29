package com.mlany.app.ws.bean.mlany.model;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ModelTrainingResultBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Float meanScore;
	private Float standardDeviation;
	private Float trainingAccuracy;
	private Float validationAccuracy;
	private Long averagePredictionTimeMs;

	@Override
	public String toString() {
		return "ModelTrainingResultBean [id=" + id + ", meanScore=" + meanScore + ", standardDeviation="
				+ standardDeviation + ", trainingAccuracy=" + trainingAccuracy + ", validationAccuracy="
				+ validationAccuracy + ", averagePredictionTimeMs=" + averagePredictionTimeMs + "]";
	}
}