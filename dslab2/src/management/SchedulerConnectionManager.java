package management;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * manages sockets: company agent, connection listener
 * @author babz
 *
 */
public class SchedulerConnectionManager implements Runnable {

	private static SchedulerConnectionManager instance;
	private Socket clientSocket;
	private ConnectionListener listener;
	private CompanyAgent agent;
	private MgmtTaskManager taskManager;

	private SchedulerConnectionManager(String schedulerHost, int schedulerTCPPort, MgmtTaskManager taskManager) throws UnknownHostException, IOException {
		clientSocket = new Socket(schedulerHost, schedulerTCPPort);
		this.taskManager = taskManager;
	}
	
	public static synchronized SchedulerConnectionManager getInstance(String host, int port, MgmtTaskManager tasks) throws UnknownHostException, IOException {
		if(instance == null) {
			instance = new SchedulerConnectionManager(host, port, tasks);
		}
		return instance;
	}

	@Override
	public void run() {
		try {
			agent = new CompanyAgent(clientSocket, taskManager, this);
			new Thread(agent).start();
			listener = new ConnectionListener(clientSocket, taskManager, this);
			new Thread(listener).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void terminate() {
		try {
			agent.terminate();
			listener.terminate();
			clientSocket.close();
		} catch (IOException e) { }
	}
}
