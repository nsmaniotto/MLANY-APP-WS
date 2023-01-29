package com.mlany.app.ws.bean.common;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class FileInfoBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String path;
	private String name;

	@Override
	public String toString() {
		return "FileInfoBean [id=" + id + ", path=" + path + ", name=" + name + "]";
	}
}