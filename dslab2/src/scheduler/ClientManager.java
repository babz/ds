package scheduler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import management.UserManager;

/**
 * manages Server Socket
 * @author babz
 *
 */
public class ClientManager implements Runnable {
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger("class client handler");

	private ServerSocket serverSocket;
	private UserManager companyManager;

	private GTEManager engineManager;

	private List<ClientHandler> clients = Collections.synchronizedList(new LinkedList<ClientHandler>());

	private boolean alive = true;

	public ClientManager(int tcpPort, GTEManager engineManager) throws IOException {
		serverSocket = new ServerSocket(tcpPort);
		companyManager = UserManager.getInstance();
		this.engineManager = engineManager;
	}

	@Override
	public void run() {
		while (alive ) {
			try {
				//gibt GTEAssigner mit
				new Thread(new ClientHandler(serverSocket.accept(), companyManager, engineManager.getGTEAssigner(), this)).start();
			} catch (IOException e) {
				// shutdown
			}
		}
	}
	
	/**
	 * tracks all online clients. used for emergency logout at scheduler shutdown.
	 */
	public void addClientHandler(ClientHandler handler) {
		clients.add(handler);
	}
	
	public void removeClientHandler(ClientHandler handler) {
		clients.remove(handler);
	}
	

	/**
	 * logs out all clients and closes socket
	 */
	public void terminate() {
		alive = false;
		
		synchronized(clients) {
			for(ClientHandler c : clients) {
				c.logoutClientAtExit();
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) { }
	}
}
