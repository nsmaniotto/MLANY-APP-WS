package com.mlany.app.persistence.entity;

import javax.validation.constraints.NotNull;

import com.mlany.app.ws.bean.AbstractBean;

public interface Beanable<T extends AbstractEntity> {
	@NotNull
	T toBean();
}