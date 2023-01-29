package com.mlany.app.persistence.entity.mlany.dataset;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.Beanables;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.bean.mlany.dataset.DatasetContentInfoBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DATASET_CONTENT_INFO")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class DatasetContentInfo extends MlanyEntity implements Beanable<DatasetContentInfoBean> {

	private static final long serialVersionUID = 1L;

	@Column(name = "ANALYSIS_STATUS", length = 20, nullable = true)
	private String analysisStatus;

	@Column(name = "LINE_COUNT", nullable = true)
	private Long lineCount;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "datasetContentInfo", cascade = CascadeType.ALL)
	private List<DatasetColumn> columns = new ArrayList<>();

	@Override
	public DatasetContentInfoBean toBean() {
		DatasetContentInfoBean bean = new DatasetContentInfoBean();
		bean.setId(getId());
		bean.setAnalysisStatus(getAnalysisStatus());
		bean.setLineCount(getLineCount());
		bean.setColumns(getColumns() != null ? Beanables.toBeanList(getColumns()) : null);
		return bean;
	}

}
