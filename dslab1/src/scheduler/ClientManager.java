package scheduler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Server Socket
 * @author babz
 *
 */
public class ClientManager implements Runnable {
	private static Logger log = Logger.getLogger("class client handler");

	private ServerSocket serverSocket;
	private CompanyManager manager;

	public ClientManager(int tcpPort) throws IOException {
		serverSocket = new ServerSocket(tcpPort);
		manager = CompanyManager.getInstance();
	}

	@Override
	public void run() {
		while (true) {
			try {
				new Thread(new ClientHandler(serverSocket.accept(), manager)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void destroy() throws IOException {
		log.info("scheduler: close all");
		serverSocket.close();
	}
}
