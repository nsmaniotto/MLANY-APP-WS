package com.mlany.app.ws.services.mlany.dataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlany.app.ws.services.external.ExternalMlanyMlService;
import com.mlany.app.persistence.entity.common.FileInfo;
import com.mlany.app.persistence.entity.enumeration.dataset.DatasetContentInfoAnalysisStatusEnum;
import com.mlany.app.persistence.entity.mlany.dataset.DatasetColumn;
import com.mlany.app.persistence.entity.mlany.dataset.DatasetColumnCorrelation;
import com.mlany.app.persistence.entity.mlany.dataset.DatasetContentInfo;
import com.mlany.app.persistence.repository.mlany.dataset.DatasetColumnCorrelationRepository;
import com.mlany.app.persistence.repository.mlany.dataset.DatasetContentInfoRepository;
import com.mlany.app.ws.bean.external.DatasetContentAnalysisResultBean;
import com.mlany.app.ws.bean.mlany.dataset.DatasetColumnCorrelationBean;

@Service
public class DatasetContentInfoService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final int MAX_ANALYSIS_ATTEMPTS = 10;
	private static final long MS_TIME_BETWEEN_ANALYSIS_ATTEMPT = 500;

	/* =============== SERVICES =============== */

	@Autowired
	private DatasetColumnService datasetColumnService;

	@Autowired
	private ExternalMlanyMlService externalMlanyMlService;

	/* ============= REPOSITORIES ============= */

	@Autowired
	private DatasetColumnCorrelationRepository datasetColumnCorrelationRepository;

	@Autowired
	private DatasetContentInfoRepository datasetContentInfoRepository;

	@Transactional
	public DatasetContentInfo startAnalysis(FileInfo fileInfo) {
		DatasetContentInfo datasetContentInfo = new DatasetContentInfo();

		datasetContentInfo.setAnalysisStatus(DatasetContentInfoAnalysisStatusEnum.IN_PROGRESS.name());
		datasetContentInfoRepository.save(datasetContentInfo);
		logger.info("Initialized and saved datasetContentInfo#{}", datasetContentInfo.getId());

		// Start dataset content's analysis in an asynchronous way
		new Thread(() -> this.analyzeDatasetContent(datasetContentInfo.getId(), fileInfo, MAX_ANALYSIS_ATTEMPTS))
				.start();

		return datasetContentInfo;
	}

	private void analyzeDatasetContent(Long datasetContentInfoId, FileInfo fileInfo, int attemptsLeft) {
		logger.info("Started datasetContentInfo#{} anlysis of file#{} - {}", datasetContentInfoId, fileInfo.getId(),
				fileInfo.getName());
		Optional<DatasetContentInfo> optDatasetContentInfo = datasetContentInfoRepository
				.findById(datasetContentInfoId);

		if (optDatasetContentInfo.isPresent()) {
			DatasetContentInfo datasetContentInfo = optDatasetContentInfo.get();

			DatasetContentAnalysisResultBean datasetContentAnalysisResultBean = externalMlanyMlService
					.analyzeDatasetContent(fileInfo.getId());

			if (datasetContentAnalysisResultBean != null) {
				datasetContentInfo.setLineCount(datasetContentAnalysisResultBean.getLineCount());
				datasetContentInfo.setColumns(datasetContentAnalysisResultBean.getColumns().stream().map(column -> {
					column.setDatasetContentInfoId(datasetContentInfoId);
					return datasetColumnService.save(column);
				}).toList());

				// Generate column correlations using previously saved columns
				saveDatasetColumnCorrelations(datasetContentAnalysisResultBean.getColumnCorrelations(),
						datasetContentInfo.getColumns());

				datasetContentInfo.setAnalysisStatus(DatasetContentInfoAnalysisStatusEnum.FINISHED.name());
			} else {
				datasetContentInfo.setAnalysisStatus(DatasetContentInfoAnalysisStatusEnum.FAILED.name());
			}

			logger.info("file#{} - {} content analysis ended with status {}", fileInfo.getId(), fileInfo.getName(),
					datasetContentInfo.getAnalysisStatus());

			DatasetContentInfo updatedDatasetContentInfo = datasetContentInfoRepository.save(datasetContentInfo);
			logger.info("Updating datasetContentInfo#{}", updatedDatasetContentInfo.getId());
		} else if (attemptsLeft > 0) {
			logger.info("datasetContentInfo#{} was not found, retry {}/{}", datasetContentInfoId,
					MAX_ANALYSIS_ATTEMPTS - attemptsLeft + 1, MAX_ANALYSIS_ATTEMPTS);
			try {
				Thread.sleep(MS_TIME_BETWEEN_ANALYSIS_ATTEMPT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.analyzeDatasetContent(datasetContentInfoId, fileInfo, attemptsLeft - 1);
		}
	}

	private List<DatasetColumnCorrelation> saveDatasetColumnCorrelations(
			List<DatasetColumnCorrelationBean> datasetColumnCorrelationBeans, List<DatasetColumn> datasetColumns) {
		List<DatasetColumnCorrelation> columnCorrelations = new ArrayList<>();

		datasetColumnCorrelationBeans.forEach(columnCorrelationBean -> {
			DatasetColumnCorrelation columnCorrelation = new DatasetColumnCorrelation();

			// Find equivalent right column by name
			columnCorrelation.setFirstColumn(datasetColumns.stream()
					.filter(datasetColumn -> datasetColumn.getName().equals(columnCorrelationBean.getFirstColumnName()))
					.findFirst().orElse(null));

			// Find equivalent left column by name
			columnCorrelation.setSecondColumn(datasetColumns.stream().filter(
					datasetColumn -> datasetColumn.getName().equals(columnCorrelationBean.getSecondColumnName()))
					.findFirst().orElse(null));

			columnCorrelation.setCorrelation(columnCorrelationBean.getCorrelation());

			// Save column correlation instance
			columnCorrelations.add(datasetColumnCorrelationRepository.save(columnCorrelation));
		});

		return columnCorrelations;
	}
}
