package cyberoam.communication.socketcommunication.server;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class SocketCommRejectionHandler implements RejectedExecutionHandler {
	public static String MODULE = new String("[SocketCommRejectionHandler] ");

	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		try {
			if (runnable != null) {
				SocketDataProcessor sdp = (SocketDataProcessor) runnable;
				try {
					Thread.sleep(10);
				} catch (Exception e) {
				}
				System.out.println(MODULE + "Inside RejectionTask: " + sdp.getDataObject());
				ClientDataQueueManager.getInstance().addDataToQueue(sdp.getDataObject());
			} else {
				System.out.println(MODULE + "Rejected Runnable was null: " + runnable);
			}
		} catch (Exception e) {
			System.err.println(MODULE + "Error in rejectedExecution  :  " + e);
		}
	}
}
