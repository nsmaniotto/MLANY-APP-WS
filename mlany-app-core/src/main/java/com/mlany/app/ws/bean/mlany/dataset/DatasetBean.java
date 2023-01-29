package com.mlany.app.ws.bean.mlany.dataset;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;
import com.mlany.app.ws.bean.common.FileInfoBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class DatasetBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private FileInfoBean fileInfo;
	private DatasetContentInfoBean datasetContentInfo;
	private Long linkedProblemId;

	@Override
	public String toString() {
		return "DatasetBean [id=" + id + ", fileInfo=" + fileInfo + ", datasetContentInfo=" + datasetContentInfo
				+ ", linkedProblemId=" + linkedProblemId + "]";
	}
}