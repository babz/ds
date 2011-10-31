package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

public class GTEManager {
	private static Logger log = Logger.getLogger("class engine manager");
	
	private int udpPort, min, max, timeout, checkPeriod;
	private DatagramSocket datagramSocket;
	private Hashtable<EngineIdentifier, GTEInfo> engines = new Hashtable<EngineIdentifier, GTEInfo>();

	public GTEManager(int udpPort, int min, int max, int timeout, int checkPeriod) throws SocketException {
		this.udpPort = udpPort;
		datagramSocket = new DatagramSocket(udpPort);
		this.min = min;
		this.max = max;
		this.timeout = timeout;
		this.checkPeriod = checkPeriod;
	}

	public void startWorking() {
		log.info("manager started");
		// TODO do in own thread

		//schleife f. alive-receive
		GTEListener listener = new GTEListener(datagramSocket, engines);
		Thread t1 = new Thread(listener);
		GTESuspender suspender = new GTESuspender(datagramSocket, engines, min, max, timeout, checkPeriod);
		Thread t2 = new Thread(suspender);
		t1.start();
		t2.start();
	}
	
	public String toString() {
		String engineList = "";
		for(Entry<EngineIdentifier, GTEInfo> engine: engines.entrySet()) {
			engineList += engine.getValue().toString() + "\n";
		}
		return engineList;
	}
}
