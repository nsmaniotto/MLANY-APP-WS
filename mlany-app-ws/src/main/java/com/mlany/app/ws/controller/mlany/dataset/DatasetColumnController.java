package com.mlany.app.ws.controller.mlany.dataset;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlany.app.ws.services.mlany.dataset.DatasetColumnService;
import com.mlany.app.persistence.entity.enumeration.common.ColumnInputOutputEnum;
import com.mlany.app.persistence.entity.enumeration.common.ColumnTypeEnum;
import com.mlany.app.ws.bean.mlany.dataset.DatasetColumnBean;

@RestController
@RequestMapping("dataset/column")
public class DatasetColumnController {

	@Autowired
	private DatasetColumnService datasetColumnService;

	@PostMapping(value = "save")
	public DatasetColumnBean save(@RequestBody DatasetColumnBean datasetColumnBean) {
		return datasetColumnService.save(datasetColumnBean).toBean();
	}

	@GetMapping(value = "type")
	public List<ColumnTypeEnum> getTypes() {
		return datasetColumnService.getTypes();
	}

	@GetMapping(value = "input-output")
	public List<ColumnInputOutputEnum> getInputOutputAnswers() {
		return datasetColumnService.getInputOutputAnswers();
	}
}
