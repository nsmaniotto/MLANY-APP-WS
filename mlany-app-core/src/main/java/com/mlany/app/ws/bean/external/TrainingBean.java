package com.mlany.app.ws.bean.external;

import java.io.Serializable;
import java.util.List;

import com.mlany.app.ws.bean.AbstractBean;
import com.mlany.app.ws.bean.mlany.common.ColumnRawBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "modelTrainingId")
public class TrainingBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long modelTrainingId;
	private String modelClassName;
	private Long datasetFileInfoId;
	private List<ColumnRawBean> columns;
	private Long modelFileInfoId;
	private Long vectorizerFileInfoId;

	@Override
	public String toString() {
		return "TrainingBean [modelTrainingId=" + modelTrainingId + ", modelClassName=" + modelClassName
				+ ", datasetFileInfoId=" + datasetFileInfoId + ", columns=" + modelFileInfoId + ", columns="
				+ modelFileInfoId + ", vectorizerFileInfoId=" + vectorizerFileInfoId + "]";
	}
}