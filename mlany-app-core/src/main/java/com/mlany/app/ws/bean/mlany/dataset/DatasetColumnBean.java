package com.mlany.app.ws.bean.mlany.dataset;

import java.io.Serializable;
import java.util.List;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class DatasetColumnBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long datasetContentInfoId;
	private String name;
	private String type;
	private String inputOutput;
	private Long emptyValueCount;
	private List<DatasetColumnCorrelationBean> correlations;

	@Override
	public String toString() {
		return "DatasetColumnBean [id=" + id + ", datasetContentInfoId=" + datasetContentInfoId + ", name=" + name
				+ ", type=" + type + ", inputOutput=" + inputOutput + ", emptyValueCount=" + emptyValueCount
				+ ", correlations=" + correlations + "]";
	}
}