package com.sapient.usecases.uc7ConcurrentCounter;

public class Solution {

	public static void main(String[] args) {

		ConcurrentCounter cc = new ConcurrentCounter(1);

		Thread t1 = new Thread(new NumberProcessor(cc));
		Thread t2 = new Thread(new NumberProcessor(cc));
		Thread t3 = new Thread(new NumberProcessor(cc));
		Thread t4 = new Thread(new NumberProcessor(cc));
		Thread t5 = new Thread(new NumberProcessor(cc));
		Thread t6 = new Thread(new NumberProcessor(cc));
		Thread t7 = new Thread(new NumberProcessor(cc));
		Thread t8 = new Thread(new NumberProcessor(cc));
		Thread t9 = new Thread(new NumberProcessor(cc));
		Thread t10 = new Thread(new NumberProcessor(cc));

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
		t10.start();

	}

}

class NumberProcessor implements Runnable {
	private ConcurrentCounter cc;

	public NumberProcessor(ConcurrentCounter cc) {
		this.cc = cc;
	}

	public void run() {
		for (int i = 0; i < 1000; i++) {
			System.out.println(cc.incrementAndGet());
		}
	}

}