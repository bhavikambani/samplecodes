package com.elitecore.datamanager.exception;

public class MethodNotSupportedException extends Exception{
	
	public MethodNotSupportedException(){
		super("This method is not supported");
	}

	public MethodNotSupportedException(String message){
		super(message);
	}
}