package com.pala.springboot.exception;

import java.time.OffsetDateTime;

public class CustomErrorDetails {
	
	private OffsetDateTime date;
	private String message;
	private String details;
	
	public CustomErrorDetails(OffsetDateTime date, String message, String details) {
		this.date = date;
		this.message = message;
		this.details = details;
	}

	public OffsetDateTime getDate() {
		return date;
	}

	public void setDate(OffsetDateTime date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "CustomErrorDetails [date=" + date + ", message=" + message + ", details=" + details + "]";
	}
	
}
