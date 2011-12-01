package GTEs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable {

	private Socket sock;
	@SuppressWarnings("unused")
	private String dir; //relief
	private EngineManager manager;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private ConnectionListener connectionListener;

	public ClientConnection(Socket socket, String taskDir, EngineManager engineManager, ConnectionListener connectionListener) {
		sock = socket;
		dir = taskDir;
		manager = engineManager;
		this.connectionListener = connectionListener;
		connectionListener.addClient(this);
	}

	@Override
	public void run() {
		int sleepTime = 0;
		try {
			System.out.println("opening streams");
			
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream());
			
			System.out.println("splitting command");
			
			// receive task command + taskData
			String[] cmd = in.readLine().split(" ");
			if(cmd[0].equals("!executeTask")) {
				
				System.out.println("execute task");
				
				String effort = cmd[1];
				int load = 0;
				if(effort.equals("LOW")) {
					load = 33;
					sleepTime = 30000;
				} else if (effort.equals("MIDDLE")) {
					load = 66;
					sleepTime = 60000 * 3;
				} else if (effort.equals("HIGH")) {
					load = 100;
					sleepTime = 60000 * 5;
				}
				@SuppressWarnings("unused")
				String startScript = cmd[2];
				
				//TODO relief #1
				System.out.println("read file from client - not implemented, see relief #1");
				// read file from client
				System.out.println("finished reading file from client - not implemented, see relief #1");
				
				out.println("Starting execution");
				
				manager.addLoad(load);
				
				// TODO relief #2
				out.println("begin task xyz");
				Thread.sleep(sleepTime); // simulate execution
				
				out.println("task completed successfully");
				
				manager.removeLoad(load);
			} else if (cmd[0].trim().equals("!currentLoad")) {
				System.out.println("load requested");
				out.println(manager.getLoad());
				System.out.println("requesting load 2");
			} else {
				System.out.println("UNKnONW COMMAND: " + cmd);
			}
		} catch (IOException e) {
			
		}
		catch (InterruptedException e) {
		
		} finally {
			connectionListener.removeClient(this);
			
			//close all after finishing
			
			System.out.println("closing streams");

			try {
				out.close();
				in.close();
				sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void terminate() {
		try {
			out.println("Engine shutting down");
			
			out.close();
			in.close();
			sock.close();
		} catch (IOException e) { }
	}

}
