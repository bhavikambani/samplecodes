package cyberoam.communication.socketcommunication.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientReceiverSocketObserver extends Thread implements Observer {
	private ConcurrentHashMap<ClientReceiverSocket, Object> clientReceiverSockets;
	private static volatile ClientReceiverSocketObserver instance;
	private Object PRESENT;
	private boolean communicationIsWorking;

	private ClientReceiverSocketObserver() {
		clientReceiverSockets = new ConcurrentHashMap<ClientReceiverSocket, Object>();
		PRESENT = new Object();
		communicationIsWorking = true;
	}

	public void stopCommunicationChannel() {
		communicationIsWorking = false;
	}
	
	public static ClientReceiverSocketObserver getInstance() {
		if (instance == null) {
			synchronized (ClientReceiverSocketObserver.class) {
				if (instance == null) {
					instance = new ClientReceiverSocketObserver();
				}
			}
		}
		return instance;
	}

	public void addClientReceiverSocketForObservation(
			ClientReceiverSocket clientReceiverSocket) {
		if (communicationIsWorking) {
			synchronized (clientReceiverSockets) {
				clientReceiverSockets.put(clientReceiverSocket, PRESENT);
			}
		}
	}

	public List<ClientReceiverSocket> getClientReceiverSocketList() {
		List<ClientReceiverSocket> receiverSocketList = new ArrayList<ClientReceiverSocket>();
		synchronized (clientReceiverSockets) {
			Set<ClientReceiverSocket> keys = clientReceiverSockets.keySet();
			for (ClientReceiverSocket s : keys) {
				receiverSocketList.add(s);
			}
		}
		return receiverSocketList;
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
}
