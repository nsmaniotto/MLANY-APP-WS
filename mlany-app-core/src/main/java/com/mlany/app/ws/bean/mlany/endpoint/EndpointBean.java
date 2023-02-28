package com.mlany.app.ws.bean.mlany.endpoint;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class EndpointBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Long modelInstanceId;

	@Override
	public String toString() {
		return "EndpointBean [id=" + id + ", name=" + name + ", modelInstanceId=" + modelInstanceId + "]";
	}
}