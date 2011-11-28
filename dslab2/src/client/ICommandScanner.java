package client;

import java.rmi.RemoteException;

public interface ICommandScanner {

	/**
	 * reads commands from commandline
	 * @param cmd command with args
	 * @throws RemoteException
	 */
	public void readCommand(String[] cmd) throws RemoteException;
	
	/**
	 * logs out either company or admin
	 * @throws RemoteException
	 */
	public void logout() throws RemoteException;
	
}
