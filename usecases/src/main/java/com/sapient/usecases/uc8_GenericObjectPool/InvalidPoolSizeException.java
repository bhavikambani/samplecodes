package com.sapient.usecases.uc8_GenericObjectPool;

/**
 * The Class InvalidPoolSizeException which will be thrown when Pool size is
 * passed as 0 of negative.
 * 
 * @author Bhavik Ambani
 */
public class InvalidPoolSizeException extends Exception {

	/**
	 * Instantiates a new invalid pool size exception.
	 *
	 * @param message
	 *            the message
	 */
	public InvalidPoolSizeException(String message) {
		super(message);
	}
}