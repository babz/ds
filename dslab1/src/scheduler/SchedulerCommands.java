package scheduler;

import java.io.IOException;
import java.util.Scanner;

import sun.security.jca.GetInstance;

public class SchedulerCommands {
	private Scanner sc = new Scanner(System.in);
	private GTEManager manager;
	private CompanyManager companies;

	public SchedulerCommands(GTEManager engineManager) throws IOException {
		manager = engineManager;
		companies = CompanyManager.getInstance();
	}

	public void read() {
		//scheduler commands: engines, companies, exit
		while(sc.hasNext()) {
			String command = sc.next();
			if(command.equals("!engines")) {
				System.out.println(manager);
			} else if (command.equals("!companies")) {
				System.out.println(companies);
			} else if (command.equals("!exit")) {
				//TODO executor service shutdown
				//logout each company
			} else {
				System.out.println("Invalid command!");
			}
		}
	}
}
