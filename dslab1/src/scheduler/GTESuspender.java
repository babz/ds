package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.net.DatagramSocket;
import java.util.Hashtable;

public class GTESuspender implements Runnable {

	private DatagramSocket datagramSocket;
	private Hashtable<EngineIdentifier, GTEInfo> engines;

	public GTESuspender(DatagramSocket datagramSocket, Hashtable<EngineIdentifier, GTEInfo> engines) {
		this.datagramSocket = datagramSocket;
		this.engines = engines;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
