package com.mlany.app.persistence.entity.mlany.prediction;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.persistence.entity.mlany.problem.Problem;
import com.mlany.app.ws.bean.mlany.prediction.PredictionBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PREDICTION")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class Prediction extends MlanyEntity implements Beanable<PredictionBean> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROBLEM_ID", nullable = true)
	private Problem problem;

	@Column(name = "INPUT_JSON", nullable = false)
	@Lob
	private byte[] inputJson;

	@Column(name = "OUTPUT_JSON", nullable = true)
	@Lob
	private byte[] outputJson;

	@Column(name = "PREDICTION_REQUEST_DATE", nullable = true)
	private Date predictionRequestDate;

	@Column(name = "PREDICTION_RESULT_DATE", nullable = true)
	private Date predictionResultDate;

	@Override
	public PredictionBean toBean() {
		PredictionBean bean = new PredictionBean();
		bean.setId(getId());
		bean.setProblemId(getProblem() != null ? getProblem().getId() : null);
		bean.setInputJson(new String(getInputJson(), StandardCharsets.UTF_8));
		bean.setOutputJson(new String(getOutputJson(), StandardCharsets.UTF_8));
		bean.setPredictionRequestDate(getPredictionRequestDate());
		bean.setPredictionResultDate(getPredictionResultDate());
		return bean;
	}

}
