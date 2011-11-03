package genericTaskEngine;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnection implements Runnable {

	private static final int BUF_LENGTH = 100;
	private Socket sock;
	private String dir;
	private EngineManager manager;

	public ClientConnection(Socket socket, String taskDir, EngineManager engineManager) {
		
		
		System.out.println("new Client connection");
		
		
		sock = socket;
		dir = taskDir;
		manager = engineManager;
	}

	@Override
	public void run() {
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			
			System.out.println("opening streams");
			
			
			in = new DataInputStream(sock.getInputStream());
			out = new DataOutputStream(sock.getOutputStream());

			
			System.out.println("splitting command");
			
			
			// receive task command + taskData
			String[] cmd = in.readUTF().split(" ");
			if(cmd[0].equals("!executeTask")) {
				
				System.out.println("execute task");
				
				
				String effort = cmd[1];
				int load = 0;
				if(effort.equals("LOW")) {
					load = 33;
				} else if (effort.equals("MIDDLE")) {
					load = 66;
				} else if (effort.equals("HIGH")) {
					load = 100;
				}
				String startScript = cmd[2];
				String filename = cmd[3];
				
				//TODO check if this works
				FileOutputStream fos = new FileOutputStream(dir + File.separator + filename);
				
				
				System.out.println("read file from client");
				
				
				byte[] buf = new byte[BUF_LENGTH];
				int bytesReceived = 0;
				while((bytesReceived = in.read(buf)) != -1) {
					System.out.println("writing buf to disk");
					fos.write(buf, 0, bytesReceived);
				}
				
				// TODO set status of file to executable
				
				System.out.println("finished reading file from client");
				
				out.writeUTF("Starting execution");
				
				
				manager.addLoad(load);
				
				// TODO execute and write back to client
				Thread.sleep(30000); // simulate execution
				
				out.writeUTF("task completed successfully");

				
				manager.removeLoad(load);
			} else if (cmd[0].equals("!currentLoad")) {
				out.writeInt(manager.getLoad());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			//close all after finishing
			
			
			System.out.println("closing streams");

			try {
				out.close();
				in.close();
				sock.close();
			} catch (IOException e) { }
		}
	}

}
