package client;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

import remote.ICompanyMode;
import remote.ILogin;
import remote.ManagementException;
import scheduler.CompanyManager;
import scheduler.GTEManager;

/**
 * reads commands from command line of client
 * differentiate between admins and companies in management
 * @author babz
 *
 */
public class ClientInfoPoint {
	private Scanner sc = new Scanner(System.in);
	private CompanyManager companies;
	private ILogin login;
	private ICompanyMode loggedInCompany;

	public ClientInfoPoint(ILogin rmLogin) throws IOException {
		login = rmLogin;
		companies = CompanyManager.getInstance();
	}

	public void read() throws RemoteException {
		//scheduler commands: engines, companies, exit
		while(sc.hasNext()) {
			String command = sc.next();
			if (command.equals("!login")) {
				String companyName = "";
				if(sc.hasNext()) {
					companyName = sc.next();
					String pw = "";
					if(sc.hasNext()) {
						pw = sc.next();
						try {
							loggedInCompany = login.login(companyName, pw);
							System.out.println("Successfully logged in. Using company mode.");
						} catch (ManagementException ex) {
							System.out.println("Wrong name or password.");
						}
					}
				}
			} else if (command.equals("!logout")) {
				if(loggedInCompany == null) {
					System.out.println("You have to log in first.");
					continue;
				}
				loggedInCompany.logout();
				System.out.println("Successfully logged out.");
			} else if (command.equals("!getPricingCurve")) {
				//TODO process to mgmt ADMIN
			} else if (command.equals("!setPriceStep")) {
				//TODO process to mgmt ADMIN
			} else if (command.equals("!list")) {
				//TODO LOCALLY COMPANY
				System.out.println(companies);
			} else if (command.equals("!credits")) {
				//TODO process to mgmt COMPANY
			} else if (command.equals("!buy")) {
				//TODO process to mgmt COMPANY
			} else if (command.equals("!prepare")) {
				//TODO process to mgmt COMPANY
			} else if (command.equals("!executeTask")) {
				//TODO process to mgmt COMPANY
			} else if (command.equals("!info")) {
				//TODO process to mgmt COMPANY
			} else if (command.equals("!getOutput")) {
				//TODO process to mgmt COMPANY
			} else if (command.equals("!exit")) {
				return;
			} else {
				System.out.println("Invalid command!");
			}
		}
	}
}
