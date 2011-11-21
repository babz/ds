package scheduler;

import GTEs.EngineIdentifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Server Socket; listens to client
 * own thread for each client
 * @author babz
 *
 */
public class ClientHandler implements Runnable {
	private static Logger log = Logger.getLogger("class clientHandler");
	private PrintWriter out;
	private BufferedReader in;
	private Socket clientSocket;
	private CompanyManager companyManager;
	private GTEAssigner gteAssigner;
	private String currentlyLoggedIn = null;
	private ClientManager clientManager;

	public ClientHandler(Socket socket, CompanyManager companyManager, GTEAssigner gteAssigner, ClientManager clientManager) throws IOException {
		clientSocket = socket;
		this.companyManager = companyManager;
		this.gteAssigner = gteAssigner;
		this.clientManager = clientManager;
		clientManager.addClientHandler(this);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	@Override
	public void run() {
		log.info("read stream from client");

		String input = null;
		try {
			//TODO check auf blocking - warum wird nur 1 line gelesen?
			while ((input = in.readLine()) != null) {
				UserCommand cmd = InputProcessor.processInput(input);
				if(cmd == null) {
					out.println("Invalid command!");
				} 
				//!login <company> <password>
				else if(cmd.command() == UserCommand.Cmds.LOGIN) {
					if(currentlyLoggedIn != null) {
						out.println("Already logged in.");
						continue;
					}
					if(companyManager.checkLogin(cmd.getArg(0), cmd.getArg(1))) {
						currentlyLoggedIn = cmd.getArg(0);
						out.println("Successfully logged in.");
					} else {
						out.println("Wrong company or password.");
					}
				} 
				//!logout
				else if (cmd.command() == UserCommand.Cmds.LOGOUT) {
					if(companyManager.logout(currentlyLoggedIn)) {
						currentlyLoggedIn = null;
						out.println("Successfully logged out.");
					} else {
						out.println("You have to log in first.");
					}
				} 
				//!requestEngine <taskId>.effort
				else if (cmd.command() == UserCommand.Cmds.REQUESTENGINE) {
					if(currentlyLoggedIn == null) {
						out.println("Login required!");
					} else {
						int id = Integer.parseInt(cmd.getArg(0));
						EngineIdentifier engine = gteAssigner.getEngine(cmd.getArg(1));
						if (engine == null) {
							out.println("!engineRequestFailed:" + id);
						} else {
							companyManager.getCompanyInfo(currentlyLoggedIn).increaseRequests(cmd.getArg(1));
							out.println("!engineAssigned:" + id + ":" + engine.getAddress().getHostAddress() + ":" + engine.getPort());
						}
					}
				}
				//!executeTask <taskId>.effort <startScript>
				else if (cmd.command() == UserCommand.Cmds.EXECUTETASK) {
					if(currentlyLoggedIn == null) {
						out.println("Login required!");
					} else {
						//TODO
					}
				}
				//!info <taskId>.effort
				else if (cmd.command() == UserCommand.Cmds.INFO) {
					if(currentlyLoggedIn == null) {
						out.println("Login required!");
					} else {

					}
				}
				//!exit
				else if (cmd.command() == UserCommand.Cmds.EXIT) {
					if(currentlyLoggedIn != null) {
						companyManager.logout(currentlyLoggedIn);
						return;
					}
				}
			} 
		} catch (IOException e) {
			// shutdown
		} finally {
			clientManager.removeClientHandler(this);
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