package cyberoam.communication.socketcommunication.server;

import java.util.concurrent.LinkedBlockingQueue;

public class ClientDataQueueManager {
	private final String MODULE = new String("[DataQueueManager] ");
	private LinkedBlockingQueue<Object> queue = null;
	private static volatile ClientDataQueueManager instance = null;

	private ClientDataQueueManager() {
		System.out.println(MODULE + "Instance Initialized");
		queue = new LinkedBlockingQueue<Object>();
	}

	public static ClientDataQueueManager getInstance() {
		if (instance == null) {
			synchronized (ClientDataQueueManager.class) {
				if (instance == null) {
					instance = new ClientDataQueueManager();
				}
			}
		}
		return instance;
	}

	public LinkedBlockingQueue<Object> getClientDataQueue() {
		return queue;
	}

	public Object getDataFromQueue() {
		return queue.poll();
	}

	public void addDataToQueue(Object o) {
		boolean isRequestSubmitted = queue.offer(o);
		if (!isRequestSubmitted) {
			System.out.println(MODULE + "Pausing addDataToQueue. Reason: Request queue is full");
			Thread.yield();
			isRequestSubmitted = queue.offer(o);
			if (!isRequestSubmitted) {
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					System.out.println(MODULE + "InterruptedException while sleeping thread # " + e);
					e.printStackTrace();
				}
				queue.offer(o);
			}
		}
	}
}
