package com.mlany.app.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotNull;

import com.mlany.app.ws.bean.AbstractBean;

public interface Beanables {

	public static <T extends AbstractBean, U extends Beanable<T>> List<T> toBeanList(Iterable<U> beanables) {
		if (beanables != null) {
			return toBeanList(StreamSupport.stream(beanables.spliterator(), false));
		} else {
			return new ArrayList<>();
		}
	}

	public static <T extends AbstractBean, U extends Beanable<T>> List<T> toBeanList(@NotNull Stream<U> beanables) {
		return beanables.map(Beanable::toBean).toList();
	}
}