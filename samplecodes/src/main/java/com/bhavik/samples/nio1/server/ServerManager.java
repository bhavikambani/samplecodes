package com.bhavik.samples.nio1.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ServerManager {
	public ServerManager() {
		try {
			// Create an AsynchronousServerSocketChannel that will listen on
			// port 5000
			final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(5000));

			// Listen for a new request
			listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

				@Override
				public void completed(AsynchronousSocketChannel ch, Void att) {
					// Accept the next connection
					listener.accept(null, this);

					// Greet the client
					ch.write(ByteBuffer.wrap("This is server accepting string packets!\n".getBytes()));
					ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
					try {
						Integer i = new Integer(5);
						ObjectOutputStream oos = new ObjectOutputStream(Channels.newOutputStream(ch));
						oos.writeObject(i);
						oos.close();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					// Allocate a byte buffer (4K) to read from the client
					try {
						// Read the first line
						int bytesRead = ch.read(byteBuffer).get(20, TimeUnit.SECONDS);

						boolean running = true;
						while (bytesRead != -1 && running) {
							System.out.println("bytes read: " + bytesRead);

							// Make sure that we have data to read
							if (byteBuffer.position() > 2) {
								// Make the buffer ready to read
								byteBuffer.flip();

								// Convert the buffer into a line
								byte[] lineBytes = new byte[bytesRead];
								byteBuffer.get(lineBytes, 0, bytesRead);
								String line = new String(lineBytes);

								// Debug
								System.out.println("Message: " + line);

								// Echo back to the caller
								ch.write(ByteBuffer.wrap(line.getBytes()));

								// Make the buffer ready to write
								byteBuffer.clear();

								// Read the next line
								bytesRead = ch.read(byteBuffer).get(20, TimeUnit.SECONDS);
							} else {
								// An empty line signifies the end of the
								// conversation in our protocol
								running = false;
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					} catch (TimeoutException e) {
						// The user exceeded the 20 second timeout, so close the
						// connection
						ch.write(ByteBuffer.wrap("Good Bye\n".getBytes()));
						System.out.println("Connection timed out, closing connection");
					}

					System.out.println("End of conversation");
					try {
						// Close the connection if we need to
						if (ch.isOpen()) {
							ch.close();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void failed(Throwable exc, Void att) {
					/// ...
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ServerManager server = new ServerManager();
		try {
			Thread.sleep(60000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}