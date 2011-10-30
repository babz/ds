package genericTaskEngine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class DeadOrAlive implements Runnable {

	private DatagramSocket alivePacket;
	private int udpPort, tcpPort;
	private int minConsumption, maxConsumption;
	private boolean alive;
	private long alivePeriod;
	private String message;
	private String schedulerHost;
	
	public DeadOrAlive(int udpPort, int tcpPort, String schedulerHost, int alivePeriod, int minConsumption, int maxConsumption) throws SocketException {
		alivePacket = new DatagramSocket();
		alive = true;
		this.alivePeriod = alivePeriod;
		this.udpPort = udpPort;
		this.tcpPort = tcpPort;
		this.schedulerHost = schedulerHost;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
	}
	
	@Override
	public void run() {
		message = tcpPort + " " + minConsumption + " " + maxConsumption;
		while(alive) {
			int packetLength = 100;
			byte[] msg = new byte[packetLength];
			msg = message.getBytes();
			try {
				alivePacket.send(new DatagramPacket(msg, msg.length , InetAddress.getByName(schedulerHost), udpPort));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(alivePeriod);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				alive = false;
				return;
			}
		}
	}

}
