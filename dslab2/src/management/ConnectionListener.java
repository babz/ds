package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * client socket; receives answers from server (scheduler)
 * @author babz
 *
 */
public class ConnectionListener implements Runnable {
	private static Logger log = Logger.getLogger("class client socket listener");

	private BufferedReader in;
	private boolean alive = true;

	private TaskManager taskManager;

	private CompanyAgent companyAgent;

	private ClientConnectionManager clientConnectionManager;

	public ConnectionListener(Socket clientSocket, TaskManager taskManager, CompanyAgent agent, ClientConnectionManager ccManager) throws IOException {
		this.taskManager = taskManager;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		companyAgent = agent;
		clientConnectionManager = ccManager;
	}

	@Override
	public void run() {
		log.info("catch answers from scheduler");
		while(alive) {
			try {
				String input = in.readLine();
				if(input == null) {
					// shutdown of scheduler
					clientConnectionManager.terminate();
					return;
				} else if(input.equals("!engineRequestFailed")) {
					System.out.println("Not enough capacity. Try again later.");
				} else if(input.startsWith("!engineAssigned")) {
					String[] cmd = input.split(":");
					int taskId = Integer.parseInt(cmd[1]);
					String address = cmd[2];
					int port = Integer.parseInt(cmd[3]);

					taskManager.assignEngine(taskId, address, port);
					System.out.println("Assigned engine: " + address + " Port: " + port);
				} else if(input.equals("Successfully logged in.")) {
					companyAgent.loggedIn();
					System.out.println(input);
				} else if(input.equals("Successfully logged out.")) {
					companyAgent.loggedOut();
					System.out.println(input);
				}  else if(input.startsWith("!engineRequestFailed")) {
					System.out.println("Not enough capacity. Try again later.");
				}
				else {
					System.out.println(input);
				}
			} catch (IOException e) {
			} //answer from server
		}
	}

	public void terminate() throws IOException {
		alive = false;
		in.close();
	}

}
