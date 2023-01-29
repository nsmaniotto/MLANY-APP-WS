package com.mlany.app.persistence.entity.mlany.dataset;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.ws.bean.mlany.dataset.DatasetColumnCorrelationBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@IdClass(DatasetColumnCorrelationId.class)
@Table(name = "DATASET_COLUMN_CORRELATION")
@Getter
@Setter
@EqualsAndHashCode(of = { "firstColumn", "secondColumn" })
public class DatasetColumnCorrelation implements Beanable<DatasetColumnCorrelationBean>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIRST_COLUMN_ID", nullable = false)
	private DatasetColumn firstColumn;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECOND_COLUMN_ID", nullable = false)
	private DatasetColumn secondColumn;

	@Column(name = "CORRELATION", nullable = false)
	private Float correlation;

	@Override
	public DatasetColumnCorrelationBean toBean() {
		DatasetColumnCorrelationBean bean = new DatasetColumnCorrelationBean();
		bean.setFirstColumnId(getFirstColumn() != null ? getFirstColumn().getId() : null);
		bean.setSecondColumnId(getSecondColumn() != null ? getSecondColumn().getId() : null);
		bean.setCorrelation(getCorrelation());
		return bean;
	}

}
