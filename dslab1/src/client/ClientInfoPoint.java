package client;

import java.util.Scanner;

public class ClientInfoPoint {

	private Scanner sc = new Scanner(System.in);

	public void read() {
		//client commands: list, prepare
		while(sc .hasNext()) {
			String command = sc.next();
			if(command.equals("!list")) {
				//TODO
			} else if (command.equals("!prepare")) {
				String taskName, type;
				if(sc.hasNext()) {
					taskName = sc.next();
					if(sc.hasNext()) {
						type = sc.next();
						//TODO
					}
				} else {
					//TODO name eingeben!
				}
			} else {
				System.out.println("Invalid command!");
			}
		}
	}

}
