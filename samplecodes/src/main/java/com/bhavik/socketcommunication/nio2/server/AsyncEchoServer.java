package com.bhavik.socketcommunication.nio2.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AcceptPendingException;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.bhavik.socketcommunication.nio2.util.NamedThreadFactory;

/**
 * AsyncCounterServer is built on top Java nio2 asynchronous socket channels. It
 * can receive messages from clients and echo the response back.
 * 
 * The responsibility for managing server instances is with
 * {@link ServerManager}. That class should be used for starting and stopping
 * server instances.
 * <P>
 * Every Server has an server name and should be configured to run on free port.
 * <P>
 * To make remote requests to this class refer to
 * {@link com.alex.asyncexamples.client.AsyncEchoClient}
 * <P>
 * 
 * <dev-notes> Characteristics of this solution:
 * <OL>
 * <LI>ByteBuffers
 * <P>
 * There are two types of byte buffers:
 * <UL>
 * <LI>Direct byte buffers which use native APIs where possible
 * <LI>Non-direct byte buffers which don't. Recommendation from JDoc is to use
 * indirect byte buffers. "It is therefore recommended that direct buffers be
 * allocated primarily for large, long-lived buffers that are subject to the
 * underlying system's native I/O operations" See:
 * http://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html
 * </UL>
 * <LI>Threads
 * <UL>
 * <LI>Instantiation of AsyncCounterServer instance can happen on any thread -
 * the thread running a test, the main execution test of the program.
 * <LI>The ServerManager has a thread group which names threads as:
 * ServerManager_Cache_TP_Thread. AsyncCounterServer instances will set up a
 * Completion Handler callback to receive channel events on this thread.
 * <LI>A cached thread pool is used for the channel group (this is a collection
 * of all channels for a server instance). Thread from this pool will have the
 * name: %Server_name%_Group_Thread. Invocations to the command handler
 * completed() methods will happen on threads from this pool. In addition,
 * subsequent CompletionHandler callbacks are set up on this thread.
 * <LI>Everytime the server accepts a client request, the counter
 * <code>acceptCounter</code> is incremented. Everytime a request finished this
 * counter is decrement. Any request to shutdown this server will check this
 * counter and will try to wait until it is zero (signifying there are no
 * requests) and then close down. It won't wait forever. So it is possible to
 * have some corruption but just very unlikely.
 * <LI>A request will not be processed if the Server has been shutdown.
 * </UL>
 * <LI>Risks There some documented risks associated with using nio2 async
 * channels.
 * <UL>
 * <LI>Long lived operations on the CompleteHandler Callbacks could starve a
 * fixed sized thread pool.</LI> Several mitigation strategies for this. the one
 * I used was to use a CachedThreadPool which would increase in pool size as
 * needs be. There are also read / write timeout set in the callback logic. To
 * make this server even more sophisticated logic could be added so that if a
 * counter has been changed and then the write back to the client fails, the
 * change rolls back.
 * <LI>Too many reads happening at the same time and overwhelming memory</LI> A
 * mitigation strategy here would be to throttle using a pool of ByteBuffers. I
 * didn't implement this in this in this example as according to Alan Bateman's
 * JavaOne presentation it was possible to reach 10,000 connection without this
 * happening.
 * <LI>
 * </UL>
 * See: http://openjdk.java.net/projects/nio/presentations/TS-4222.pdf for more
 * details.
 * 
 * @author Bhavik Aniruddh Ambani
 */
public final class AsyncEchoServer implements Runnable {
	private AsynchronousChannelGroup asyncChannelGroup;
	private String name;
	private AsynchronousServerSocketChannel asyncServerSocketChannel;

	public final static int READ_MESSAGE_WAIT_TIME = 15;
	public final static int MESSAGE_INPUT_SIZE = 128;

	Logger logger = Logger.getLogger(AsyncEchoServer.class.getName());

	private AsyncEchoServer() {
	}

	AsyncEchoServer(String name) throws IOException, InterruptedException, ExecutionException {
		this.name = name;
		asyncChannelGroup = AsynchronousChannelGroup
				.withThreadPool(Executors.newCachedThreadPool(new NamedThreadFactory(name + "_Group_Thread")));
	}

	void open(InetSocketAddress serverAddress) throws IOException {
		// open a server channel and bind to a free address, then accept a
		// connection
		logger.info("Opening Aysnc ServerSocket channel at " + serverAddress);
		asyncServerSocketChannel = AsynchronousServerSocketChannel.open(asyncChannelGroup).bind(serverAddress);
		asyncServerSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, MESSAGE_INPUT_SIZE);
		asyncServerSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
	}

	public void run() {
		try {
			if (asyncServerSocketChannel.isOpen()) {
				// The accept method does not block it sets up the
				// CompletionHandler callback and moves on.
				asyncServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
					@Override
					public void completed(final AsynchronousSocketChannel asyncSocketChannel, Object attachment) {
						if (asyncServerSocketChannel.isOpen()) {
							asyncServerSocketChannel.accept(null, this);
						}
						handleAcceptConnection(asyncSocketChannel);
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						if (asyncServerSocketChannel.isOpen()) {
							asyncServerSocketChannel.accept(null, this);
							System.out.println("***********" + exc + " statement=" + attachment);
						}
					}
				});
				logger.info("Server " + getName() + " reading to accept first connection...");
			}
		} catch (AcceptPendingException ex) {
			ex.printStackTrace();
		}
	}

	public void stopServer() throws IOException {
		logger.info(">>stopingServer()...");
		this.asyncServerSocketChannel.close();
		this.asyncChannelGroup.shutdown();
	}

	private void handleAcceptConnection(AsynchronousSocketChannel asyncSocketChannel) {
		logger.info(">>handleAcceptConnection(), asyncSocketChannel=" + asyncSocketChannel);
		ByteBuffer messageByteBuffer = ByteBuffer.allocate(MESSAGE_INPUT_SIZE);
		try {
			// read a message from the client, timeout after 10 seconds
			Future<Integer> futureReadResult = asyncSocketChannel.read(messageByteBuffer);
			futureReadResult.get(READ_MESSAGE_WAIT_TIME, TimeUnit.SECONDS);

			String clientMessage = new String(messageByteBuffer.array()).trim();

			messageByteBuffer.clear();
			messageByteBuffer.flip();

			String responseString = "echo" + "_" + clientMessage;
			messageByteBuffer = ByteBuffer.wrap((responseString.getBytes()));
			Future<Integer> futureWriteResult = asyncSocketChannel.write(messageByteBuffer);
			futureWriteResult.get(READ_MESSAGE_WAIT_TIME, TimeUnit.SECONDS);
			if (messageByteBuffer.hasRemaining()) {
				messageByteBuffer.compact();
			} else {
				messageByteBuffer.clear();
			}
		} catch (InterruptedException | ExecutionException | TimeoutException | CancellationException e) {
			logger.error(e);
		} finally {
			try {
				asyncSocketChannel.close();
			} catch (IOException ioEx) {
				logger.error(ioEx);
			}
		}
	}

	public String getName() {
		return this.name;
	}

}