package com.bhavik.socketcommunication.nio2.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.bhavik.socketcommunication.nio2.util.NamedThreadFactory;

/**
 * ServerManager is responsible for starting, stopping and restarting
 * AysncCounterServers.
 * <P>
 * Clients should never directly interact with CounterServer instances, instead
 * they send messages over TCP / IP. Tests or Server programs to start Servers
 * should use this API to manage Servers.
 * <P>
 * ServerManager is a singleton and follow Singleton pattern as described by
 * Josh Bloch Item 3, Effective Java Second Edition. This approach provides
 * guarentees against multiple instantiation even in the face of sophisticated
 * serialization or reflection attacks.
 * <P>
 * ServerManager contains some simple Server Management logic such as checking
 * that a specified Server is not already started when a request is made to
 * start it.
 * <P>
 * ServerManager has its own thread pool. Servers are started on these threads.
 * <P>
 * ServerManager can write counter values to a file. This is useful for example
 * if you have to restart a server. If a Server is the first Server to start up
 * it will read from the file.
 * <P>
 * <dev-note> Only allowing starting and stopping via the ServerManager
 * simplifies threading concerns. ServerManager takes a coarse grained locking
 * approach when starting and stopping servers. For example, it will lock when
 * starting a server befores it read any countervalue from a file, then it will
 * check the server is up and running before returning the lock. It will do
 * similar when stopping a server. Preventing some complex races conditions.
 * <P>
 */
public enum ServerManager {
	INSTANCE;

	private ConcurrentHashMap<String, AsyncEchoServer> servers = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, InetSocketAddress> serverAddresses = new ConcurrentHashMap<>();
	private ExecutorService executors = Executors.newCachedThreadPool(new NamedThreadFactory("ServerManager_Cache_TP_Thread"));

	private Logger logger = Logger.getLogger(ServerManager.class.getName());
	private String counterFileName = "counterValue.txt"; // counter file name
															// with default
															// value. might be
															// changed for tests
															// etc.

	/**
	 * Starts a Server on a specified addess.
	 * 
	 * @param serverName
	 * @param netSocketAddress
	 * @return Response from pinging started server.
	 */
	public String startServer(String serverName, InetSocketAddress netSocketAddress) {
		logger.info(">>startServer(serverName=" + serverName + ",InetSocketAddress=" + netSocketAddress + ")");
		AsyncEchoServer server = null;
		String returnMessage = null;
		try {
			// Is this the first server?
			if (!servers.contains(serverName)) {
				server = new AsyncEchoServer(serverName);
				servers.put(serverName, server);
			} else {
				server = getServer(serverName);
			}

			if (serverAddresses.contains(serverName)) {
				// server already started on another port?
				throw new RuntimeException("Server already started on " + serverAddresses.get(serverName));
			}

			this.serverAddresses.put(serverName, netSocketAddress);
			server.open(netSocketAddress);
			// begin accepting connections
			executors.execute(server);

			// Now give the server a sec
			Thread.sleep(1500);

			// And ping to make sure all ok.
			returnMessage = pingServer(serverName);

		} catch (InterruptedException | IOException | ExecutionException ex) {
			throw new RuntimeException("Problem startig server", ex);
		}
		logger.info("Server " + serverName + " on " + netSocketAddress + ",open for business");
		return returnMessage;
	}

	/**
	 * Example calling of this API: <code>
	 * ServerManager.startServer('myServer', startServerInetAddress.getLocalHost().getHostName(), 999);
	 * </code>
	 * 
	 * @param serverName
	 * @param hostName
	 * @param port
	 */
	public String startServer(String serverName, String hostName, int port) {
		String returnMessage = null;
		try {
			InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost().getHostName(), port);
			returnMessage = startServer(serverName, socketAddress);
		} catch (IOException ex) {
			// problem with starting server.
			throw new RuntimeException("Problem startig server", ex);
		}
		return returnMessage;
	}

	/**
	 * Pings servers to ensure it is up.
	 * 
	 * @param serverName
	 * @return Response from Ping
	 */
	public String pingServer(String serverName) {
		String returnResult = null;
		if (servers.containsKey(serverName)) {
			try {
				AsynchronousSocketChannel asyncSocketChannel = AsynchronousSocketChannel.open();
				// Connecting to server
				logger.info("Connecting to server... " + serverName);
				InetSocketAddress socketAddress = this.serverAddresses.get(serverName);
				Future<Void> connectFuture = asyncSocketChannel.connect(socketAddress);
				connectFuture.get();
				ByteBuffer messageByteBuffer = ByteBuffer.wrap("PING".getBytes());

				// wait for the response
				Future<Integer> futureWriteResult = asyncSocketChannel.write(messageByteBuffer);
				futureWriteResult.get();

				// Now wait for return message.
				ByteBuffer returnMessage = ByteBuffer.allocate(100);
				Future<Integer> futureReadResult = asyncSocketChannel.read(returnMessage);
				futureReadResult.get();

				// wait for the response
				returnResult = new String(returnMessage.array());
				messageByteBuffer.clear();

				asyncSocketChannel.close();
				long endTime = System.currentTimeMillis();

			} catch (InterruptedException | ExecutionException | IOException e) {
				throw new RuntimeException("Problem pinging server", e);
			}
		} else {
			// cannot ping a server that does not exist
			returnResult = "SERVER NOT REACHABLE";
		}
		logger.info("<<ping() return=" + returnResult);
		return returnResult;
	}

	/**
	 * Stops server. If writeToFile is true and this is the last server, counter
	 * value will be written to file.
	 * 
	 * @param serverName
	 * @param writeToFile
	 */
	public void stopServer(String serverName, boolean writeToFile) {
		try {
			AsyncEchoServer asyncJava7Server = getServer(serverName);
			asyncJava7Server.stopServer();
			String fileString = "Server " + serverName + " stopped at " + new Date().toString();
			logger.info(fileString);

			// Now remove.
			this.servers.remove(serverName);
			this.serverAddresses.remove(serverName);

		} catch (IOException | InterruptedException | ExecutionException ex) {
			throw new RuntimeException("Problem stopping server", ex);
		}
	}

	/**
	 * Lists all Servers running.
	 * 
	 * @return
	 */
	public List<String> listServersRunning() {
		ArrayList<String> serverNames = Collections.list(servers.keys());
		return serverNames;
	}

	public InetSocketAddress getServerPort(String serverName) {
		return this.serverAddresses.get(serverName);
	}

	public void setCounterValueFileName(String counterFileName) {
		this.counterFileName = counterFileName;
	}

	public String getCounterValueFileName() {
		return this.counterFileName;
	}

	/**
	 * Restarts Server.
	 * 
	 * @param serverName
	 */
	public void restartServer(String serverName) {
		InetSocketAddress netSocketAddress = this.serverAddresses.get(serverName);
		stopServer(serverName, true);
		startServer(serverName, netSocketAddress);
	}

	private AsyncEchoServer getServer(String serverName) throws IOException, InterruptedException, ExecutionException {
		AsyncEchoServer server = servers.get(serverName);
		return server;
	}

}