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
				byte[] buf = new byte[packetLength];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				datagramSocket.receive(packet);
				String msg = new String(packet.getData(), 0, packet.getLength());
				System.out.println(msg);
				String[] msgParts = msg.split(" ");
				int tcpPort = Integer.parseInt(msgParts[0]);
				int minCons = Integer.parseInt(msgParts[1]);
				int maxCons = Integer.parseInt(msgParts[2]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("msg nicht angekommen");
			}
		}
	}

}
