package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * manages sockets: writes to server, listens to answer
 * @author babz
 *
 */
public class SchedulerConnectionManager {

	private static Logger log = Logger.getLogger("open scheduler connection");

	private static SchedulerConnectionManager instance;
	private Socket clientSocket;

	private PrintWriter serverWriter;
	private BufferedReader schedulerIn;

	private SchedulerConnectionManager(String schedulerHost, int schedulerTCPPort) throws UnknownHostException, IOException {
		clientSocket = new Socket(schedulerHost, schedulerTCPPort);
		serverWriter = new PrintWriter(clientSocket.getOutputStream(), true);
		schedulerIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public static synchronized SchedulerConnectionManager getInstance(String host, int port) throws UnknownHostException, IOException {
		if(instance == null) {
			instance = new SchedulerConnectionManager(host, port);
		}
		return instance;
	}

	public String requestEngine(String taskEffort) throws NumberFormatException, IOException {
		log.info("forward request to scheduler");
		serverWriter.println(taskEffort);
		
		log.info("catch answer from scheduler");
		String answer = schedulerIn.readLine();
		closeConnectionProperly();
		return answer;
	}

	private void closeConnectionProperly() {
		serverWriter.close();
		try {
			schedulerIn.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
