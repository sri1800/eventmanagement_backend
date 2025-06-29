package com.sri.event.Exception;

public class UserServiceException extends RuntimeException{
	
	private static final long serialVersionUID = -6215603674324441710L;

	public UserServiceException(String message) {
		super(message);
	}
}
