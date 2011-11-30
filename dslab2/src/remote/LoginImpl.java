package remote;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import management.AdminCallbackImpl;
import management.CompanyCallbackImpl;
import management.MgmtTaskManager;
import management.UserManager;

/**
 * Implements the Remote-Interface and implements login for one user
 * @author babz
 *
 */
public class LoginImpl implements ILogin {

	private UserManager cManager = null;
	private MgmtTaskManager taskManager;
	private int prepCosts;
	private String schedulerHost;
	private int schedulerPort;

	public LoginImpl(int preparationCosts, String host, int port) throws IOException {
		cManager = UserManager.getInstance();
		taskManager = MgmtTaskManager.getInstance();
		prepCosts = preparationCosts;
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
				user = new CompanyCallbackImpl(cManager.getUserInfo(userName), taskManager, prepCosts, schedulerHost, schedulerPort);
			}
			UnicastRemoteObject.exportObject(user, 0);
			return user;
		} else {
			throw new ManagementException("login failed");
		}
	}

}
