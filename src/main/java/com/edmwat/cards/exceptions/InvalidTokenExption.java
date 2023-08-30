package com.edmwat.cards.exceptions;


public class InvalidTokenExption extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private int errorCode;
		
	public InvalidTokenExption(String message, int errorCode){
		super(message);
		this.errorCode = errorCode;
	}
	

}
