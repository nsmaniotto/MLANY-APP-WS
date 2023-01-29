package com.mlany.app.ws.bean.mlany.model;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ModelInstanceParameterBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long modelInstanceId;
	private String parameter;
	private String value;

	@Override
	public String toString() {
		return "ModelInstanceParameterBean [id=" + id + ", modelInstanceId=" + modelInstanceId + ", parameter="
				+ parameter + ", value=" + value + "]";
	}
}