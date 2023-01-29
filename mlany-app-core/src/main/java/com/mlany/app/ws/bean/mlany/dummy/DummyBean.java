package com.mlany.app.ws.bean.mlany.dummy;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class DummyBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

	@Override
	public String toString() {
		return "DummyBean [id=" + id + ", name=" + name + "]";
	}
}