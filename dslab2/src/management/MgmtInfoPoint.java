package management;

import java.io.IOException;
import java.util.Scanner;

import scheduler.GTEManager;

public class MgmtInfoPoint {

	private Scanner sc = new Scanner(System.in);
	private UserManager user;
	private MgmtTaskManager tasks;

	public MgmtInfoPoint(UserManager userManager, MgmtTaskManager taskManager) throws IOException {
		user = userManager;
		tasks = taskManager;
	}

	public void read() {
		while(sc.hasNext()) {
			String command = sc.next();
			if (command.equals("!users")) {
				System.out.println(user.getUsersAndTasks(tasks));
			} else if (command.equals("!exit")) {
				return;
			} else {
				System.out.println("Invalid command!");
			}
		}
	}

}
