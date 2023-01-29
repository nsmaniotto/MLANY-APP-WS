package com.mlany.app.ws.bean.mlany.model;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;
import com.mlany.app.ws.bean.common.FileInfoBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ModelTrainingBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long problemSolvingId;
	private ModelInstanceBean modelInstance;
	private FileInfoBean vectorizerFileInfo;
	private String status;
	private ModelTrainingResultBean modelTrainingResult;

	@Override
	public String toString() {
		return "ModelTrainingBean [id=" + id + ", problemSolvingId=" + problemSolvingId + ", modelInstance="
				+ modelInstance + ", vectorizerFileInfo=" + vectorizerFileInfo + ", status=" + status
				+ ", modelTrainingResult=" + modelTrainingResult + "]";
	}
}