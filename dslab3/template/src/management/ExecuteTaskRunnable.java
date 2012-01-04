package management;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import management.TaskInfo.StatusType;
import remote.INotifyClientCallback;

public class ExecuteTaskRunnable implements Runnable {

	private TaskInfo task;
	private INotifyClientCallback clientCb;
	private String script;

	public ExecuteTaskRunnable(int taskId, INotifyClientCallback callback, String startScript) {
		task = MgmtTaskManager.getInstance().getTask(taskId);
		clientCb = callback;
		script = startScript;
	}

	@Override
	public void run() {
		//TODO: note: relief #1 for sending file to engine
		String address = task.getAssignedEngineAddress();
		int port = task.getAssignedEnginePort();
		Socket engineSocket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			engineSocket = new Socket(address, port);
			 in = new BufferedReader(new InputStreamReader(engineSocket.getInputStream()));
			 out = new PrintWriter(engineSocket.getOutputStream());

			//listen to result of engine bzw. msg "Execution of task <taskId> finished."
			out.println("!executeTask " + task.getEffortType() + " " + script);
			task.setStatus(StatusType.EXECUTING);
			
			String lineFromEngine;
			StringBuffer taskOutput = new StringBuffer();
			while((lineFromEngine = in.readLine()) != null) {
				taskOutput.append(lineFromEngine);
				System.out.println("read line: " + lineFromEngine);
			}
			task.setOutput(taskOutput.toString());
			task.setStatus(StatusType.FINISHED);
			
			clientCb.sendNotification(task.getId());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("closing streams");
				in.close();
				out.close();
				engineSocket.close();
			} catch (IOException e) {
			}
		}
	}

}
