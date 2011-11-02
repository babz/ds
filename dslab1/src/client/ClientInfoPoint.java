package client;

import java.io.File;
import java.util.Scanner;

public class ClientInfoPoint {

	private Scanner sc = new Scanner(System.in);
	private TaskManager taskManager;

	public ClientInfoPoint(TaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public void read() {
		//client commands: list, prepare
		while(sc.hasNext()) {
			String command = sc.next();
			if(command.equals("!list")) {
				System.out.println(taskManager);
			} else if (command.equals("!prepare")) {
				String taskName, type;
				if(sc.hasNext()) {
					taskName = sc.next();
					if(sc.hasNext()) {
						type = sc.next();
						int prepared = taskManager.prepareTask(taskName, type);
						if(prepared == -1) {
							System.out.println("Task not found.");
						} else {
							System.out.println("Task with id " + prepared + " prepared.");
						}
					}
				} else {
					System.out.println("Invalid command!");
				}
			}
		}
	}
}