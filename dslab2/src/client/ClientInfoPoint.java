package client;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

import remote.IAdminMode;
import remote.ICompanyMode;
import remote.ILogin;
import remote.IUser;
import remote.ManagementException;

/**
 * reads commands from command line of client
 * differentiate between admins and companies in management
 * @author babz
 *
 */
public class ClientInfoPoint {

	private Scanner sc = new Scanner(System.in);
	private ILogin login;
	private ICommandScanner cmdScanner = null;

	public ClientInfoPoint(ILogin rmLogin) throws IOException {
		login = rmLogin;
	}

	public void read() throws RemoteException {
		while(sc.hasNextLine()) {
			String[] cmd = sc.nextLine().split(" ");
			if (cmd[0].equals("!login")) {
				String companyName = cmd[1];
				String pw = cmd[2];
				try {
					IUser user = login.login(companyName, pw);
					if(user.isAdmin()) {
						cmdScanner = new AdminScanner((IAdminMode) user);
						System.out.println("Successfully logged in. Using admin mode.");

					} else {
						cmdScanner = new CompanyScanner((ICompanyMode) user);
						System.out.println("Successfully logged in. Using company mode.");
					}

				} catch (ManagementException ex) {
					System.out.println("Wrong name or password.");
				}
			} else if (cmd.equals("!logout")) {
				if(cmdScanner == null) {
					System.out.println("You have to log in first.");
					continue;
				}
				cmdScanner.logout();
				System.out.println("Successfully logged out.");
			} else {
				if(cmdScanner == null) {
					System.out.println("You have to log in first.");
					continue;
				}
				cmdScanner.readCommand(cmd);
			}				
		}
	}
}
