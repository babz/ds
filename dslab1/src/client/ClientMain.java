package client;

import java.io.IOException;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientSocket socket = null;

		// TODO fehlerbehandlung für typen
		int noOfParams = 3;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}
		
		try {
			socket = new ClientSocket(args[0], Integer.parseInt(args[1]), args[2]);
			socket.readStream();
		} catch (IOException exc) {
			System.out.println("connection from client failed");
		}

	}

}
