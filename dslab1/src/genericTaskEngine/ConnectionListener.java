package genericTaskEngine;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionListener implements Runnable {

	private ServerSocket socket;
	private String taskDir;
	private EngineManager engineManager;

	public ConnectionListener(int tcp, String taskDir, EngineManager engineManager) throws IOException {
		socket = new ServerSocket(tcp);
		this.taskDir = taskDir;
		this.engineManager = engineManager;
	}

	@Override
	public void run() {
		try {
			while(true) {
				// TODO save connection for shutdown
				ClientConnection connection = new ClientConnection(socket.accept(), taskDir, engineManager);
				new Thread(connection).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
