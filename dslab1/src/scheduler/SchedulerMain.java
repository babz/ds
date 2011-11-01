package scheduler;

import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;

public class SchedulerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO fehlerbehandlung für typen
		//params = int tcpPort, int udpPort, int min, int max, int timeout, int checkPeriod
		int noOfParams = 6;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}

		int tcpPort = Integer.parseInt(args[0]);
		int udpPort = Integer.parseInt(args[1]);
		int min = Integer.parseInt(args[2]); //min amt of used GTEs
		int max = Integer.parseInt(args[3]); //max amt of used GTEs
		int timeout = Integer.parseInt(args[4]);
		int checkPeriod = Integer.parseInt(args[5]);


		GTEManager engineManager = null;
		ClientHandler clientHandler = null;
		try {
			clientHandler = new ClientHandler(tcpPort, udpPort, min, max, timeout, checkPeriod);
			clientHandler.readStream();
			engineManager = new GTEManager(udpPort, min, max, timeout, checkPeriod);
			engineManager.startWorking();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("connection from scheduler failed");
		}

		SchedulerCommands readCommand = new SchedulerCommands(engineManager);
		readCommand.read();
	}

}
