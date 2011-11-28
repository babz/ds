package management;

import java.io.IOException;
import java.util.Scanner;

import scheduler.UserManager;
import scheduler.GTEManager;

public class MgmtInfoPoint {

	private Scanner sc = new Scanner(System.in);
	private GTEManager manager;
	private UserManager companies;

	public MgmtInfoPoint() throws IOException {
//		manager = engineManager;
//		companies = CompanyManager.getInstance();
	}

	public void read() {
		//scheduler commands: engines, companies, exit
		while(sc.hasNext()) {
			String command = sc.next();
			if (command.equals("!users")) {
				//TODO
				System.out.println();
			} else if (command.equals("!exit")) {
				return;
			} else {
				System.out.println("Invalid command!");
			}
		}
	}

}
