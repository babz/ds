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
	private SchedulerListener listener;
	private AliveSignalEmitter emitter;

	public EngineManager(int udpPort, int tcpPort, String schedulerHost,
			int alivePeriod, int minConsumption, int maxConsumption) throws SocketException {
		udp = udpPort;
		datagramSocket = new DatagramSocket();
		tcp = tcpPort;
		host = schedulerHost;
		this.alivePeriod = alivePeriod;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
	}

	@Override
	public void run() {
		
		try {
			emitter = new AliveSignalEmitter(datagramSocket, udp, tcp,
					host, alivePeriod, minConsumption, maxConsumption);
			new Thread(emitter).start();
			listener = new SchedulerListener(datagramSocket, emitter);
			new Thread(listener).start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void terminate() {
		datagramSocket.close();
		listener.terminate();
		emitter.terminate();
	}

}
