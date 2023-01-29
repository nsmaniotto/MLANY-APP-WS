package com.mlany.app.ws.bean.external;

import java.io.Serializable;
import java.util.Map;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictionRequestBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> input;

	@Override
	public String toString() {
		return "PredictionRequestBean [input=" + input + "]";
	}
}