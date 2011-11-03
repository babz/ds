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

	public ClientConnection(Socket socket, String taskDir) {
		sock = socket;
		dir = taskDir;
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
				
				// TODO set effort
				
				// TODO execute and write back to client
				
				// TODO set effort
			}
			
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
