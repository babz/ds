package Connector;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class SocketConnector {
	private static Logger log = Logger.getLogger("Socket Connector");
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private String servername = "stockholm.vitalab.tuwien.ac.at";
	private int tcpPort = 9000;
	
	//establish the socket connection between client and server
	public SocketConnector() throws IOException {
		log.info("start logon");
		clientSocket = new Socket(servername, tcpPort);
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
			System.out.println(in.readLine());
		}
	}
	
	public void closeConnections() throws IOException {
		log.info("close all");
		out.close();
		in.close();
		clientSocket.close();
		
	}

}
