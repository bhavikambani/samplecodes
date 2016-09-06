package com.bhavik.socketcommunication.nio2.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread factory classe where you can name threads grouping.
 * 
 * @author Bhavik Aniruddh Ambani
 *
 */
public class NamedThreadFactory implements ThreadFactory {

	private String name = "";

	public NamedThreadFactory(String name) {
		this.name = name;
	}

	final AtomicInteger id = new AtomicInteger(1);

	@Override
	public Thread newThread(Runnable r) {
		final Thread thread = new Thread(r);
		thread.setName(name + "-" + id.incrementAndGet());
		return thread;
	}
}