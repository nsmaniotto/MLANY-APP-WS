package com.mlany.app.ws.bean.common;

import java.io.Serializable;

import com.mlany.app.ws.bean.AbstractBean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfoRawBean implements Serializable, AbstractBean {

	private static final long serialVersionUID = 1L;

	private String path;
	private String prefix;
	private String name;

	@Override
	public String toString() {
		return "FileInfoRawBean [path=" + path + ", prefix=" + prefix + ", name=" + name + "]";
	}
}