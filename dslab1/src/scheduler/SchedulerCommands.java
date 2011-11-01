package scheduler;

import java.util.Scanner;

public class SchedulerCommands {
	private Scanner sc = new Scanner(System.in);
	private GTEManager manager;

	public SchedulerCommands(GTEManager engineManager) {
		manager = engineManager;
	}

	public void read() {
		//scheduler commands: engines, companies, exit
		while(sc.hasNext()) {
			String command = sc.next();
			if(command.equals("!engines")) {
				System.out.println(manager);
			} else if (command.equals("!companies")) {
				System.out.println("companies");
			} else if (command.equals("!exit")) {
				//executor service shutdown
				//logout each company
			} else {
				System.out.println("Invalid command!");
			}
		}
	}
}
