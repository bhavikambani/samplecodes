package com.sapient.usecases.uc3_4ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The Class MultipleProducerConsumer will implement use case for producer
 * consumer problem having multiple producers and multiple consumers
 * 
 * @author Bhavik Ambani.
 */
public class MultipleProducerConsumer {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(5000);
		Thread p1 = new Thread(new Producer(queue, 10, "P1"));
		Thread p2 = new Thread(new Producer(queue, 10, "P2"));
		Thread p3 = new Thread(new Producer(queue, 10, "P3"));
		Thread c1 = new Thread(new Consumer(queue, "C1"));
		Thread c2 = new Thread(new Consumer(queue, "C2"));
		Thread c3 = new Thread(new Consumer(queue, "C3"));
		Thread c4 = new Thread(new Consumer(queue, "C4"));
		Thread c5 = new Thread(new Consumer(queue, "C5"));
		Thread p4 = new Thread(new Producer(queue, 10, "P4"));
		Thread p5 = new Thread(new Producer(queue, 10, "P5"));
		Thread p6 = new Thread(new Producer(queue, 10, "P6"));
		Thread p7 = new Thread(new Producer(queue, 10, "P7"));
		Thread p8 = new Thread(new Producer(queue, 10, "P8"));
		Thread p9 = new Thread(new Producer(queue, 10, "P9"));
		Thread p10 = new Thread(new Producer(queue, 10, "P10"));
		Thread c6 = new Thread(new Consumer(queue, "C6"));
		Thread c7 = new Thread(new Consumer(queue, "C7"));
		Thread c8 = new Thread(new Consumer(queue, "C8"));
		Thread c9 = new Thread(new Consumer(queue, "C9"));
		Thread c10 = new Thread(new Consumer(queue, "C10"));

		p1.start();
		c1.start();
		p2.start();
		c2.start();
		p3.start();
		c5.start();
		p4.start();
		c3.start();
		p5.start();
		c7.start();
		p6.start();
		c4.start();
		p7.start();
		c9.start();
		c10.start();
		p8.start();
		c6.start();
		p9.start();
		p10.start();
		c8.start();

	}

}

class Consumer implements Runnable {
	private BlockingQueue<Integer> queue;
	private final String name;

	public Consumer(BlockingQueue<Integer> queue, String name) {
		this.queue = queue;
		this.name = name;
	}

	public void run() {
		while (true) {
			try {
				synchronized (queue) {
					if (queue.isEmpty()) {
						queue.wait();
					}
					System.out.println("Consumed # " + queue.take() + ",by # " + name);
					queue.notifyAll();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class Producer implements Runnable {
	private BlockingQueue<Integer> queue;
	private int itemsToBeProduced;
	private String name;

	public Producer(BlockingQueue<Integer> queue, int itemsToBeProduced, String name) {
		this.queue = queue;
		this.itemsToBeProduced = itemsToBeProduced;
		this.name = name;
	}

	public void run() {
		for (int i = 0; i < itemsToBeProduced; i++) {
			try {
				System.out.println("Produced # " + i + ", by # " + name);
				queue.put(i);
			} catch (InterruptedException e) {
				System.out.println("Producer interrupted # " + e);
				e.printStackTrace();
			}
		}
	}

}