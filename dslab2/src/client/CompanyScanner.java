package client;

import java.rmi.RemoteException;

import remote.ICompanyMode;

public class CompanyScanner implements ICommandScanner {

	private ICompanyMode company;

	public CompanyScanner(ICompanyMode loggedInCompany) {
		company = loggedInCompany;
	}

	@Override
	public void readCommand(String[] cmd) throws RemoteException {
		if (cmd[0].equals("!list")) {
			if(!checkNoOfArgs(cmd, 0)) {
				return;
			}
			//TODO
		} else if (cmd[0].equals("!credits")) {
			if(!checkNoOfArgs(cmd, 0)) {
				return;
			}
			System.out.println("You have " + company.getCredit() + " credits left.");
		} else if (cmd[0].equals("!buy")) {
			if(!checkNoOfArgs(cmd, 1)) {
				return;
			}
			int credit = Integer.parseInt(cmd[1]);
			if(credit < 0) {
				System.out.println("Invalid amount of credits.");
				return;
			}
			company.buyCredits(credit);
			System.out.println("Successfully bought credits. You have " + company.getCredit() + " credits left.");
		} else if (cmd[0].equals("!prepare")) {
			if(!checkNoOfArgs(cmd, 2)) {
				return;
			}
			//TODO
		} else if (cmd[0].equals("!executeTask")) {
			if(!checkNoOfArgs(cmd, 2)) {
				return;
			}
			//TODO
		} else if (cmd[0].equals("!info")) {
			if(!checkNoOfArgs(cmd, 1)) {
				return;
			}
			//TODO
		} else if (cmd[0].equals("!getOutput")) {
			if(!checkNoOfArgs(cmd, 1)) {
				return;
			}
			//TODO
		} else {
			System.out.println("Command not allowed. You are not a company.");
		}
	}

	private boolean checkNoOfArgs(String[] cmd, int noOfSupposedArgs) {
		if((cmd.length - 1) != noOfSupposedArgs) {
			System.out.println("Supposed number of arguments: " + noOfSupposedArgs);
			return false;
		}
		return true;
	}

	@Override
	public void logout() throws RemoteException {
		company.logout();
	}
}

