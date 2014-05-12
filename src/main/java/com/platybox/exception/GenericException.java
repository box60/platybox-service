package com.platybox.exception;

/*Generates a platybox exception with a custom message*/

public class GenericException extends RuntimeException{
	 
	private String customMsg;
 
	public GenericException(String customMsg) {
		this.customMsg = customMsg;
	}
 
}