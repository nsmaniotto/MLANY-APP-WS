package com.mlany.app.ws.services.mlany.dataset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlany.app.persistence.entity.enumeration.common.ColumnInputOutputEnum;
import com.mlany.app.persistence.entity.enumeration.common.ColumnTypeEnum;
import com.mlany.app.persistence.entity.mlany.dataset.DatasetColumn;
import com.mlany.app.persistence.repository.mlany.dataset.DatasetColumnRepository;
import com.mlany.app.persistence.repository.mlany.dataset.DatasetContentInfoRepository;
import com.mlany.app.ws.bean.mlany.dataset.DatasetColumnBean;

@Service
public class DatasetColumnService {

	/* =============== SERVICES =============== */

	/* ============= REPOSITORIES ============= */

	@Autowired
	private DatasetColumnRepository datasetColumnRepository;

	@Autowired
	private DatasetContentInfoRepository datasetContentInfoRepository;

	@Transactional
	public DatasetColumn save(DatasetColumnBean datasetColumnBean) {
		DatasetColumn datasetColumn = new DatasetColumn();

		if (datasetColumnBean.getId() != null) {
			Optional<DatasetColumn> optionalDatasetColumn = datasetColumnRepository.findById(datasetColumnBean.getId());
			if (optionalDatasetColumn.isPresent()) {
				datasetColumn = optionalDatasetColumn.get();
			}
		}

		datasetColumn.setDatasetContentInfo(datasetColumnBean.getDatasetContentInfoId() != null
				? datasetContentInfoRepository.findById(datasetColumnBean.getDatasetContentInfoId()).orElse(null)
				: null);

		datasetColumn.setName(datasetColumnBean.getName());
		datasetColumn.setType(datasetColumnBean.getType());
		datasetColumn.setInputOutput(datasetColumnBean.getInputOutput());
		datasetColumn.setEmptyValueCount(datasetColumnBean.getEmptyValueCount());

		return datasetColumnRepository.save(datasetColumn);
	}

	public List<ColumnTypeEnum> getTypes() {
		return new ArrayList<>(Arrays.asList(ColumnTypeEnum.values()));
	}

	public List<ColumnInputOutputEnum> getInputOutputAnswers() {
		return new ArrayList<>(Arrays.asList(ColumnInputOutputEnum.values()));
	}
}
