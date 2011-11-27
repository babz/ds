package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAdminMode extends Remote {
	
	//Admins
	public int adjustPricing() throws RemoteException;
	
}
