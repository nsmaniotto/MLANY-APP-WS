package com.mlany.app.ws.services.mlany.dataset;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mlany.app.ws.services.common.filer.FilerService;
import com.mlany.app.persistence.entity.mlany.dataset.Dataset;
import com.mlany.app.persistence.repository.common.FileInfoRepository;
import com.mlany.app.persistence.repository.mlany.dataset.DatasetContentInfoRepository;
import com.mlany.app.persistence.repository.mlany.dataset.DatasetRepository;
import com.mlany.app.persistence.repository.mlany.problem.ProblemRepository;
import com.mlany.app.ws.bean.mlany.dataset.DatasetBean;

@Service
public class DatasetService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* =============== SERVICES =============== */

	@Autowired
	private DatasetContentInfoService datasetContentInfoService;

	@Autowired
	private FilerService filerService;

	/* ============= REPOSITORIES ============= */

	@Autowired
	private DatasetContentInfoRepository datasetContentInfoRepository;

	@Autowired
	private DatasetRepository datasetRepository;

	@Autowired
	private FileInfoRepository fileInfoRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Transactional
	public Dataset createFromFile(MultipartFile file) throws IOException {
		Dataset dataset = new Dataset();

		dataset.setFileInfo(filerService.upload(file));
		dataset.setDatasetContentInfo(datasetContentInfoService.startAnalysis(dataset.getFileInfo()));

		datasetRepository.save(dataset);
		logger.info("Initialized and saved dataset#{} with datasetContentInfo#{}", dataset.getId(),
				dataset.getDatasetContentInfo().getId());

		return dataset;
	}

	@Transactional
	public Dataset save(DatasetBean datasetBean) {
		Dataset dataset = new Dataset();

		if (datasetBean.getId() != null) {
			Optional<Dataset> optionalDataset = datasetRepository.findById(datasetBean.getId());
			if (optionalDataset.isPresent()) {
				dataset = optionalDataset.get();
			}
		}

		if (datasetBean.getFileInfo() != null) {
			dataset.setFileInfo(fileInfoRepository.findById(datasetBean.getFileInfo().getId()).orElse(null));
		}

		if (datasetBean.getDatasetContentInfo() != null) {
			dataset.setDatasetContentInfo(
					datasetContentInfoRepository.findById(datasetBean.getDatasetContentInfo().getId()).orElse(null));
		}

		dataset.setLinkedProblem(datasetBean.getLinkedProblemId() != null
				? problemRepository.findById(datasetBean.getLinkedProblemId()).orElse(null)
				: null);

		return datasetRepository.save(dataset);
	}

	public Optional<Dataset> getDataset(Long id) {
		return datasetRepository.findById(id);
	}

	public Iterable<Dataset> getAll() {
		return datasetRepository.findAllByOrderByIdAsc();
	}
}
