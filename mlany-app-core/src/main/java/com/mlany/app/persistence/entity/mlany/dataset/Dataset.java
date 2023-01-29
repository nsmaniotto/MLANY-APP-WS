package com.mlany.app.persistence.entity.mlany.dataset;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.common.FileInfo;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.persistence.entity.mlany.problem.Problem;
import com.mlany.app.ws.bean.mlany.dataset.DatasetBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "DATASET")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class Dataset extends MlanyEntity implements Beanable<DatasetBean> {

	private static final long serialVersionUID = 1L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_INFO_ID", nullable = false)
	private FileInfo fileInfo;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DATASET_CONTENT_INFO_ID", nullable = true)
	private DatasetContentInfo datasetContentInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINKED_PROBLEM_ID", nullable = true)
	private Problem linkedProblem;

	@Override
	public DatasetBean toBean() {
		DatasetBean bean = new DatasetBean();
		bean.setId(getId());
		bean.setFileInfo(getFileInfo() != null ? getFileInfo().toBean() : null);
		bean.setDatasetContentInfo(getDatasetContentInfo() != null ? getDatasetContentInfo().toBean() : null);
		bean.setLinkedProblemId(getLinkedProblem() != null ? getLinkedProblem().getId() : null);
		return bean;
	}

}
