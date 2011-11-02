package scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * own thread for each client
 * @author babz
 *
 */
public class ClientHandler implements Runnable {
	private static Logger log = Logger.getLogger("class clientHandler");
	private PrintWriter out;
	private BufferedReader in;
	private Socket clientSocket;
	private CompanyManager manager;
	private String currentlyLoggedIn;

	public ClientHandler(Socket accept, CompanyManager manager) throws IOException {
		clientSocket = accept;
		this.manager = manager;
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
				//!login
				else if(cmd.command() == UserCommand.Cmds.LOGIN) {
					if(manager.checkLogin(cmd.getArg(0), cmd.getArg(1))) {
						currentlyLoggedIn = cmd.getArg(0);
						out.println("Successfully logged in.");
					} else {
						out.println("Wrong company or password.");
					}
				} 
				//!logout
				else if (cmd.command() == UserCommand.Cmds.LOGOUT) {
					if(manager.logout(currentlyLoggedIn)) {
						out.println("Successfully logged out.");
					} else {
						out.println("You have to log in first.");
					}
				} 
				//!requestEngine
				else if (cmd.command() == UserCommand.Cmds.REQUESTENGINE) {
					//TODO
				}
				//!executeTask
				else if (cmd.command() == UserCommand.Cmds.EXECUTETASK) {
					//TODO
				}
				//!info
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
