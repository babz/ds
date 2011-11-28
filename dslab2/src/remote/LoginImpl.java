package remote;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import scheduler.UserManager;

import management.AdminCallbackImpl;
import management.CompanyCallbackImpl;



/**
 * Implements the Remote-Interface and implements login for one user
 * @author babz
 *
 */
public class LoginImpl implements ILogin {

	private UserManager cManager = null;
	private int prepCosts;

	public LoginImpl(int preparationCosts) throws IOException {
		cManager = UserManager.getInstance();
		prepCosts = preparationCosts;
	}

	@Override
	public IUser login(String userName, String pw) throws RemoteException, ManagementException {
		if(cManager.login(userName, pw)) {
			IUser user = null;
			if(cManager.getUserInfo(userName).isAdmin()) {
				//Admin Mode
				user = new AdminCallbackImpl(cManager.getUserInfo(userName));
			} else {
				//Company Mode
				user = new CompanyCallbackImpl(cManager.getUserInfo(userName), prepCosts);
			}
			UnicastRemoteObject.exportObject(user, 0);
			return user;
		} else {
			throw new ManagementException("login failed");
		}
	}

}
