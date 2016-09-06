package com.bhavik.socketcommunication.nio2.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

/**
 * AsyncCounterClient is used to connect to remote the AsyncCounterServer.
 * Multiple AsyncCounterClients can connect to AsyncCounterServer at the same
 * time. There a command line program which will run AsyncCounterClient
 * instances.
 * 
 * @author Bhavik Aniruddh Ambani
 */
public class AsyncEchoClient {

	private Logger logger = Logger.getLogger(AsyncEchoClient.class.getName());

	private String serverName = null;
	private int port;
	private String clientName;

	public final static int MESSAGE_INPUT_SIZE = 128;

	private final static int WAIT_TIME = 3;

	public AsyncEchoClient(String clientName, String serverName, int port) throws IOException {
		logger.info(
				">>AsynCounterClient(clientName=" + clientName + ",serverName=" + serverName + ",port=" + port + ")");
		this.clientName = clientName;
		this.serverName = serverName;
		this.port = port;
	}

	private AsynchronousSocketChannel connectToServer(int waitTime)
			throws IOException, InterruptedException, ExecutionException, TimeoutException {
		AsynchronousSocketChannel asyncSocketChannel = AsynchronousSocketChannel.open();
		Future<Void> connectFuture = null;

		// Connecting to server
		logger.info("Connecting to server... " + serverName + ",port=" + port);
		connectFuture = asyncSocketChannel.connect(new InetSocketAddress("Alex-PC", this.port));

		// You have two seconds to connect. This will throw exception if server
		// is not there.
		connectFuture.get(waitTime, TimeUnit.SECONDS);

		asyncSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 128 * MESSAGE_INPUT_SIZE);
		asyncSocketChannel.setOption(StandardSocketOptions.SO_SNDBUF, 128 * MESSAGE_INPUT_SIZE);
		asyncSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

		return asyncSocketChannel;
	}

	public String sendMessage(String request) {
		logger.info(">> sendMessage(request=" + request + ")");
		String response = null;
		AsynchronousSocketChannel asyncSocketChannel = null;
		try {
			asyncSocketChannel = connectToServer(WAIT_TIME);
			ByteBuffer messageByteBuffer = ByteBuffer.wrap(request.getBytes());
			Future<Integer> futureWriteResult = asyncSocketChannel.write(messageByteBuffer);
			futureWriteResult.get();

			// Now wait for return message.
			ByteBuffer returnMessage = ByteBuffer.allocate(MESSAGE_INPUT_SIZE);
			Future<Integer> futureReadResult = asyncSocketChannel.read(returnMessage);
			futureReadResult.get();
			response = new String(returnMessage.array());

			logger.info("received response=" + response);

			messageByteBuffer.clear();
		} catch (Exception e) {
			handleException(e);
		} finally {
			if (asyncSocketChannel.isOpen()) {
				try {
					asyncSocketChannel.close();
				} catch (IOException e) {
					// Not really anything we can do here.
					e.printStackTrace();
				}
			}
		}
		return response;
	}

	private void handleException(Exception e) {
		e.printStackTrace();
		throw new RuntimeException(e);
	}

}