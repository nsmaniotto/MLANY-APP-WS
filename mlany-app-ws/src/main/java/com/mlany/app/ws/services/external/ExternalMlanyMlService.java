package com.mlany.app.ws.services.external;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlany.app.ws.services.mlany.model.ModelTrainingResultService;
import com.mlany.app.ws.services.mlany.model.ModelTrainingService;
import com.mlany.app.ws.services.mlany.problem.ProblemSolvingColumnService;
import com.mlany.app.persistence.entity.mlany.model.ModelTraining;
import com.mlany.app.persistence.entity.mlany.model.ModelTrainingResult;
import com.mlany.app.persistence.entity.mlany.prediction.Prediction;
import com.mlany.app.persistence.repository.mlany.model.ModelTrainingRepository;
import com.mlany.app.ws.bean.external.DatasetContentAnalysisResultBean;
import com.mlany.app.ws.bean.external.PredictionOrderBean;
import com.mlany.app.ws.bean.external.PredictionResultBean;
import com.mlany.app.ws.bean.external.TrainingBean;
import com.mlany.app.ws.bean.mlany.model.ModelTrainingResultBean;

@Service
public class ExternalMlanyMlService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String MLANY_ML_URL = "http://localhost:5009/";
	private static final String MLANY_ML_DUMMY_URL = MLANY_ML_URL + "/dummy";
	private static final String MLANY_ML_ANALYZE_DATASET_URL = MLANY_ML_URL + "/dataset/analyze";
	private static final String MLANY_ML_PREDICT_URL = MLANY_ML_URL + "/prediction/predict";
	private static final String MLANY_ML_TRAIN_URL = MLANY_ML_URL + "/training/train";

	/* =============== SERVICES =============== */

	@Autowired
	private ModelTrainingResultService modelTrainingResultService;

	@Autowired
	private ModelTrainingService modelTrainingService;

	@Autowired
	private ProblemSolvingColumnService problemSolvingColumnService;

	/* ============= REPOSITORIES ============= */

	@Autowired
	private ModelTrainingRepository modelTrainingRepository;

	/**
	 * Call external Python service to analyze the content of a dataset
	 * 
	 * @param fileInfoId: dataset file identifier
	 * @return null if analysis failed, otherwise the result of the analysis
	 */
	public DatasetContentAnalysisResultBean analyzeDatasetContent(Long fileInfoId) {
		logger.info("Analyzing fileInfo#{}", fileInfoId);

		DatasetContentAnalysisResultBean datasetContentAnalysisResult = null;

		HttpHeaders headers = createJsonHeaders();

		// Build request
		HttpEntity<Long> httpEntity = new HttpEntity<>(fileInfoId, headers);

		// Build Rest Template
		RestTemplate restTemplate = createJsonRestTemplate();

		// Send POST request
		ResponseEntity<DatasetContentAnalysisResultBean> response = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		try {
			response = restTemplate.postForEntity(MLANY_ML_ANALYZE_DATASET_URL, httpEntity,
					DatasetContentAnalysisResultBean.class);

			// Check response status code
			if (response.getStatusCode() == HttpStatus.OK) {
				datasetContentAnalysisResult = response.getBody();
			} else {
				logger.error("Could not get response at analysis for fileInfo#{}", fileInfoId);
			}
		} catch (Exception e) {
			logger.error("Could not get response at analysis for fileInfo#{}", fileInfoId, e);
		}

		return datasetContentAnalysisResult;
	}

	public void startTraining(ModelTraining modelTraining) {
		new Thread(() -> this.train(modelTraining)).start();
	}

	private void train(ModelTraining modelTraining) {
		logger.info("Training modelTraining#{}", modelTraining.getId());

		HttpHeaders headers = createJsonHeaders();

		// Build request body
		TrainingBean trainingBean = new TrainingBean();
		trainingBean.setModelTrainingId(modelTraining.getId());
		trainingBean.setModelClassName(modelTraining.getModelInstance().getModel().getClassName());
		trainingBean.setDatasetFileInfoId(modelTraining.getProblemSolving().getDataset().getFileInfo().getId());
		trainingBean.setColumns(problemSolvingColumnService
				.getColumnRawBeansByProblemSolvingId(modelTraining.getProblemSolving().getId()));
		trainingBean.setModelFileInfoId(modelTraining.getModelInstance().getFileInfo().getId());
		trainingBean.setVectorizerFileInfoId(modelTraining.getVectorizerFileInfo().getId());

		// Build request
		HttpEntity<TrainingBean> httpEntity = new HttpEntity<>(trainingBean, headers);

		// Build Rest Template
		RestTemplate restTemplate = createJsonRestTemplate();

		// Send POST request
		ResponseEntity<ModelTrainingResultBean> response = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		try {
			response = restTemplate.postForEntity(MLANY_ML_TRAIN_URL, httpEntity, ModelTrainingResultBean.class);

			// Check response status code
			if (response.getStatusCode() == HttpStatus.OK) {
				ModelTrainingResultBean responseBody = response.getBody();
				ModelTrainingResult modelTrainingResult = modelTrainingResultService.save(responseBody);
				modelTraining.setModelTrainingResult(modelTrainingResult);
				modelTrainingRepository.save(modelTraining);
				modelTrainingService.handleModelTrainingSuccess(modelTraining.getId(),
						ModelTrainingService.MAX_HANDLING_TRAINING_SUCCESS_ATTEMPTS);
			} else {
				logger.error("Could not get response at training for modelTraining#{}", modelTraining.getId());
				modelTrainingService.handleModelTrainingFailure(modelTraining.getId(),
						ModelTrainingService.MAX_HANDLING_TRAINING_FAILURE_ATTEMPTS);
			}
		} catch (Exception e) {
			modelTrainingService.handleModelTrainingFailure(modelTraining.getId(),
					ModelTrainingService.MAX_HANDLING_TRAINING_FAILURE_ATTEMPTS);
			logger.error("Could not get response at training for modelTraining#{}", modelTraining.getId(), e);
		}
	}

	private HttpHeaders createJsonHeaders() {
		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType(MediaType.APPLICATION_JSON);
		// set `accept` header
		headers.setAccept(Collections.singletonList(MediaType.ALL));

		return headers;
	}

	private RestTemplate createJsonRestTemplate() {
		RestTemplate restTemplate = new RestTemplateBuilder().build();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

		// Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);

		restTemplate.setMessageConverters(messageConverters);

		return restTemplate;
	}

	public PredictionResultBean predict(Prediction prediction) throws JsonProcessingException {
		logger.info("Predicting prediction#{}", prediction.getId());

		PredictionResultBean predictionResult = new PredictionResultBean();

		HttpHeaders headers = createJsonHeaders();

		// Build request body
		PredictionOrderBean predictionOrderBean = new PredictionOrderBean();
		predictionOrderBean.setPredictionId(prediction.getId());

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(new String(prediction.getInputJson(), StandardCharsets.UTF_16),
				Map.class);
		predictionOrderBean.setInputJson(map);

		predictionOrderBean.setModelFileInfoId(
				prediction.getProblem().getDeployedModelFromTraining().getModelInstance().getFileInfo().getId());
		predictionOrderBean.setVectorizerFileInfoId(
				prediction.getProblem().getDeployedModelFromTraining().getVectorizerFileInfo().getId());

		// Build request
		HttpEntity<PredictionOrderBean> httpEntity = new HttpEntity<>(predictionOrderBean, headers);

		// Build Rest Template
		RestTemplate restTemplate = createJsonRestTemplate();

		// Send POST request
		ResponseEntity<PredictionResultBean> response = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
		try {
			response = restTemplate.postForEntity(MLANY_ML_PREDICT_URL, httpEntity, PredictionResultBean.class);

			// Check response status code
			if (response.getStatusCode() == HttpStatus.OK) {
				PredictionResultBean responseBody = response.getBody();
				predictionResult.setOutput(responseBody != null ? responseBody.getOutput() : null);
			} else {
				logger.error("Could not get response at predicting for prediction#{}", prediction.getId());
			}
		} catch (Exception e) {
			logger.error("Could not get response at predicting for prediction#{}", prediction.getId(), e);
		}

		return predictionResult;
	}

	public void test() {
		HttpHeaders headers = createJsonHeaders();

		// create a map for post parameters
		Map<String, Object> map = new HashMap<>();
		map.put("userId", 1);
		map.put("title", "Introduction to Spring Boot");
		map.put("body", "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications.");

		// build the request
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

		// build rest template
		RestTemplate restTemplate = new RestTemplateBuilder().build();

		// send POST request
		ResponseEntity<String> response = restTemplate.postForEntity(MLANY_ML_DUMMY_URL, entity, String.class);

		// check response status code
		if (response.getStatusCode() == HttpStatus.OK) {
			logger.info(response.getBody());
		} else {
			logger.info("null response from MLany-ML");
		}
	}
}
