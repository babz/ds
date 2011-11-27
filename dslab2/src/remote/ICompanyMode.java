package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import remote.Task;


public interface ICompanyMode extends Remote {

	//Companies
	<T> T executeTask(Task<T> t) throws RemoteException;
}
