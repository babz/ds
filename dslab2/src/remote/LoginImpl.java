package remote;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import scheduler.CompanyManager;

import management.CompanyCallbackImpl;



/**
 * Implements the Remote-Interface and implements login for one user
 * @author babz
 *
 */
public class LoginImpl implements ILogin {

	private CompanyManager cManager = null;

	public LoginImpl() throws IOException {
		cManager = CompanyManager.getInstance();
	}

	@Override
	public ICompanyMode login(String companyName, String pw) throws RemoteException, ManagementException {
		if(cManager.login(companyName, pw)) {
			System.out.println("Successfully logged in. Using company mode.");
			ICompanyMode company = new CompanyCallbackImpl(cManager.getCompanyInfo(companyName));
			UnicastRemoteObject.exportObject(company, 0);
			return company;
		} else {
			throw new ManagementException("login failed");
		}
	}

}
