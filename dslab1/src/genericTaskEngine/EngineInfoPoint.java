package genericTaskEngine;

import java.util.Scanner;

/**
 * reads from terminal and gives informations
 * @author babz
 *
 */
public class EngineInfoPoint {
	private Scanner sc = new Scanner(System.in);
	
	public void read() {
		//engine commands: load, exit
		while(sc.hasNext()) {
			String command = sc.next();
			if(command.equals("!load")) {
				//TODO
				int load = 0;
				System.out.println("Current load: " + load + "%");
			} else if (command.equals("!exit")) {
				//TODO executor service shutdown
				//disconnect each client
			} else {
				System.out.println("Invalid command!");
			}
		}
	}

}
