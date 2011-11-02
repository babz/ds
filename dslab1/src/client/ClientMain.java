package client;

import java.io.IOException;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CompanyAgent socket = null;

		// TODO fehlerbehandlung für typen
//		params = String schedulerHost, int schedulerTCPPort, String taskDir
		int noOfParams = 3;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}
		
		String schedulerHost = args[0];
		int schedulerTCPPort = Integer.parseInt(args[1]);
		String taskDir = args[2];
		
		try {
			socket = new CompanyAgent(schedulerHost, schedulerTCPPort, taskDir);
			socket.readStream();
		} catch (IOException exc) {
			System.out.println("connection from client failed");
		}

		ClientInfoPoint commandReader = new ClientInfoPoint();
		commandReader.read();
	}

}
