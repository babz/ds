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
//	private UserManager companyManager;

	private GTEManager engineManager;

	private List<ClientListener> clients = Collections.synchronizedList(new LinkedList<ClientListener>());

	private boolean alive = true;

	public ClientConnectionManager(int tcpPort, GTEManager engineManager) throws IOException {
		serverSocket = new ServerSocket(tcpPort);
//		companyManager = UserManager.getInstance();
		this.engineManager = engineManager;
	}

	@Override
	public void run() {
		while (alive ) {
			try {
				//gibt GTEAssigner mit
				new Thread(new ClientListener(serverSocket.accept(), engineManager.getGTEAssigner(), this)).start();
			} catch (IOException e) {
				// shutdown
			}
		}
	}
	
	/**
	 * tracks all online clients. used for emergency logout at scheduler shutdown.
	 */
	public void addClientHandler(ClientListener handler) {
		clients.add(handler);
	}
	
	public void removeClientHandler(ClientListener handler) {
		clients.remove(handler);
	}
	

	/**
	 * logs out all clients and closes socket
	 */
	public void terminate() {
		alive = false;
		
		synchronized(clients) {
			for(ClientListener c : clients) {
				c.logoutClientAtExit();
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) { }
	}
}
