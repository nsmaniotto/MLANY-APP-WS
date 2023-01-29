package com.mlany.app.ws.controller.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mlany.app.ws.services.mlany.prediction.PredictionRequestService;
import com.mlany.app.ws.bean.external.PredictionRequestBean;
import com.mlany.app.ws.bean.external.PredictionResultBean;

@RestController
@RequestMapping("api/predict")
public class PredictionRequestController {

	@Autowired
	private PredictionRequestService predictionRequestService;

	@PostMapping(value = "{problemEndPoint}")
	public PredictionResultBean predict(@PathVariable String problemEndPoint,
			@RequestBody PredictionRequestBean predictionRequestBean) throws JsonProcessingException {
		return predictionRequestService.predict(problemEndPoint, predictionRequestBean);
	}
}
