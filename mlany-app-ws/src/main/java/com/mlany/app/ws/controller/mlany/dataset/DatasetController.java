package com.mlany.app.ws.controller.mlany.dataset;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mlany.app.ws.services.mlany.dataset.DatasetService;
import com.mlany.app.persistence.entity.Beanables;
import com.mlany.app.persistence.entity.mlany.dataset.Dataset;
import com.mlany.app.ws.bean.mlany.dataset.DatasetBean;

@RestController
@RequestMapping("dataset")
public class DatasetController {

	@Autowired
	private DatasetService datasetService;

	@GetMapping(value = "")
	public List<DatasetBean> getAll() {
		return Beanables.toBeanList(datasetService.getAll());
	}

	@GetMapping(value = "{id}")
	public DatasetBean getDataset(@PathVariable Long id) {
		Optional<Dataset> dataset = datasetService.getDataset(id);
		return dataset.isPresent() ? dataset.get().toBean() : null;
	}

	@PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public DatasetBean upload(@RequestParam("file") MultipartFile file) throws IOException {
		return datasetService.createFromFile(file).toBean();
	}

	@PostMapping(value = "save")
	public DatasetBean save(@RequestBody DatasetBean datasetBean) {
		return datasetService.save(datasetBean).toBean();
	}
}
