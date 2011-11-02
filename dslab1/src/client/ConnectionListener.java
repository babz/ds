package client;

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
	private boolean alive;

	public ConnectionListener(Socket clientSocket) throws IOException {
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		alive = true;
	}

	@Override
	public void run() {
		log.info("catch answers from scheduler");
		while(alive) {
			try {
				System.out.println(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //answer from server
		}
	}

	public void destroy() {
		log.info("connection listener: destroy");
	}

	public void terminate() throws IOException {
		alive = false;
		in.close();
	}

}
