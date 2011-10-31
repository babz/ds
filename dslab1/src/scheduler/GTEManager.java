package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Logger;

public class GTEManager {
	private static Logger log = Logger.getLogger("class engine manager");
	
	private int udpPort;
	private DatagramSocket datagramSocket;
	private Hashtable<EngineIdentifier, GTEInfo> engines = new Hashtable<EngineIdentifier, GTEInfo>();

	public GTEManager(int udpPort) throws SocketException {
		this.udpPort = udpPort;
		datagramSocket = new DatagramSocket(udpPort);
	}

	public void startWorking() {
		log.info("manager started");
		// TODO do in own thread

		//schleife f. alive-receive
		GTEListener listener = new GTEListener(datagramSocket, engines);
		Thread t1 = new Thread(listener);
		GTESuspender suspender = new GTESuspender(datagramSocket, engines);
		Thread t2 = new Thread(suspender);
		t1.start();
		t2.start();
	}
}
