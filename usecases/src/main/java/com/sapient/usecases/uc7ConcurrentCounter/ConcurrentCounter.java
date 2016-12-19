package com.sapient.usecases.uc7ConcurrentCounter;

public class ConcurrentCounter {

	private int number;

	public ConcurrentCounter(int initValue) {
		this.number = initValue;
	}

	public ConcurrentCounter() {
		number = 0;
	}

	public synchronized int incrementAndGet() {
		return number++;
	}
}
