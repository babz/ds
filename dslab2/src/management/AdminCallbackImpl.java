package management;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import remote.IAdminMode;


public class AdminCallbackImpl implements IAdminMode {

	private UserInfo admin;
	
	public AdminCallbackImpl(UserInfo adminInfo) {
		admin = adminInfo;
	}

	@Override
	public void logout() throws RemoteException {
		//callback instantiated only after successful login
		admin.setOffline();
		UnicastRemoteObject.unexportObject(this, false);
	}
	
	@Override
	public boolean isAdmin() throws RemoteException {
		return true;
	}

	@Override
	public String getPricingCurve() throws RemoteException {
		return admin.getPriceCurve();
	}

	@Override
	public void setPriceStep(int taskCount, double percent) throws RemoteException {
		if(taskCount < 0) {
			throw new RemoteException("Error: Invalid task count!");
		} else if (percent > 100) {
			throw new RemoteException("Error: Invalid percentage!");
		} else {
			admin.setPriceStep(taskCount, percent);
		}
	}
}
