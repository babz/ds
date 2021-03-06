package management;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import remote.ILogin;
import remote.IUser;
import remote.ManagementException;


/**
 * Implements the Remote-Interface and implements login for one user
 * @author babz
 *
 */
public class LoginImpl implements ILogin {

	private UserManager cManager = null;

	public LoginImpl() throws IOException {
		cManager = UserManager.getInstance();
	}

	@Override
	public IUser login(String userName, String pw) throws ManagementException, RemoteException {
		if(cManager.login(userName, pw)) {
			IUser user = null;
			if(cManager.getUserInfo(userName).isAdmin()) {
				//Admin Mode
				user = new AdminCallbackImpl(cManager.getUserInfo(userName));
			} else {
				//Company Mode
				user = new CompanyCallbackImpl(cManager.getUserInfo(userName));
			}
			UnicastRemoteObject.exportObject(user, 0);
			return user;
		} else {
			throw new ManagementException("login failed");
		}
	}

}
