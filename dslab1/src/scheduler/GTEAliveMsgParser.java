package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.net.DatagramPacket;
import java.util.Hashtable;
import java.util.logging.Logger;

public class GTEAliveMsgParser implements Runnable {
	private static Logger log = Logger.getLogger("class gte alivemsg parser");

	private DatagramPacket packet;
	private Hashtable<EngineIdentifier, GTEInfo> engines;


	public GTEAliveMsgParser(DatagramPacket packet,
			Hashtable<EngineIdentifier, GTEInfo> engines) {
		this.packet = packet;
		this.engines = engines;
	}


	@Override
	public void run() {
		log.info("run");
		String msg = new String(packet.getData(), 0, packet.getLength());
		
		//msg decode
		int tcpPort = 0;
		int minCons = 0;
		int maxCons = 0;
		String[] msgParts = msg.split(" ");
		try {
			tcpPort = Integer.parseInt(msgParts[0]);
			minCons = Integer.parseInt(msgParts[1]);
			maxCons = Integer.parseInt(msgParts[2]);
		} catch (NumberFormatException exc) {
			log.warning("parsing failed");
		}

		//update time if engine already in hashtable, add new engine with time otherwise
		EngineIdentifier currEngine = new EngineIdentifier(packet.getAddress(), packet.getPort());
		if(engines.containsKey(currEngine)) {
			engines.get(currEngine).updateTime();
		} else {
			engines.put(currEngine, new GTEInfo(tcpPort, minCons, maxCons));
		}
		log.info("parsed:" + tcpPort);
	}

}
