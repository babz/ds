package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * manages sockets: company agent, connection listener
 * @author babz
 *
 */
public class ClientConnectionManager implements Runnable {

	private Socket clientSocket;
	private ConnectionListener listener;
	private CompanyAgent agent;

	public ClientConnectionManager(String schedulerHost, int schedulerTCPPort) throws UnknownHostException, IOException {
		clientSocket = new Socket(schedulerHost, schedulerTCPPort);
	}

	@Override
	public void run() {
		try {
			agent = new CompanyAgent(clientSocket);
			new Thread(agent).start();
			listener = new ConnectionListener(clientSocket);
			new Thread(listener).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void terminate() throws IOException {
		clientSocket.close();
		agent.terminate();
		listener.terminate();
	}
}
