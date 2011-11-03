package client;

import java.io.File;
import java.io.IOException;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO fehlerbehandlung für typen
//		params = String schedulerHost, int schedulerTCPPort, String taskDir
		int noOfParams = 3;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}
		
		String schedulerHost = args[0];
		int schedulerTCPPort = Integer.parseInt(args[1]);
		File taskDir = new File(args[2]);
		
		TaskManager taskManager = null;
		ClientConnectionManager connection = null;
		try {
			taskManager = new TaskManager(taskDir);
			connection = new ClientConnectionManager(schedulerHost, schedulerTCPPort, taskManager);
			new Thread(connection).start();
		} catch (IOException exc) {
			System.out.println("connection from client failed");
		}

	}

}
