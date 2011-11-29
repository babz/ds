package remote;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import management.AdminCallbackImpl;
import management.SchedulerConnectionManager;
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
	private SchedulerConnectionManager schedulerConnection;
	private int prepCosts, tcpPort;
	private String host;

	public LoginImpl(int preparationCosts, String schedulerHost, int schedulerTCPPort) throws IOException {
		cManager = UserManager.getInstance();
		taskManager = MgmtTaskManager.getInstance();
		prepCosts = preparationCosts;
		host = schedulerHost;
		tcpPort = schedulerTCPPort;
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
				user = new CompanyCallbackImpl(cManager.getUserInfo(userName), taskManager, prepCosts);
				try {
					schedulerConnection = SchedulerConnectionManager.getInstance(host, tcpPort, taskManager);
					new Thread(schedulerConnection).start();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			UnicastRemoteObject.exportObject(user, 0);
			return user;
		} else {
			throw new ManagementException("login failed");
		}
	}

}
