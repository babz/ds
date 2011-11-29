package management;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import remote.ICompanyMode;

public class CompanyCallbackImpl implements ICompanyMode {

	private UserInfo company;
	private int prepCosts;
	private MgmtTaskManager taskManager;

	public CompanyCallbackImpl(UserInfo companyInfo,
			MgmtTaskManager taskManager, int preparationCosts) {
		company = companyInfo;
		this.taskManager = taskManager;
		prepCosts = preparationCosts;
	}

	@Override
	public void logout() throws RemoteException {
		// callback instantiated only after successful login
		company.setOffline();
		UnicastRemoteObject.unexportObject(this, false);
	}

	@Override
	public int getCredit() throws RemoteException {
		return company.getCredits();
	}

	@Override
	public int buyCredits(int amount) throws RemoteException {
		if (amount < 0) {
			throw new RemoteException("Error: Invalid amount of credits.");
		}
		return company.increaseCredit(amount);
	}

	@Override
	public int prepareTask(String taskName, String taskType)
			throws RemoteException {
		if (company.getCredits() < prepCosts) {
			throw new RemoteException("Error: Not enough credits to prepare a task.");
		}
		company.decreaseCredit(prepCosts);
		return taskManager.prepareTask(taskName, taskType, company.getName());
	}

	@Override
	public String executeTask(int taskId, String startScript)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInfo(int taskId) throws RemoteException {
		checkTaskExistanceAndOwner(taskId);
		return taskManager.getTask(taskId).getInfo();
	}

	@Override
	public String getOutput(int taskId) throws RemoteException {
		checkTaskExistanceAndOwner(taskId);
		if (!taskManager.checkFinished(taskId)) {
			throw new RemoteException("Error: Task " + taskId
					+ " has not been finished yet.");
		}
		int costs = taskManager.calculateCostsForTask(taskId);
		if (costs > company.getCredits()) {
			throw new RemoteException(
					"Error: You do not have enough credits to pay this execution. (Costs: "
							+ costs
							+ " credits) Buy new credits for retrieving the output.");
		}
		company.decreaseCredit(costs);
		// TODO return output
		return null;
	}

	private void checkTaskExistanceAndOwner(int taskId) throws RemoteException {
		if (!taskManager.taskExists(taskId)) {
			throw new RemoteException("Error: Task " + taskId + " doesn't exist.");
		}
		if (!taskManager.checkTaskOwner(taskId, company.getName())) {
			throw new RemoteException("Error: Task "
					+ " does not belong to your company.");
		}
	}

	@Override
	public boolean isAdmin() throws RemoteException {
		return false;
	}

}
