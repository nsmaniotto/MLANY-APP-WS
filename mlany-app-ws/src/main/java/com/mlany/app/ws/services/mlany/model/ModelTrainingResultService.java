package com.mlany.app.ws.services.mlany.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlany.app.persistence.entity.mlany.model.ModelTrainingResult;
import com.mlany.app.persistence.repository.mlany.model.ModelTrainingResultRepository;
import com.mlany.app.ws.bean.mlany.model.ModelTrainingResultBean;

@Service
public class ModelTrainingResultService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private ModelTrainingResultRepository modelTrainingResultRepository;

	public ModelTrainingResult save(ModelTrainingResultBean bean) {
		ModelTrainingResult entity = new ModelTrainingResult();

		if (bean.getId() != null) {
			Optional<ModelTrainingResult> optionalModelTrainingResult = modelTrainingResultRepository
					.findById(bean.getId());

			if (optionalModelTrainingResult.isPresent()) {
				entity = optionalModelTrainingResult.get();
			}
		}

		if (bean.getMeanScore() != null) {
			entity.setMeanScore(bean.getMeanScore());
		}

		if (bean.getStandardDeviation() != null) {
			entity.setStandardDeviation(bean.getStandardDeviation());
		}

		if (bean.getTrainingAccuracy() != null) {
			entity.setTrainingAccuracy(bean.getTrainingAccuracy());
		}

		if (bean.getValidationAccuracy() != null) {
			entity.setValidationAccuracy(bean.getValidationAccuracy());
		}

		if (bean.getAveragePredictionTimeMs() != null) {
			entity.setAveragePredictionTimeMs(bean.getAveragePredictionTimeMs());
		}

		return modelTrainingResultRepository.save(entity);
	}
}
