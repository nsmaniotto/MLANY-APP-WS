package com.mlany.app.persistence.entity.mlany.endpoint;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.persistence.entity.mlany.model.ModelInstance;
import com.mlany.app.ws.bean.mlany.endpoint.EndpointBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ENDPOINT")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class Endpoint extends MlanyEntity implements Beanable<EndpointBean> {

	private static final long serialVersionUID = 1L;

	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODEL_INSTANCE_ID", nullable = false)
	private ModelInstance modelInstance;

	@Override
	public EndpointBean toBean() {
		EndpointBean bean = new EndpointBean();
		bean.setId(getId());
		bean.setName(getName());
		bean.setModelInstanceId(getModelInstance() != null ? getModelInstance().getId() : null);
		return bean;
	}

}
