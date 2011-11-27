package management;

import java.rmi.RemoteException;

import remote.ICompanyMode;


public class CompanyCallbackImpl implements ICompanyMode {
	
	private UserInfo company;

	public CompanyCallbackImpl(UserInfo companyInfo) {
		company = companyInfo;
	}

	@Override
	public void logout() throws RemoteException {
		//callback instantiated only after successful login
		company.setOffline();
	}
	
	@Override
	public int getCredit() throws RemoteException {
		return company.getCredits();
	}

	@Override
	public int buyCredits(int amount) throws RemoteException {
		return company.increaseCredit(amount);
	}

	@Override
	public int prepareTask(String taskName, String taskType)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String executeTask(int taskId, String startScript)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo(int taskId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutput(int taskId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
