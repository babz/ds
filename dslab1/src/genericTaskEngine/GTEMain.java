package genericTaskEngine;

import java.net.SocketException;

public class GTEMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TODO fehlerbehandlung für typen
		// params = int tcpPort, String schedulerHost, int schedulerUDPPort, int
		// alivePeriod, int minConsumption, int maxConsumption, String taskdir
		int noOfParams = 7;
		if (args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			// TODO exit
		}

		int tcpPort = Integer.parseInt(args[0]);
		String schedulerHost = args[1];
		int udpPort = Integer.parseInt(args[2]);
		int alivePeriod = Integer.parseInt(args[3]);
		int minConsumption = Integer.parseInt(args[4]);
		int maxConsumption = Integer.parseInt(args[5]);
		String taskdir = args[6];

		try {
			EngineManager manager = new EngineManager(udpPort, tcpPort,
					schedulerHost, alivePeriod, minConsumption, maxConsumption);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EngineInfoPoint commandReader = new EngineInfoPoint();
		commandReader.read();
	}

}
