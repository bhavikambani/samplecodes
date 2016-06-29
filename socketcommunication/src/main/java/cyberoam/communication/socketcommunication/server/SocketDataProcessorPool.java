package cyberoam.communication.socketcommunication.server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketDataProcessorPool {

	private static final String MODULE ="[SocketDataProcessorPool]: ";
	private BlockingQueue<Runnable> worksQueue = null;
	private ThreadPoolExecutor executor = null;
	private static SocketDataProcessorPool socketCommThreadPoolExecutor = null;
	
	private static int workQueueSize;
	private static int coreThreadPoolSize;
	private static int maxThreadPoolSize;
	private static int keepAliveTime;
	
	static{
		initialize();
	}
	
	private static void initialize() {
		try {
			workQueueSize = Integer.valueOf(iClientServicesDAO.getValueByKey("socketComm_workqueuesize"));
		} catch (Exception e) {
			workQueueSize = 200;
		}
		try {
			keepAliveTime = Integer.valueOf(iClientServicesDAO.getValueByKey("socketComm_keepalivetime"));
		} catch (Exception e) {
			keepAliveTime = 10;
		}
		try {
			coreThreadPoolSize = Integer.valueOf(iClientServicesDAO.getValueByKey("socketComm_corethreadsize"));
		} catch (Exception e) {
			coreThreadPoolSize = 10;
		}
		try {
			maxThreadPoolSize = Integer.valueOf(iClientServicesDAO.getValueByKey("socketComm_maxthreadsize"));
		} catch (Exception e) {
			maxThreadPoolSize = 15;
		}
		
		System.out.println(MODULE + "******        Init Params       *******");
		System.out.println(MODULE + "coreThreadPoolSize: " + coreThreadPoolSize);
		System.out.println(MODULE + "maxThreadPoolSize: " + maxThreadPoolSize);
		System.out.println(MODULE + "workQueueSize: " + workQueueSize);
		System.out.println(MODULE + "keepAliveTime: " + keepAliveTime);
		System.out.println(MODULE + "****** End of Initialized Params ******");
	}
	
	SocketDataProcessorPool(){
		worksQueue = new LinkedBlockingQueue<Runnable>(workQueueSize);
		RejectedExecutionHandler socketCommRejectionHandler = new SocketCommRejectionHandler(); 
		executor = new ThreadPoolExecutor(coreThreadPoolSize,maxThreadPoolSize,keepAliveTime,TimeUnit.SECONDS, worksQueue,socketCommRejectionHandler);
		//executor.allowCoreThreadTimeOut(true);
	}

	/**
	 * Will Add Runnable into Thread Pool To run.
	 * @param runnableThread
	 */
	public  void addThread(Runnable runnableThread){
		System.out.printf("[SocketCommThreadPoolExecutor] [monitor] [%d/%d] Active: %d, QueueSize: %d, Completed: %d, Task: %d",
						this.executor.getPoolSize(),
						this.executor.getCorePoolSize(),
						this.executor.getActiveCount(), this.worksQueue.size(),
						this.executor.getCompletedTaskCount(),
						this.executor.getTaskCount());
		executor.execute(runnableThread);
	}
	
	public static SocketCommThreadPoolExecutor getSocketCommThreadPoolExecutor() {
			if(socketCommThreadPoolExecutor == null){
				synchronized (SocketCommThreadPoolExecutor.class) {
					if(socketCommThreadPoolExecutor == null){
						System.out.println(MODULE + "In getSocketCommThreadPoolExecutor() to create Singlton SocketCommThreadPoolExecutor.");
						socketCommThreadPoolExecutor = new SocketCommThreadPoolExecutor();
					}
				}
			}
			return socketCommThreadPoolExecutor;
	}
	
	public static void main(String arg[]){
	}
	
	protected void finalize() throws Throwable {
			System.out.println(MODULE + "Finalize called ....");
			super.finalize();
	}
	
}
