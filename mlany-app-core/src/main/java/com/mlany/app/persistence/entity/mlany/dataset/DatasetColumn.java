package com.mlany.app.persistence.entity.mlany.dataset;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.Beanables;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.bean.mlany.common.ColumnRawBean;
import com.mlany.app.ws.bean.mlany.dataset.DatasetColumnBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DATASET_COLUMN")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class DatasetColumn extends MlanyEntity implements Beanable<DatasetColumnBean> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATASET_CONTENT_INFO_ID", nullable = false)
	private DatasetContentInfo datasetContentInfo;

	@Column(name = "NAME", length = 250, nullable = false)
	private String name;

	@Column(name = "TYPE", length = 50, nullable = true)
	private String type;

	@Column(name = "INPUT_OUPUT", length = 50, nullable = true)
	private String inputOutput;

	@Column(name = "EMPTY_VALUE_COUNT", nullable = true)
	private Long emptyValueCount;

	@OneToMany(mappedBy = "firstColumn", fetch = FetchType.LAZY)
	private List<DatasetColumnCorrelation> correlations;

	@Override
	public DatasetColumnBean toBean() {
		DatasetColumnBean bean = new DatasetColumnBean();
		bean.setId(getId());
		bean.setDatasetContentInfoId(getDatasetContentInfo() != null ? getDatasetContentInfo().getId() : null);
		bean.setName(getName());
		bean.setType(getType());
		bean.setInputOutput(getInputOutput());
		bean.setEmptyValueCount(getEmptyValueCount());
		bean.setCorrelations(Beanables.toBeanList(getCorrelations()));
		return bean;
	}

	public ColumnRawBean toRawBean() {
		ColumnRawBean bean = new ColumnRawBean();
		bean.setName(getName());
		bean.setType(getType());
		bean.setInputOutput(getInputOutput());
		return bean;
	}

}
