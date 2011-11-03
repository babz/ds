package scheduler;

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

	public ClientHandler(Socket socket, CompanyManager companyManager, GTEAssigner gteAssigner) throws IOException {
		clientSocket = socket;
		this.companyManager = companyManager;
		this.gteAssigner = gteAssigner;
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
						gteAssigner.getEngine(cmd.getArg(0));
					}
				}
				//!executeTask <taskId> <startScript>
				else if (cmd.command() == UserCommand.Cmds.EXECUTETASK) {
					//TODO
				}
				//!info <taskId>
				else if (cmd.command() == UserCommand.Cmds.INFO) {
					//TODO
				}
				//!exit
				else if (cmd.command() == UserCommand.Cmds.EXIT) {
					destroy();
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error!");
		}

	}

	private void destroy() throws IOException {
		out.close();
		in.close();
		clientSocket.close();
	}

}
