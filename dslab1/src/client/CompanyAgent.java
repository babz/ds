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
	private static final int BUF_LENGTH = 100;

	private static Logger log = Logger.getLogger("class client socket chatter");

	private PrintWriter serverWriter;
	private boolean alive;
	private BufferedReader streamIn;

	private TaskManager taskManager;

	private boolean loggedIn = false;


	//establish the socket connection between client and server
	public CompanyAgent(Socket clientSocket, TaskManager taskManager) throws IOException{
		log.info("init");
		serverWriter = new PrintWriter(clientSocket.getOutputStream(), true);
		alive = true;
		this.taskManager = taskManager;
	}


	//read one line at a time from the input stream and send to server by writing it to PrintWriter
	@Override
	public void run(){
		log.info("forward requests to scheduler");
		streamIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		try {
			while(alive && ((userInput = streamIn.readLine()) != null)) {
				String[] input = userInput.split(" ");
				String command = input[0];
				//locally: !list
				if(command.equals("!list")) {
					System.out.println(taskManager);
				} 
				//locally: !prepare <taskname> <type>
				else if (command.equals("!prepare")) {
					if(!loggedIn) {
						System.out.println("You have to login first");
						continue;
					}
					if(input.length != 3) {
						System.out.println("Usage: !prepare <taskname> <type>");
						continue;
					}
					String taskName = input[1];
					String type = input[2];
					int prepared = taskManager.prepareTask(taskName, type);
					if(prepared == -1) {
						System.out.println("Task not found.");
					} else {
						System.out.println("Task with id " + prepared + " prepared.");
					}
				} 
				//!requestEngine <taskId>.effort
				else if (command.equals("!requestEngine")) {
					if(input.length != 2) {
						System.out.println("Usage: !requestEngine <taskId>");
						continue;
					}
					int taskId = Integer.parseInt(input[1]);
					if (!taskManager.checkPrepared(taskId)) {
						System.out.println("No task with Id " + taskId + " prepared.");
						continue;
					}
					String effortType = taskManager.getEffort(taskId);
					serverWriter.println(command + " " + taskId + " " + effortType);
				} 
				//TODO !executeTask <taskId>.effort <startScript>
				else if (command.equals("!executeTask")) {
					if (input.length != 3) {
						System.out.println("Usage: !executeTask <taskId> <startScript>");
						continue;
					}
					int taskId = Integer.parseInt(input[1]);
					if (!taskManager.checkPrepared(taskId)) {
						System.out.println("No task with Id " + taskId + " prepared.");
						continue;
					}
					TaskInfo task = taskManager.getTask(taskId);

					// open connection to assigned engine
					Socket socket = new Socket(task.getAssignedEngineAddress(), task.getAssignedEnginePort());
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					DataInputStream in = new DataInputStream(socket.getInputStream());

					System.out.println("writing cmd to GTE");
					out.writeUTF("!executeTask " + task.getEffortType() + " " + input[2] + " " + task.getName());

					// TODO write file to engine
					FileInputStream fis = new FileInputStream(taskManager.getTaskDir() + File.separator + task.getName());
					
					
					byte[] buf = new byte[BUF_LENGTH];
					System.out.println("writing data to GTE");
					while(fis.read(buf) != -1) {
						out.write(buf);
					}

					try {
						while(true) {
							System.out.println("waiting for answer");
							String answer = in.readUTF();
							System.out.println(answer);
						}
					} catch (IOException e) {
						// no more output from engine
					}
					
					//closeAll after finishing
					out.close();
					in.close();
					socket.close();

				}
				//!info <taskId>.effort
				else if (command.equals("!info")) {
					if (input.length != 2) {
						System.out.println("Usage: !info <taskId>");
						continue;
					}
					int taskId = Integer.parseInt(input[1]);
					if (!taskManager.checkPrepared(taskId)) {
						System.out.println("No task with Id " + taskId + " prepared.");
						continue;
					}
					String effortType = taskManager.getEffort(taskId);
					serverWriter.println(command + " " + effortType);
				}
				//forward to server
				else {
					serverWriter.println(userInput);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void terminate() throws IOException {
		alive = false;
		serverWriter.close();
		streamIn.close();
	}


	public void loggedIn() {
		loggedIn = true;
	}
	
	public void loggedOut() {
		loggedIn = false;
	}
}
