package scheduler;

import java.io.IOException;
import java.net.SocketException;

public class SchedulerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SchedulerSocket serverSocket = null;

		// TODO fehlerbehandlung für typen
		//		params = int tcpPort, int udpPort, int min, int max, int timeout, int checkPeriod
		int noOfParams = 6;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}

		int tcpPort = Integer.parseInt(args[0]);
		int udpPort = Integer.parseInt(args[1]);
		int min = Integer.parseInt(args[2]);
		int max = Integer.parseInt(args[3]);
		int timeout = Integer.parseInt(args[4]);
		int checkPeriod = Integer.parseInt(args[5]);

		try {
			GTEManager engineManager;
			engineManager = new GTEManager(udpPort);
			engineManager.startWorking();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//			serverSocket = new SchedulerSocket(tcpPort, udpPort, min, max, timeout, checkPeriod);
		//			serverSocket.readStream();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			System.out.println("connection from scheduler failed");
		//			try {
		////				serverSocket.destroy();
		//			} catch (IOException e1) {
		//				// TODO Auto-generated catch block
		//				System.out.println("server socket could not be terminated");
		//			}
		//		}


	}

}
