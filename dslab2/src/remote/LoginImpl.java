package remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.CompanyCallbackImpl;


/**
 * Implements the Remote-Interface and implements login
 * @author babz
 *
 */
public class LoginImpl implements ILogin {

	@Override
	public ICompanyMode login() throws RemoteException {
		ICompanyMode company = new CompanyCallbackImpl();
		UnicastRemoteObject.exportObject(company, 0);
		return company;
	}

}
