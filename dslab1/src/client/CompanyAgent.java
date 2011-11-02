package client;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

/**
 * Client Socket; forwards commands to scheduler
 * does everything for company (login, demand engine, ...)
 * @author babz
 *
 */
public class CompanyAgent implements Runnable {
	private static Logger log = Logger.getLogger("class client socket chatter");

	private PrintWriter out;
	private boolean alive;
	private BufferedReader streamIn;


	//establish the socket connection between client and server
	public CompanyAgent(Socket clientSocket) throws IOException{
		log.info("init");
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		alive = true;
	}


	//read one line at a time from the input stream and send to server by writing it to PrintWriter
	@Override
	public void run(){
		log.info("forward requests to scheduler");
		streamIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		try {
			while(alive && ((userInput = streamIn.readLine()) != null)) {
				out.println(userInput);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void terminate() throws IOException {
		alive = false;
		out.close();
		streamIn.close();
	}
}
