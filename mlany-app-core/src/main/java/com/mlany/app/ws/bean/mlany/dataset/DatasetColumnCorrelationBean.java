package com.mlany.app.ws.bean.mlany.dataset;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "firstColumnId", "secondColumnId" })
public class DatasetColumnCorrelationBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long firstColumnId;
	private String firstColumnName;
	private Long secondColumnId;
	private String secondColumnName;
	private Float correlation;

	@Override
	public String toString() {
		return "DatasetColumnBean [firstColumnId=" + firstColumnId + ", firstColumnName=" + firstColumnName
				+ ", secondColumnId=" + secondColumnId + ", secondColumnName=" + secondColumnName + ", correlation="
				+ correlation + "]";
	}
}