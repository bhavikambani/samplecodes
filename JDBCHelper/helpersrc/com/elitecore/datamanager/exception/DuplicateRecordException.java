package com.elitecore.datamanager.exception;

public class DuplicateRecordException extends Exception{
	
	public DuplicateRecordException(){
		super("This method is not supported");
	}

	public DuplicateRecordException(String message){
		super(message);
	}
}