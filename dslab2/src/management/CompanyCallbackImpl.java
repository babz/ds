package management;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import remote.ICompanyMode;


public class CompanyCallbackImpl implements ICompanyMode {
	
	private UserInfo company;
	private int prepCosts;

	public CompanyCallbackImpl(UserInfo companyInfo, int preparationCosts) {
		company = companyInfo;
		prepCosts = preparationCosts;
	}

	@Override
	public void logout() throws RemoteException {
		//callback instantiated only after successful login
		company.setOffline();
		UnicastRemoteObject.unexportObject(this, false);
	}
	
	@Override
	public int getCredit() throws RemoteException {
		return company.getCredits();
	}

	@Override
	public int buyCredits(int amount) throws RemoteException {
		if(amount < 0) {
			throw new RemoteException("Invalid amount of credits.");
		}
		return company.increaseCredit(amount);
	}

	@Override
	public int prepareTask(String taskName, String taskType)
			throws RemoteException {
		if(company.getCredits() < prepCosts) {
			throw new RemoteException("Not enough credits to prepare a task.");
		}
		company.decreaseCredit(prepCosts);
		//TODO increase EffordTypeCounter
		//TODO change status type of task
		//TODO create unique id for whole cloud
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
		// TODO throw exc for unknown id
		//TODO throw exc for wrong company
		//TODO return details
		return null;
	}

	@Override
	public String getOutput(int taskId) throws RemoteException {
		// TODO throw exc for unfinished task
		//TODO throw new exc for task not of company
		//TODO throw new exc for unexistent task
		//TODO buy credits first
		//TODO return output
		return null;
	}

	@Override
	public boolean isAdmin() throws RemoteException {
		return false;
	}

}
