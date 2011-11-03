package scheduler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * manages Server Socket
 * @author babz
 *
 */
public class ClientManager implements Runnable {
	private static Logger log = Logger.getLogger("class client handler");

	private ServerSocket serverSocket;
	private CompanyManager companyManager;

	private GTEManager engineManager;

	public ClientManager(int tcpPort, GTEManager engineManager) throws IOException {
		serverSocket = new ServerSocket(tcpPort);
		companyManager = CompanyManager.getInstance();
		this.engineManager = engineManager;
	}

	@Override
	public void run() {
		while (true) {
			try {
				//gibt GTEAssigner mit
				new Thread(new ClientHandler(serverSocket.accept(), companyManager, engineManager.getGTEAssigner())).start();
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
