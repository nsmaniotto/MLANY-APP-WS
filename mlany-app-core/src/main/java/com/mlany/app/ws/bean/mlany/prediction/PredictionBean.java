package com.mlany.app.ws.bean.mlany.prediction;

import java.io.Serializable;
import java.util.Date;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PredictionBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long problemId;
	private String inputJson;
	private String outputJson;
	private Date predictionRequestDate;
	private Date predictionResultDate;

	@Override
	public String toString() {
		return "PredictionBean [id=" + id + ", problemId=" + problemId + ", inputJson=" + inputJson + ", outputJson="
				+ outputJson + ", predictionRequestDate=" + predictionRequestDate + ", predictionResultDate="
				+ predictionResultDate + "]";
	}
}