package genericTaskEngine;

import java.io.IOException;
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
	private SchedulerListener scheduleListener;
	private AliveSignalEmitter emitter;
	private String taskDir;

	public EngineManager(int udpPort, int tcpPort, String schedulerHost,
			int alivePeriod, int minConsumption, int maxConsumption, String taskDir) throws SocketException {
		udp = udpPort;
		datagramSocket = new DatagramSocket();
		tcp = tcpPort;
		host = schedulerHost;
		this.alivePeriod = alivePeriod;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
		this.taskDir = taskDir;
	}

	@Override
	public void run() {
		
		try {
			emitter = new AliveSignalEmitter(datagramSocket, udp, tcp,
					host, alivePeriod, minConsumption, maxConsumption);
			new Thread(emitter).start();
			scheduleListener = new SchedulerListener(datagramSocket, emitter);
			new Thread(scheduleListener).start();
			ConnectionListener connectionListener = new ConnectionListener(tcp, taskDir);
			new Thread(connectionListener).start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void terminate() {
		datagramSocket.close();
		scheduleListener.terminate();
		emitter.terminate();
	}

}
