package com.mlany.app.ws.exception;

public class MlanyApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MlanyApplicationException(String message) {
		super(message);
	}

	public MlanyApplicationException(Exception e) {
		super(e);
	}
}
