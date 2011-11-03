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
		sock = socket;
		dir = taskDir;
		manager = engineManager;
	}

	@Override
	public void run() {
		try {
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());

			// receive task command + taskData
			String[] cmd = in.readUTF().split(" ");
			if(cmd[0].equals("!executeTask")) {
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
				
				byte[] buf = new byte[BUF_LENGTH];
				int bytesReceived = 0;
				while((bytesReceived = in.read(buf)) != -1) {
					fos.write(buf, 0, bytesReceived);
				}
				
				// TODO set status of file to executable
				
				
				
				manager.addLoad(load);
				
				// TODO execute and write back to client
				Thread.sleep(30000); // simulate execution
				
				out.writeUTF("task completed successfull");
				
				//close all after finishing
				in.close();
				out.close();
				sock.close();
				
				manager.removeLoad(load);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
