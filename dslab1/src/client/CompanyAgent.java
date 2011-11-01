package client;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

/**
 * Client Socket
 * does everything for company (login, demand engine, ...)
 * @author babz
 *
 */
public class CompanyAgent {
	private static Logger log = Logger.getLogger("class client socket");
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	//establish the socket connection between client and server
	public CompanyAgent(String schedulerHost, int schedulerTCPPort, String taskDir) throws IOException {
		log.info("init");
		clientSocket = new Socket(schedulerHost, schedulerTCPPort);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	
	//read one line at a time from the input stream and send to server by writing it to PrintWriter
	public void readStream() throws IOException {
		log.info("read stream");
		BufferedReader streamIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		while((userInput = streamIn.readLine()) != null) {
			out.println(userInput);
			//TODO listen in own thread
			System.out.println(in.readLine()); //answer from server
		}
	}
	
	public void destroy() throws IOException {
		log.info("company agent: destroy");
		out.close();
		in.close();
		clientSocket.close();
		
	}

}
