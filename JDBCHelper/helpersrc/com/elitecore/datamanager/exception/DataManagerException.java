package com.elitecore.datamanager.exception;

import java.lang.Exception;

public class DataManagerException extends Exception{
	
	public DataManagerException(){
		super("This method is not supported");
	}

	public DataManagerException(String message){
		super(message);
	}
}