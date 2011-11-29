package scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

import GTEs.EngineIdentifier;

/**
 * Server Socket; listens to client
 * own thread for each client
 * @author babz
 *
 */
public class ClientListener implements Runnable {
	private static Logger log = Logger.getLogger("class clientHandler");
	private PrintWriter out;
	private BufferedReader in;
	private Socket clientSocket;
	private GTEAssigner gteAssigner;
	private ClientConnectionManager incomingRequests;

	public ClientListener(Socket socket, GTEAssigner gteAssigner, ClientConnectionManager clientManager) throws IOException {
		clientSocket = socket;
		this.gteAssigner = gteAssigner;
		incomingRequests = clientManager;
		clientManager.addClientHandler(this);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	@Override
	public void run() {
		log.info("read stream from client");

		String input = null;
		try {
			while ((input = in.readLine()) != null) {
				UserCommand cmd = InputProcessor.processInput(input);
				if(cmd == null) {
					out.println("Invalid command!");
				} 
				//!requestEngine <taskId>.effort
				else if (cmd.command() == UserCommand.Cmds.REQUESTENGINE) {
					int id = Integer.parseInt(cmd.getArg(0));
					EngineIdentifier engine = gteAssigner.getEngine(cmd.getArg(1));
					if (engine == null) {
						out.println("!engineRequestFailed:" + id);
					} else {
						out.println("!engineAssigned:" + id + ":" + engine.getAddress().getHostAddress() + ":" + engine.getPort());
					}
				}
				//!exit
				else if (cmd.command() == UserCommand.Cmds.EXIT) {
					//TODO
				}
			} 
		} catch (IOException e) {
			// shutdown
		} finally {
			incomingRequests.removeClientHandler(this);
			destroy();
		}
	}

	public void logoutClientAtExit() {
		out.println("Scheduler shutting down. You will be logged out.");
		out.println("Successfully logged out.");
		destroy();
	}

	private void destroy() {
		out.close();
		try {
			in.close();
			clientSocket.close();
		} catch (IOException e) { }
	}
}