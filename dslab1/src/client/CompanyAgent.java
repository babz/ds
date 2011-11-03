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

	private PrintWriter serverWriter;
	private boolean alive;
	private BufferedReader streamIn;

	private TaskManager taskManager;


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
				//TODO scanner auf printwriter umstellen
				String command = input[0];
				if(command.equals("!list")) {
					System.out.println(taskManager);
				} else if (command.equals("!prepare")) {
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
				} else if (command.equals("!request")) {
					if(input.length != 2) {
						System.out.println("Usage: !requestEngine <taskId>");
						continue;
					}
					int taskId = Integer.parseInt(input[1]);
					if (!taskManager.checkPrepared(taskId)) {
						System.out.println("No task with Id " + taskId + " prepared.");
					}
					String effortType = taskManager.getEffort(taskId);
					serverWriter.println(command + " " + effortType);
				} else {
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
}
