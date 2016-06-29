package cyberoam.communication.socketcommunication.server;

import net.sf.json.JSONObject;
import cyberoam.communication.socketcommunication.processor.ISocketDataProcessor;

public class SocketDataProcessor extends Thread {
	private static final String MODULE = "[SocketDataProcessor]: ";
	private Object dataObject;
	private final ISocketDataProcessor iSocketDataHandler;

	public SocketDataProcessor(Object dataObject) {
		this.setName("SocketDataProcessor");
		this.dataObject = dataObject;
		iSocketDataHandler = null; // Need to initialize the following object
	}

	public void run() {
		boolean status = false;
		try {
			if (dataObject == null) {
				System.out.println(MODULE + "Received Object is null: " + dataObject);
				return;
			}
			if (dataObject instanceof JSONObject) {
				// Call Handler of JSON
			} else {
				// Call Handler of Simple Object
				status = iSocketDataHandler.processSocketData(dataObject);
			}
			if (status == false) {
				System.out.println(MODULE + "Obj: " + dataObject + " process status: " + status);
			}
		} catch (Exception e) {
			System.out.println(MODULE + "Exception occured while processing: " + e);
		}
	}

	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}
}