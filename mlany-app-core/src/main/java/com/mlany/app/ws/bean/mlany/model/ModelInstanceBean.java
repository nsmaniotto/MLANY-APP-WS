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
public class ModelInstanceBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private ModelBean model;
	private FileInfoBean fileInfo;

	@Override
	public String toString() {
		return "ModelInstanceBean [id=" + id + ", model=" + model + ", fileInfo=" + fileInfo + "]";
	}
}