package com.mlany.app.ws.bean.external;

import java.io.Serializable;
import java.util.List;

import com.mlany.app.ws.bean.AbstractBean;
import com.mlany.app.ws.bean.mlany.dataset.DatasetColumnBean;
import com.mlany.app.ws.bean.mlany.dataset.DatasetColumnCorrelationBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasetContentAnalysisResultBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long lineCount;
	private List<DatasetColumnBean> columns;
	private List<DatasetColumnCorrelationBean> columnCorrelations;

	@Override
	public String toString() {
		return "DatasetContentAnalysisResultBean [lineCount=" + lineCount + ", columns=" + columns
				+ ", columnCorrelations=" + columnCorrelations + "]";
	}
}