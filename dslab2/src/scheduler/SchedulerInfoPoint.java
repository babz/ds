package scheduler;

import java.io.IOException;
import java.util.Scanner;

/**
 * reads from terminal and gives informations
 * @author babz
 *
 */
public class SchedulerInfoPoint {
	private Scanner sc = new Scanner(System.in);
	private GTEManager manager;
	private CompanyManager companies;

	public SchedulerInfoPoint(GTEManager engineManager) throws IOException {
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
				return;
				
			} else {
				System.out.println("Invalid command!");
			}
		}
	}
}
