package com.mlany.app.ws.bean;

import java.io.Serializable;

public interface AbstractBean extends Serializable {
	
	@Override
	public abstract int hashCode();
	
	@Override
	public abstract boolean equals(Object obj);
	
	@Override
	public abstract String toString();
	
}