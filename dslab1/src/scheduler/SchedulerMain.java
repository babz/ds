package scheduler;

import java.io.IOException;

public class SchedulerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SchedulerSocket serverSocket = null;

		// TODO fehlerbehandlung für typen
		int noOfParams = 6;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}

		//cast args to int
		int[] argsInt = new int[6];
		for(int i = 0; i < args.length; i++) {
			argsInt[i] = Integer.parseInt(args[i]);
		}

		try {
			serverSocket = new SchedulerSocket(argsInt[0], argsInt[1], argsInt[2], argsInt[3], argsInt[4], argsInt[5]);
			serverSocket.readStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("connection from scheduler failed");
			try {
				serverSocket.destroy();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("server socket could not be terminated");
			}
		}


	}

}
