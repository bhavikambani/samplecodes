package com.sapient.usecases.uc8_GenericObjectPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The Abstract Class GenericObjectPool which will implement the pooling of the
 * object. Extended class must need to override the method
 * {@link #instantiateInstance()}
 *
 * @param <T>
 *            the generic type
 * 
 * @author Bhavik Ambani
 */
public abstract class GenericObjectPool<T> {

	/** The object pool queue. */
	private final BlockingQueue<T> objectPoolQueue;

	/** The pool size. */
	private final int poolSize;

	/**
	 * Instantiates a new generic object pool.
	 *
	 * @param size
	 *            the size
	 * @throws InvalidPoolSizeException
	 *             the invalid pool size exception
	 */
	public GenericObjectPool(int size) throws InvalidPoolSizeException {
		this.poolSize = size;
		if (poolSize <= 0) {
			throw new InvalidPoolSizeException("Poolsize is passed as # " + poolSize);
		}
		objectPoolQueue = new ArrayBlockingQueue<T>(size);
		for (int i = 0; i < size; i++) {
			try {
				objectPoolQueue.put(instantiateInstance());
			} catch (InterruptedException e) {
				System.err.println("Exception while create object pool # " + e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Instantiate instance.
	 *
	 * @return the t
	 */
	public abstract T instantiateInstance();

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public T getObject() {
		T returnObject = null;
		if (objectPoolQueue.size() > 0) {
			try {
				return objectPoolQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return returnObject;
	}

	/**
	 * Release.
	 *
	 * @param t
	 *            the t
	 */
	public void release(T t) {
		if (t != null) {
			objectPoolQueue.offer(t);
		}
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		objectPoolQueue.clear();
	}
}
