package com.sri.event.Exception;

import java.util.Date;

public class ErrorMessage {

	Date errorDate;
	String message;
	
	
	public ErrorMessage() {
		super();
	}
	public ErrorMessage(Date errorDate, String message) {
		super();
		this.errorDate = errorDate;
		this.message = message;
	}
	public Date getErrorDate() {
		return errorDate;
	}
	public void setErrorDate(Date errorDate) {
		this.errorDate = errorDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
