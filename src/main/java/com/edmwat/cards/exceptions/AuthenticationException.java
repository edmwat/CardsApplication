package com.edmwat.cards.exceptions;

import lombok.Data;

@Data
public class AuthenticationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private int statusCode;
	
	public AuthenticationException() {}
	
	public AuthenticationException(String message){
		super(message);
	}
	public AuthenticationException(String message, int statusCode){
		super(message);
		this.statusCode = statusCode;
		
	}

}
