package com.sapient.usecases.uc5CyclicBarrier;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * The Class Solution will implement the custom implementation of
 * {@link CyclicBarrier} and {@link CountDownLatch}.
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class Solution1 {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String args[]) {
		Thread t1 = new Thread(new Task("Task#1"));
		Thread t2 = new Thread(new Task("Task#2"));
		Thread t3 = new Thread(new Task("Task#3"));

		try {
			t1.start();
			t2.start();
			t3.start();

			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Execution of all tasks completed.");
	}
}

class Task implements Runnable {
	private final String name;

	public Task(final String name) {
		this.name = name;
	}

	public void run() {
		try {
			System.out.println("Task # " + name + " started.");
			Thread.sleep(1000);
			System.out.println("Task # " + name + " completed.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}