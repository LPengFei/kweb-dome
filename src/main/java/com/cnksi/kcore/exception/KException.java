package com.cnksi.kcore.exception;

public class KException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KException(String message) {
		super(message);
	}

	public KException(String message, Exception e) {
		super(message, e);
	}

}
