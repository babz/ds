package client;

import java.io.File;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		params = String schedulerHost, int schedulerTCPPort, String taskDir
		int noOfParams = 2;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}
		
		String mgmtComponent = args[0];
		File taskDir = new File(args[1]);
		
//		TaskManager taskManager = null;
//		ClientConnectionManager connection = null;
//		taskManager = new TaskManager(taskDir);
//		try {
//			connection = new ClientConnectionManager(schedulerHost, schedulerTCPPort, taskManager);
//			new Thread(connection).start();
//		} catch (IOException exc) {
//			System.out.println("connection from client failed");
//		}

	}

}
