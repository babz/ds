package scheduler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class EngineManager {

	private int udpPort;
	private DatagramSocket datagramSocket;

	public EngineManager(int udpPort) throws SocketException {
		this.udpPort = udpPort;
		datagramSocket = new DatagramSocket(udpPort);
	}

	public void startWorking() {
		// TODO do in own thread
		while(true){
			try {
				int packetLength = 100;
				DatagramPacket packet = new DatagramPacket(new byte[packetLength], packetLength);
				datagramSocket.receive(packet);
				String msg = new String(packet.getData());
				System.out.println(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("msg nicht angekommen");
			}
		}
	}

}
