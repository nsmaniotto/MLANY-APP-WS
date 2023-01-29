package com.mlany.app.ws.bean.mlany.problem;

import java.io.Serializable;
import java.util.List;

import com.mlany.app.ws.bean.AbstractBean;
import com.mlany.app.ws.bean.mlany.common.ColumnRawBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemSolvingRawBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long problemId;
	private Long datasetId;
	private List<ColumnRawBean> columns;

	@Override
	public String toString() {
		return "ProblemSolvingRawBean [problemId=" + problemId + ", datasetId=" + datasetId + ", columns=" + columns
				+ "]";
	}
}