package genericTaskEngine;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * manages datagram sockets: aliveSignalEmitter and SchedulerListener
 * @author babz
 *
 */
public class EngineManager implements Runnable {

	private int tcp;
	private int udp;
	private String host;
	private int alivePeriod;
	private int minConsumption;
	private int maxConsumption;
	private DatagramSocket datagramSocket;

	public EngineManager(int udpPort, int tcpPort, String schedulerHost,
			int alivePeriod, int minConsumption, int maxConsumption) throws SocketException {
		udp = udpPort;
		datagramSocket = new DatagramSocket(udpPort);
		tcp = tcpPort;
		host = schedulerHost;
		this.alivePeriod = alivePeriod;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
	}

	@Override
	public void run() {
		AliveSignalEmitter emitter;
		try {
			emitter = new AliveSignalEmitter(datagramSocket, udp, tcp,
					host, alivePeriod, minConsumption, maxConsumption);
			new Thread(emitter).start();
			SchedulerListener listener = new SchedulerListener(datagramSocket, emitter);
			new Thread(listener).start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
