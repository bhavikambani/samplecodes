package com.sapient.usecases.uc4CustomThreadPool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * The Class CustomThreadPool which implements the Use Case # 4.
 * 
 * @author Bhavik Ambani
 */
public class CustomThreadPool {

	/** The task queue. */
	TaskQueue<Runnable> taskQueue = null;

	/** The shut down flag. */
	boolean shutDownFlag = false;

	/** The is shut down now flag. */
	boolean isShutDownNowFlag = false;

	/** The workers list. */
	List<Thread> workersList = new ArrayList<Thread>();

	/**
	 * Instantiates a new custom thread pool.
	 *
	 * @param size
	 *            the size
	 */
	public CustomThreadPool(int size) {
		taskQueue = new TaskQueue<Runnable>(size);
		for (int i = 0; i < size; i++) {
			Thread thread = new Thread(new TaskWorker(taskQueue, this), "Thread:" + i);
			System.out.println("Thread: " + i + " Started");
			workersList.add(thread);
			thread.start();
		}
	}

	/**
	 * Submit task.
	 *
	 * @param r
	 *            the r
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public void submitTask(Runnable r) throws InterruptedException {
		if (!shutDownFlag) {
			taskQueue.put(r);
		} else {
			throw new InterruptedException("ShutDown has invoked  no new tasks will be taken");
		}
	}

	/**
	 * Shut down.
	 *
	 * @return true, if successful
	 */
	public boolean shutDown() {
		return shutDownFlag = true;
	}

	/**
	 * Shut down now.
	 *
	 * @return true, if successful
	 */
	public boolean shutDownNow() {
		return isShutDownNowFlag = true;
	}

	public static void main(String[] args) throws InterruptedException {

		CustomThreadPool customThreadPool = new CustomThreadPool(3);
		for (int i = 0; i < 10; i++) {
			customThreadPool.submitTask(new WorkerTask(i));
		}
		customThreadPool.shutDownNow();
	}

}

class WorkerTask implements Runnable {
	int num;

	public WorkerTask(int num) {
		this.num = num;
	}

	public void run() {
		System.out.println("Task: " + num + " is executed by a thread" + Thread.currentThread().getName());
	}

}

class TaskQueue<E> implements CustomQueue<E> {

	public Queue<E> list = new LinkedList<E>();
	boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	int maxSize;

	public TaskQueue(int maxSize) {
		this.maxSize = maxSize;

	}

	public synchronized void put(E e) {
		if (list.size() == maxSize) {
			try {
				wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		list.add(e);
		notifyAll();
	}

	public synchronized E take() {
		E e = null;
		if (list.size() == 0) {
			try {
				wait();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		e = list.remove();
		notifyAll();
		return e;
	}

}

class TaskWorker implements Runnable {
	TaskQueue<Runnable> taskQueue;
	CustomThreadPool threadPool;

	public TaskWorker(TaskQueue<Runnable> taskQueue, CustomThreadPool pool) {
		this.taskQueue = taskQueue;
		this.threadPool = pool;
	}

	public void run() {
		while (true) {
			if (threadPool.isShutDownNowFlag) {
				Thread.currentThread().interrupt();
				try {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					System.out.println("Shutdown now has been called aborting the tasks");

				}
			}
			Runnable task = taskQueue.take();
			task.run();

		}

	}

}

interface CustomQueue<E> {

	void put(E e);

	E take();
}
