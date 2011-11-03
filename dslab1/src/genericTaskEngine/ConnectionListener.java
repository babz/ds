package genericTaskEngine;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener implements Runnable {

	private ServerSocket socket;
	private String taskDir;

	public ConnectionListener(int tcp, String taskDir) throws IOException {
		socket = new ServerSocket(tcp);
		this.taskDir = taskDir;
	}

	@Override
	public void run() {
		try {
			// TODO save connection for shutdown
			ClientConnection connection = new ClientConnection(socket.accept(), taskDir);
			new Thread(connection).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
