package GTEs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientConnection implements Runnable {

	private static final int BUF_LENGTH = 100;
	private Socket sock;
	private String dir;
	private EngineManager manager;
	private DataInputStream in = null;
	private DataOutputStream out = null;
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
				@SuppressWarnings("unused")
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
				
				fos.close();
				
				// TODO set status of file to executable
				
				System.out.println("finished reading file from client");
				
				out.writeUTF("Starting execution");
				
				
				manager.addLoad(load);
				
				// TODO execute and write back to client
				Thread.sleep(20000); // simulate execution
				
				out.writeUTF("task completed successfully");

				
				manager.removeLoad(load);
			} else if (cmd[0].equals("!currentLoad")) {
				out.writeInt(manager.getLoad());
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
			} catch (IOException e) { }
		}
	}

	public void terminate() {
		try {
			out.writeUTF("Engine shutting down");
			
			out.close();
			in.close();
			sock.close();
		} catch (IOException e) { }
	}

}
