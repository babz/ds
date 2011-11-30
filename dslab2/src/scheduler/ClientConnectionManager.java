package scheduler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * manages Server Socket
 * @author babz
 *
 */
public class ClientConnectionManager implements Runnable {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger("class client handler");

	private ServerSocket serverSocket;
	private GTEManager engineManager;

	private boolean alive = true;

	public ClientConnectionManager(int tcpPort, GTEManager engineManager) throws IOException {
		serverSocket = new ServerSocket(tcpPort);
		this.engineManager = engineManager;
	}

	@Override
	public void run() {
		while (alive) {
			try {
				//gibt GTEAssigner mit
				new Thread(new ClientListener(serverSocket.accept(), engineManager.getGTEAssigner())).start();
			} catch (IOException e) {
				// shutdown
			}
		}
	}
	
	public void terminate() {
		alive = false;
		try {
			serverSocket.close();
		} catch (IOException e) { }
	}
}
