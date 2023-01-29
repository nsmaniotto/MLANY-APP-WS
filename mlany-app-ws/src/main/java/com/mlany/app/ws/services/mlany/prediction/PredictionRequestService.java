package com.mlany.app.ws.services.mlany.prediction;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mlany.app.ws.services.external.ExternalMlanyMlService;
import com.mlany.app.ws.services.mlany.problem.ProblemService;
import com.mlany.app.persistence.entity.mlany.prediction.Prediction;
import com.mlany.app.persistence.entity.mlany.problem.Problem;
import com.mlany.app.persistence.repository.mlany.prediction.PredictionRepository;
import com.mlany.app.ws.bean.external.PredictionRequestBean;
import com.mlany.app.ws.bean.external.PredictionResultBean;

@Service
public class PredictionRequestService {

	/* =============== SERVICES =============== */

	@Autowired
	private ExternalMlanyMlService externalMlanyMlService;

	@Autowired
	private PredictionService predictionService;

	@Autowired
	private ProblemService problemService;

	/* ============= REPOSITORIES ============= */

	@Autowired
	private PredictionRepository predictionRepository;

	public PredictionResultBean predict(String problemEndPoint, PredictionRequestBean predictionRequestBean)
			throws JsonProcessingException {
		PredictionResultBean predictionResultBean = new PredictionResultBean();

		Optional<Problem> optionalProblem = problemService.findByEndPoint(problemEndPoint);

		if (optionalProblem.isPresent()) {
			Prediction prediction = predictionService.saveFromRequest(optionalProblem.get(), predictionRequestBean);

			predictionResultBean = externalMlanyMlService.predict(prediction);

			prediction.setOutputJson(predictionResultBean.getOutput().getBytes(StandardCharsets.UTF_16));
			predictionRepository.save(prediction);
		}

		return predictionResultBean;
	}
}
