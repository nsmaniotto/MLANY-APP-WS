package com.mlany.app.ws.services.mlany.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mlany.app.ws.services.common.filer.FilerService;
import com.mlany.app.persistence.entity.mlany.model.Model;
import com.mlany.app.persistence.entity.mlany.model.ModelInstance;
import com.mlany.app.persistence.repository.mlany.model.ModelInstanceRepository;

@Service
public class ModelInstanceService {

	private static final String MODEL_DIRECTORY = "model";
	private static final String MODEL_EXTENSION = "pkl";

	/* =============== SERVICES =============== */

	@Autowired
	private FilerService filerService;

	/* ============= REPOSITORIES ============= */

	@Autowired
	ModelInstanceRepository modelInstanceRepository;

	public ModelInstance generateModelInstance(Model model) {
		ModelInstance modelInstance = new ModelInstance();
		modelInstance.setModel(model);
		String fileName = new StringBuilder().append(model.getName()).append(".").append(MODEL_EXTENSION).toString();
		modelInstance.setFileInfo(filerService.generateFileInfo(MODEL_DIRECTORY, fileName));
		return modelInstanceRepository.save(modelInstance);
	}
}
