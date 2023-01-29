package com.mlany.app.ws.bean.mlany.model;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class ModelBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String className;
	private String family;

	@Override
	public String toString() {
		return "ModelBean [id=" + id + ", name=" + name + ", className=" + className + ", family=" + family + "]";
	}
}