package client;

import java.io.IOException;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientSocket socket = null;

		// TODO fehlerbehandlung für typen
		int noOfParams = 7;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			try {
				socket.destroy();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			socket = new ClientSocket(args[0], Integer.parseInt(args[1]), args[2]);
			socket.readStream();
			socket.destroy();
		} catch (IOException exc) {
			System.out.println("connection from client failed");
		}

	}

}
