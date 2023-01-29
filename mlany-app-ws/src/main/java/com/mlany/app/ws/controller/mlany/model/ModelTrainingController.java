package com.mlany.app.ws.controller.mlany.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.services.mlany.model.ModelTrainingService;

@RestController
@RequestMapping("model/training")
public class ModelTrainingController {

	@Autowired
	private ModelTrainingService modelTrainingService;

	@PostMapping(value = "deploy")
	public Long deployModelFromTraining(@RequestBody Long modelTrainingId) {
		return modelTrainingService.deployModelFromTraining(modelTrainingId);
	}
}
