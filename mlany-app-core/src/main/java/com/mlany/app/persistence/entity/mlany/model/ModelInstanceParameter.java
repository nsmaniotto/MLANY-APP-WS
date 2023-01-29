package com.mlany.app.persistence.entity.mlany.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.bean.mlany.model.ModelInstanceParameterBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODEL_INSTANCE_PARAMETER")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class ModelInstanceParameter extends MlanyEntity implements Beanable<ModelInstanceParameterBean> {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODEL_INSTANCE_ID", nullable = false)
	private ModelInstance modelInstance;

	@Column(name = "PARAMETER", length = 50, nullable = false)
	private String parameter;

	@Column(name = "VALUE", length = 50, nullable = false)
	private String value;

	@Override
	public ModelInstanceParameterBean toBean() {
		ModelInstanceParameterBean bean = new ModelInstanceParameterBean();
		bean.setId(getId());
		bean.setModelInstanceId(getModelInstance() != null ? getModelInstance().getId() : null);
		bean.setParameter(getParameter());
		bean.setValue(getValue());
		return bean;
	}

}
