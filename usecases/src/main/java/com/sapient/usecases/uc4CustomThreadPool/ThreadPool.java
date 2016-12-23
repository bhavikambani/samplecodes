package com.sapient.usecases.uc4CustomThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("rawtypes")
public class ThreadPool {

	boolean shutdownInitiated = false;
	private BlockingQueue taskQueue = null;
	@SuppressWarnings("unused")
	private final int poolSize;

	private final List<Worker> workers;

	@SuppressWarnings("unchecked")
	public ThreadPool(final int poolSize, final int tasksSize) {
		this.poolSize = poolSize;
		taskQueue = new LinkedBlockingQueue(tasksSize);
		workers = new ArrayList<Worker>(poolSize);

		for (int i = 0; i < poolSize; i++) {
			workers.add(new Worker(taskQueue));
		}

		for (Thread t : workers) {
			t.start();
		}

	}

	public synchronized void shutdown() {
		shutdownInitiated = true;

		for (Worker t : workers) {
			t.interrupt();
			t.shutdownTask();
		}
	}

}

class Worker extends Thread {
	private BlockingQueue<Runnable> queue;
	private boolean shutdownInitiated = false;

	public Worker(BlockingQueue<Runnable> queue) {
		this.queue = queue;
	}

	public void run() {
		while (!shutDownInitiated()) {
			Runnable task;
			try {
				task = queue.take();
				task.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized boolean shutDownInitiated() {
		return shutdownInitiated;
	}

	public synchronized void shutdownTask() {
		shutdownInitiated = true;
	}

}

class Task implements Runnable {
	private final String taskName;

	public Task(String taskName) {
		this.taskName = taskName;
	}

	public void run() {
		System.out.println("Starting task # " + taskName);
		System.out.println("Completing task # " + taskName);
	}

}
