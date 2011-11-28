package management;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import management.TaskInfo.StatusType;

public class MgmtTaskManager {

	private static int taskIdAssign = 0;
	
	public Map<Integer, TaskInfo> preparedTasks = new HashMap<Integer, TaskInfo>();

	public MgmtTaskManager() {
	}
	
//	MOVED TO COMPANYSCANNER
//	public Set<String> tasksInTaskDir() {
//		String[] tmp = taskDir.list();
//		Set<String> allTasks = new TreeSet<String>();
//		for(String s: tmp) {
//			allTasks.add(s);
//		}
//		return allTasks;
//	}
	
	/**
	 * prepares task for execution
	 * @param taskName name of task
	 * @param type level of effort
	 * @return true if task was successfully prepared, false if invalid taskName
	 */
	public int prepareTask(String taskName, String type) {

		if(!validType(type)) {
			return -1;
		}
		
		int newTaskId = ++taskIdAssign;
		preparedTasks.put(newTaskId, new TaskInfo(newTaskId, taskName, type, StatusType.PREPARED));
		return newTaskId;
	}
	
	private boolean validType(String type) {
		return type.equals("HIGH") || type.equals("LOW") || type.equals("MIDDLE");
	}
	
	public boolean taskExists(int id) {
		return preparedTasks.containsKey(id);
	}

//	MOVED TO COMPANYSCANNER
//	/**
//	 * checks if task exists in taskDir
//	 * @param taskName name of task
//	 * @return true if task exists in taskDir
//	 */
//	private boolean valid(String taskName) {
//		return tasksInTaskDir().contains(taskName);
//	}

	public boolean checkPrepared(int taskId) {
		return preparedTasks.containsKey(taskId);
	}
	
	public String getEffort(int id) {
		return preparedTasks.get(id).getEffortType().toString();
	}
	
	public void assignEngine(int taskId, String address, int port) {
		preparedTasks.get(taskId).assignEngine(address, port);
	}

	public TaskInfo getTask(int taskId) {
		return preparedTasks.get(taskId);
	}

//	MOVED TO COMPANYSCANNER
//	public String toString() {
//		String[] tmp = taskDir.list();
//		String allTasks = "";
//		for(int i = 0; i < tmp.length; i++) {
//			allTasks += tmp[i] + "\n";
//		}
//		return allTasks;
//	}
}
