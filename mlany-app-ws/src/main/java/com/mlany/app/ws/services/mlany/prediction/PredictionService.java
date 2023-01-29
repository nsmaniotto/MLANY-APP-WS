package com.mlany.app.ws.services.mlany.prediction;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlany.app.persistence.entity.mlany.prediction.Prediction;
import com.mlany.app.persistence.entity.mlany.problem.Problem;
import com.mlany.app.persistence.repository.mlany.prediction.PredictionRepository;
import com.mlany.app.ws.bean.external.PredictionRequestBean;

@Service
public class PredictionService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private PredictionRepository predictionRepository;

	public Prediction saveFromRequest(Problem problem, PredictionRequestBean predictionRequestBean) {
		Prediction prediction = new Prediction();

		prediction.setProblem(problem);
		JSONObject jsonObject = new JSONObject(predictionRequestBean.getInput());
		prediction.setInputJson(jsonObject.toString().getBytes(StandardCharsets.UTF_16));
		prediction.setPredictionRequestDate(new Date());

		return predictionRepository.save(prediction);
	}
}
