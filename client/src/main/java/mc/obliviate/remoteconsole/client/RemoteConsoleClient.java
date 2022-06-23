package mc.obliviate.remoteconsole.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class RemoteConsoleClient {

	public static void main(String[] args) {
		start(args);
	}

	private static void start(String[] args) {
		System.out.println("Connecting to server: " + args[0] + ":" + args[1]);
		try {
			int port = validatePort(args[1]);

			waitForCommands(args[0], port);

		} catch (IllegalArgumentException e) {
			System.out.println("Invalid port specified: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Could not connected to the server.");
		}


	}

	private static void waitForCommands(String ip, int port) throws IOException {
		Socket socket = new Socket(ip, port);
		System.out.println("Waiting for commands.");

		Scanner scanner = new Scanner(System.in);
		DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		String cmd = scanner.nextLine();
		dataOutputStream.writeUTF(cmd);
		if (cmd.equalsIgnoreCase("exit")) return;
		socket.close();

		waitForCommands(ip, port);
	}

	private static int validatePort(String stringPort) throws IllegalArgumentException {
		final int port = Integer.parseInt(stringPort);
		if (port < 1 || port > 65535) throw new IllegalArgumentException("port no is must be between 1 and 65535");
		return port;
	}
}
