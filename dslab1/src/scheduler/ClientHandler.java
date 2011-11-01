package scheduler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Server Socket
 * @author babz
 *
 */
public class ClientHandler {
	private static Logger log = Logger.getLogger("class client handler");

	private ServerSocket serverSocket;
	private DatagramSocket datagramSocket;
	private Socket clientSocket = null;
	private PrintWriter out;
	private BufferedReader in;
	private CompanyManager manager;

	public ClientHandler(int tcpPort, int udpPort, int min, int max, int timeout, int checkPeriod) throws IOException {
		serverSocket = new ServerSocket(tcpPort);

		manager = new CompanyManager();

		//TODO spawn new thread for each in loop
		clientSocket = serverSocket.accept();

		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public void readStream() {
		log.info("read stream from client");

		String input = null;
		try {
			//TODO check auf blocking - warum wird nur 1 line gelesen?
			while ((input = in.readLine()) != null) {
				UserCommand cmd = InputProcessor.processInput(input);
				if(cmd == null) {
					out.println("Invalid command!");
				} else if(cmd.command() == UserCommand.Cmds.LOGIN) {
					if(manager.checkLogin(cmd.getArg(0), cmd.getArg(1))) {

						out.println("Successfully logged in.");
					} else {
						out.println("Wrong company or password.");
					}

				} else if (input.startsWith("!logout")) {
				} else if (input.startsWith("!list")) {
				} else if (input.equals("!exit")) {
					destroy();
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("Error!");
		}
	}

	public void destroy() throws IOException {
		log.info("scheduler: close all");
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
}
