package management;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import remote.ICompanyMode;
import remote.ManagementException;

public class CompanyCallbackImpl implements ICompanyMode {

	private UserInfo company;
	private int prepCosts;
	private MgmtTaskManager taskManager;
	private String host;
	private int tcpPort;
	private SchedulerConnectionManager scheduler;

	public CompanyCallbackImpl(UserInfo companyInfo,
			MgmtTaskManager taskManager, int preparationCosts,
			String schedulerHost, int schedulerPort) {
		company = companyInfo;
		this.taskManager = taskManager;
		prepCosts = preparationCosts;
		host = schedulerHost;
		tcpPort = schedulerPort;
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
	public int buyCredits(int amount) throws RemoteException,
	ManagementException {
		if (amount < 0) {
			throw new ManagementException("Invalid amount of credits.");
		}
		return company.increaseCredit(amount);
	}

	@Override
	public int prepareTask(String taskName, String taskType)
			throws RemoteException, ManagementException {
		if (company.getCredits() < prepCosts) {
			throw new ManagementException(
					"Not enough credits to prepare a task.");
		}
		company.decreaseCredit(prepCosts);
		return taskManager.prepareTask(taskName, taskType, company.getName());
	}

	@Override
	public String executeTask(int taskId, String startScript)
			throws RemoteException, ManagementException {
		// TODO fehlerfälle
		checkTaskExistanceAndOwner(taskId);
		try {
			String assignedEngine = requestEngine(taskId);
			String[] engineDetails = assignedEngine.split(":");
			String address = engineDetails[0];
			int port = Integer.parseInt(engineDetails[1]);
			taskManager.assignEngine(taskId, address, port);
			taskManager.getTask(taskId).setOutput(getEngineOutput(address, port));
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private String requestEngine(int taskId) throws NumberFormatException,
	IOException, ManagementException {
		scheduler = SchedulerConnectionManager.getInstance(host, tcpPort);
		String assignedEngine = scheduler.requestEngine(taskManager
				.getEffort(taskId));
		if (assignedEngine == null) {
			throw new ManagementException("Connection to scheduler failed.");
		}
		if (assignedEngine.startsWith("!engineRequestFailed")) {
			throw new ManagementException(
					"No engine available for execution. Please try again later.");
		}
		return assignedEngine;

	}

	private String getEngineOutput(String address, int port) throws IOException, ManagementException {
		// TODO open tcp connection to engine and forward task
		Socket engineSocket = new Socket(address, port);
		DataInputStream in = new DataInputStream(engineSocket.getInputStream());
		DataOutputStream out = new DataOutputStream(engineSocket.getOutputStream());

		if(in == null) {
			throw new ManagementException("Error: engine output is null!");
		}
		//TODO listen to result of engine bzw. msg "Execution of task <taskId> finished."
		
		out.close();
		in.close();
		engineSocket.close();
		return null;
	}

	@Override
	public String getInfo(int taskId) throws RemoteException,
	ManagementException {
		checkTaskExistanceAndOwner(taskId);
		return taskManager.getTask(taskId).getInfo();
	}

	@Override
	public String getOutput(int taskId) throws RemoteException,
	ManagementException {
		checkTaskExistanceAndOwner(taskId);
		if (!taskManager.checkFinished(taskId)) {
			throw new ManagementException("Task " + taskId
					+ " has not been finished yet.");
		}
		int costs = taskManager.calculateCostsForTask(taskId);
		if (costs > company.getCredits()) {
			throw new ManagementException(
					"You do not have enough credits to pay this execution. (Costs: "
							+ costs
							+ " credits) Buy new credits for retrieving the output.");
		}
		company.decreaseCredit(costs);
		//TODO consider discount
		return taskManager.getTask(taskId).getOutput();
	}

	private void checkTaskExistanceAndOwner(int taskId) throws RemoteException,
	ManagementException {
		if (!taskManager.taskExists(taskId)) {
			throw new ManagementException("Task " + taskId + " doesn't exist.");
		}
		if (!taskManager.checkTaskOwner(taskId, company.getName())) {
			throw new ManagementException("Task "
					+ " does not belong to your company.");
		}
	}

	@Override
	public boolean isAdmin() throws RemoteException {
		return false;
	}

}
