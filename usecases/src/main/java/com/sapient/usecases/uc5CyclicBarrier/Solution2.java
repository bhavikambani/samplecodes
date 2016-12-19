package com.sapient.usecases.uc5CyclicBarrier;

import java.util.concurrent.atomic.AtomicInteger;

public class Solution2 {

	public static void main(String args[]) {
		CustomCyclicBarrier barrier = new CustomCyclicBarrier(4);

		Thread t1 = new Thread(new ThreadService(1, barrier, Thread.currentThread()));
		Thread t2 = new Thread(new ThreadService(2, barrier, Thread.currentThread()));
		Thread t3 = new Thread(new ThreadService(3, barrier, Thread.currentThread()));
		Thread t4 = new Thread(new ThreadService(4, barrier, Thread.currentThread()));

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		barrier.await();

		System.out.println("All threads completed execution");
	}
}

class ThreadService implements Runnable {
	private final int data;
	private final CustomCyclicBarrier barrier;
	private final Thread t;

	public ThreadService(int data, CustomCyclicBarrier barrier, Thread t) {
		this.data = data;
		this.barrier = barrier;
		this.t = t;
	}

	public void run() {
		try {
			System.out.println("Process#" + data + ", started.");
			Thread.sleep(200);
			System.out.println("Process#" + data + ", first milestone achieved.");
			t.join();
			// barrier.await();
			System.out.println("Process#" + data + ", completed.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		barrier.reduceCount();
	}

}

class CustomCyclicBarrier {
	volatile AtomicInteger count;

	public CustomCyclicBarrier(int count) {
		this.count = new AtomicInteger(count);
	}

	public void await() {
		while (count.intValue() != 0) {
			synchronized (this) {
				try {
					this.wait(100);
				} catch (InterruptedException e) {
					System.out.println("CyclicBarrierInterrupted.");
					return;
				}
			}
		}
		System.out.println("Custom Cyclic barrier completed successfully.");
	}

	public void reduceCount() {
		count.decrementAndGet();
	}

}