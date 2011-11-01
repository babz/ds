package genericTaskEngine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import scheduler.GTEAliveMsgParser;

/**
 * listens to scheduler for incoming suspend/activate packages
 * @author babz
 *
 */
public class SchedulerListener implements Runnable {

	private DatagramSocket socket;
	private DatagramPacket packet;
	private AliveSignalEmitter emitter;

	public SchedulerListener(DatagramSocket datagramSocket, AliveSignalEmitter emitter) {
		socket = datagramSocket;
		this.emitter = emitter;
		int packetLength = 100;
		byte[] buf = new byte[packetLength];
		packet = new DatagramPacket(buf, buf.length);
	}

	@Override
	public void run() {
		while (true) {
			try {
				//receive packages and forward them
				socket.receive(packet);
				unpack(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void unpack(DatagramPacket packet) {
		 String msg = new String(packet.getData(), 0, packet.getLength());
		 
		 //msg decode
		 if(msg.equals("suspend")) {
			 emitter.stopEmitter();
		 } else if (msg.equals("activate")) {
			 emitter.restart();
		 } else {
			 //TODO exception werfen: unknown command
		 }
		
	}
}