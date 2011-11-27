package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ICompanyMode extends Remote {

	/**
	 * logs out company
	 * @throws RemoteException
	 */
	void logout() throws RemoteException;
	
	/**
	 * get the credits of a company
	 * @return amount of credits the company owns
	 * @throws RemoteException
	 */
	int getCredit() throws RemoteException;
	
	/**
	 * buys the given amount of credits
	 * @param amount number of credits that a company wants to buy
	 * @return message
	 * @throws RemoteException thrown if amount is negative
	 */
	int buyCredits(int amount) throws RemoteException;
	
	/**
	 * company credits are reduced for the preparation of the task
	 * @param taskName name of task
	 * @param taskType either "low", "middle" or "high"
	 * @return unique id for the task is returned; preparation successful
	 * @throws RemoteException thrown if company doesn't have enough credits and task is being ignored
	 */
	int prepareTask(String taskName, String taskType) throws RemoteException;
	
	/**
	 * executes task by forwarding to an engine
	 * @param taskId unique id of task
	 * @param startScript "java -jar task<id>.jar"
	 * @return message of execution success
	 * @throws RemoteException thrown if either there is no free engine for execution or the task belongs to another company
	 */
	String executeTask(int taskId, String startScript) throws RemoteException;
	
	/**
	 * prints out information for the specified task
	 * @param taskId
	 * @return message
	 * @throws RemoteException thrown if task id unknown or task doesn't belong to the logged in company
	 */
	String getInfo(int taskId) throws RemoteException;
	
	/**
	 * output of a finished task from the management component
	 * @param taskId id of task for which the output is required
	 * @return output printed if task belongs to company and task has been finished and paid;
	 * 					otherwise make the user pay first
	 * @throws RemoteException if not enough credit or task doesn't belong to company
	 */
	String getOutput(int taskId) throws RemoteException;
}
