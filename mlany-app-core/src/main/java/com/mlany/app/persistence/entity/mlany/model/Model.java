package com.mlany.app.persistence.entity.mlany.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.mlany.app.persistence.entity.Beanable;
import com.mlany.app.ws.bean.mlany.model.ModelBean;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MODEL")
@IdClass(ModelId.class)
@Getter
@Setter
public class Model implements Beanable<ModelBean>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	@Id
	@Column(name = "FAMILY", length = 50, nullable = false)
	private String family;

	@Column(name = "CLASS_NAME", length = 50, nullable = false)
	private String className;

	@Override
	public ModelBean toBean() {
		ModelBean bean = new ModelBean();
		bean.setName(getName());
		bean.setFamily(getFamily());
		bean.setClassName(getClassName());
		return bean;
	}

}
