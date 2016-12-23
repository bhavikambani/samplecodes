package com.sapient.usecases.uc9_MultiThreadDownloadManager;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Class DownloadManager which is multi threaded download manager program.
 * 
 * @author Bhavik Ambani
 */
public class DownloadManager {

	/** The Constant INSTANCE. */
	public static final DownloadManager INSTANCE = new DownloadManager();

	/** The download executor service. */
	private final ExecutorService downloadExecutorService = Executors.newFixedThreadPool(10);

	/** The downloads. */
	private final Map<String, AtomicInteger> downloads = new ConcurrentHashMap<String, AtomicInteger>();

	/** The shutdown flag. */
	private final AtomicBoolean shutdownFlag = new AtomicBoolean();

	/**
	 * Instantiates a new download manager.
	 */
	private DownloadManager() {
		startStatusThread();
	}

	/**
	 * Shutdown.
	 */
	public void shutdown() {
		downloadExecutorService.shutdown();
		try {
			downloadExecutorService.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		shutdownFlag.set(true);
	}

	/**
	 * Start status thread.
	 */
	private void startStatusThread() {
		new Thread(new Runnable() {
			public void run() {
				while (!shutdownFlag.get()) {
					try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (downloads.isEmpty()) {
						continue;
					}
					StringBuilder status = new StringBuilder();
					for (Map.Entry<String, AtomicInteger> downloadsEntry : downloads.entrySet()) {
						String item = downloadsEntry.getKey();
						int percentage = downloadsEntry.getValue().get();
						String percentValue = (percentage == -1) ? "Not Started" : percentage + "%";
						if (status.length() == 0) {
							status.append(item + "=" + percentValue);
						} else {
							status.append(", " + item + "=" + percentValue);
						}
						if (percentage == 100) {
							downloads.remove(item);
						}
					}
					System.out.println("***STATUS*** -- " + status.toString());
				}
			}
		}).start();
	}

	/**
	 * Download.
	 *
	 * @param item
	 *            the item
	 * @return true, if successful
	 */
	public boolean download(String item) {
		boolean returnValue = false;
		downloads.put(item, new AtomicInteger(-1));
		downloadExecutorService.submit(new DownloadTask(item));
		return returnValue;
	}

	/**
	 * The Class DownloadTask.
	 */
	private static class DownloadTask implements Callable<Boolean> {

		/** The item. */
		private final String item;

		/** The manager. */
		private final DownloadManager manager;

		/**
		 * Instantiates a new download task.
		 *
		 * @param item
		 *            the item
		 */
		public DownloadTask(String item) {
			this.item = item;
			this.manager = DownloadManager.INSTANCE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		public Boolean call() throws Exception {
			AtomicInteger percentage = manager.downloads.get(item);
			percentage.set(0);
			System.out.println("Downloading " + item + " started");
			while (percentage.get() < 100) {
				Thread.sleep(100L);
				percentage.incrementAndGet();
			}
			System.out.println("Downloading " + item + " completed");
			return true;
		}

	}
}
