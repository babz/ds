package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.logging.Logger;

public class GTEListener implements Runnable {
	private static Logger log = Logger.getLogger("class GTE listener");

	private DatagramPacket packet;
	private DatagramSocket datagramSocket;
	private Hashtable<EngineIdentifier, GTEInfo> engines;

	public GTEListener(DatagramSocket datagramSocket, Hashtable<EngineIdentifier, GTEInfo> engines) {
		int packetLength = 100;
		byte[] buf = new byte[packetLength];
		packet = new DatagramPacket(buf, buf.length);
		this.datagramSocket = datagramSocket;
		this.engines = engines;
	}

	@Override
	public void run() {
		log.info("run");
		while (true) {
			try {
				//receive packeges and forward them
				datagramSocket.receive(packet);
				Thread t = new Thread(new GTEAliveMsgParser(packet, engines));
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
