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
	private TaskManager taskManager;

	public ClientConnectionManager(String schedulerHost, int schedulerTCPPort, TaskManager taskManager) throws UnknownHostException, IOException {
		clientSocket = new Socket(schedulerHost, schedulerTCPPort);
		this.taskManager = taskManager;
	}

	@Override
	public void run() {
		try {
			agent = new CompanyAgent(clientSocket, taskManager);
			new Thread(agent).start();
			listener = new ConnectionListener(clientSocket, taskManager);
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