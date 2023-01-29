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
public class DatasetContentInfoBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String analysisStatus;
	private Long lineCount;
	private List<DatasetColumnBean> columns;

	@Override
	public String toString() {
		return "DatasetContentInfoBean [id=" + id + ", analysisStatus=" + analysisStatus + ", lineCount=" + lineCount
				+ ", columns=" + columns + "]";
	}
}