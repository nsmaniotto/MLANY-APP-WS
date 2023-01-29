package com.mlany.app.ws.bean.mlany.common;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnRawBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private String name;
	private String type;
	private String inputOutput;

	@Override
	public String toString() {
		return "ColumnRawBean [name=" + name + ", type=" + type + ", inputOutput=" + inputOutput + "]";
	}
}