package mc.obliviate.remoteconsole.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class RemoteConsoleServer {

	private boolean alive = true;
	private Thread thread;
	private final Consumer<DataInputStream> pingConsumer;

	public RemoteConsoleServer(Consumer<DataInputStream> pingConsumer) {
		this.pingConsumer = pingConsumer;
	}

	private static ServerSocket startServer(int port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void start(int port) {
		this.thread = new Thread(() -> {
			final ServerSocket serverSocket = startServer(port);
			while (!Thread.interrupted() && alive) {
				try {
					Socket socket = serverSocket.accept();
					this.pingConsumer.accept(new DataInputStream(socket.getInputStream()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.thread.start();
	}

	public void stop() {
		this.alive = false;
	}

	public boolean isAlive() {
		return alive;
	}

}
