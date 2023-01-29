package com.mlany.app.persistence.entity;

import javax.validation.constraints.NotNull;

import com.mlany.app.ws.bean.AbstractBean;

@FunctionalInterface
public interface Beanable<T extends AbstractBean> {
	@NotNull
	T toBean();
}