package cyberoam.communication.socketcommunication.server;

import java.util.List;

/**
 * The Class SocketCommunicationShutdownHook, this shutdown hook will be called
 * at the time of shutdown of the JVM process. If any of the client sockets are
 * connected, then they will be shutdown properly before shutdown of the JVM
 * 
 * @author Bhavik Aniruddh Ambani
 * 
 * @since X.3.7.3.0
 */
public class SocketCommunicationShutdownHook implements Runnable {

	/** The current thread. */
	private Thread currentThread;

	/**
	 * Instantiates a new socket communication shutdown hook.
	 */
	public SocketCommunicationShutdownHook() {
		currentThread = new Thread(this);
		currentThread.start();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		ClientReceiverSocketObserver clientReceiverSocketObserver = ClientReceiverSocketObserver.getInstance();
		clientReceiverSocketObserver.stopCommunicationChannel();
		List<ClientReceiverSocket> clientReceiverSocketList = clientReceiverSocketObserver.getClientReceiverSocketList();
		do {
			for (ClientReceiverSocket clientReceiverSocket : clientReceiverSocketList) {
				clientReceiverSocket.shutdown();
			}
			clientReceiverSocketList = clientReceiverSocketObserver.getClientReceiverSocketList();
		} while (clientReceiverSocketList == null || clientReceiverSocketList.size() == 0);
	}
}
