package client;

import java.rmi.RemoteException;

import remote.ICompanyMode;
import remote.Task;

public class CompanyCallbackImpl implements ICompanyMode{

	/**
	 * returns available amount of credits of the client
	 * @param comp choosen company
	 * @return available amount of credits of the company
	 */
	public int availableCredit(Company comp) throws RemoteException {
		return 0;
	}

	/**
	 * companies buy a certain amount of credits
	 * @param credit amount of credit to buy
	 */
	public void buyCredit(int credit) throws RemoteException {

	}

	/**
	 * preparing a task which costs preparationCosts credits
	 * @param preparationCosts costs of preparation
	 * @return id of task
	 */
	public int prepareTask(int preparationCosts) throws RemoteException {
		int id = 0;
		return id;
	}

	/**
	 * costs 10 credits for each begun minute of execution
	 */
	@Override
	public <T> T executeTask(Task<T> t) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * gives status information on a task
	 * @param t task of which you want to know the status
	 * @return String with current status of task
	 */
	public String getStatusInfo(Task t) throws RemoteException {
		return "";
	}
	
	/**
	 * returns output of a task
	 * @param t Task that has been executed
	 * @return the output of the task
	 */
	private bla getOutput(Task t) throws RemoteException {
		
	}

	/**
	 * logout company
	 * @param comp company to be logged out
	 * @throws RemoteException
	 */
	public void logout(Company comp) throws RemoteException {
		
	}
}
