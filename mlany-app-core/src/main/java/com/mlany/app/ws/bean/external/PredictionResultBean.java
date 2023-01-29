package com.mlany.app.ws.bean.external;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictionResultBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String output;

	@Override
	public String toString() {
		return "PredictionResultBean [id=" + id + ", output=" + output + "]";
	}
}