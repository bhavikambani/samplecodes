package cyberoam.communication.socketcommunication.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

import cyberoam.communication.socketcommunication.util.SocketProperties;

/**
 * The Class ClientRequestSocket.
 * 
 * @author Bhavik Aniruddh Ambani
 * @since X.3.7.3.0
 */
public class ClientReceiverSocket extends Observable implements Runnable {

	private static final String MODULE = new String("ClientRequestSocket");
	

	/** The client socket. */
	private Socket clientSocket;
	
	private Thread currentThread;
	
	/**
	 * Instantiates a new client request socket.
	 *
	 * @param socket
	 *            the socket
	 */
	public ClientReceiverSocket(Socket socket) {
		this.clientSocket = socket;
		try {
			socket.setSoTimeout(SocketProperties.SOCKET_TIMEOUT);
		} catch (SocketException e) {
			System.out.println(MODULE + "SocketException occured while setting timeout property");
			e.printStackTrace();
		}
		initSocket();
	}

	public void run() {
		readDataFromSocket();

	}
	
	public void initSocket(){
		try {
			currentThread = new Thread(this,"Thread-ClientRequestSocket#" + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
			currentThread.start();
		} catch (Exception e) {
			System.out.println(MODULE + "Exception while processing cleint's ClientRequestSocket#" + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " ##### " + e);
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		currentThread.interrupt();
		if (clientSocket != null && clientSocket.isConnected() ) {
			try {
				clientSocket.shutdownInput();
			} catch (IOException e) {
				System.out.println(MODULE + "Exception while shuttingdown socket #" + clientSocket + " ### " + e.getMessage());
				e.printStackTrace();
			}
			try {
				clientSocket.shutdownOutput();
			} catch (IOException e) {
				System.out.println(MODULE + "Exception while shuttingdown socket #" + clientSocket + " ### " + e.getMessage());
				e.printStackTrace();
			}
			try {
				clientSocket.close();
			} catch (IOException e) {
				System.out.println(MODULE + "Exception while shuttingdown socket #" + clientSocket + " ### " + e.getMessage());
				e.printStackTrace();
			}
		}
		notifyObservers(clientSocket);
	}
	
	private void readDataFromSocket() {
		System.out.println(MODULE + "Client Socket Bound: " + clientSocket.getPort() + "" + clientSocket.getInetAddress());
		try {
			ObjectInputStream objInputStream = new ObjectInputStream(clientSocket.getInputStream());
			Object tmpObj;
			ClientDataQueueManager dataQueueManager = ClientDataQueueManager.getInstance();
			while (!Thread.interrupted() && (tmpObj = objInputStream.readObject()) != null) {
				System.out.println(MODULE + "### Received Object from Socket: " + tmpObj);
				dataQueueManager.addDataToQueue(tmpObj);
			}
			objInputStream.reset();
			shutdown();
		} catch (IOException e) {
			System.out.println(MODULE + "IOException while getting InputStream/Read Object from socket ### " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println(MODULE + "ClassNotFoundException while Reading Object from socket ### " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println(MODULE + "Exit from readDataFromSocket()");

	}

}
