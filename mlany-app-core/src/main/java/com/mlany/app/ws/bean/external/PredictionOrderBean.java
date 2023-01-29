package com.mlany.app.ws.bean.external;

import java.io.Serializable;
import java.util.Map;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictionOrderBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long predictionId;
	private Map<String, Object> inputJson;
	private Long modelFileInfoId;
	private Long vectorizerFileInfoId;

	@Override
	public String toString() {
		return "PredictionOrderBean [predictionId=" + predictionId + ", input=" + inputJson + ", modelFileInfoId="
				+ modelFileInfoId + ", vectorizerFileInfoId=" + vectorizerFileInfoId + "]";
	}
}