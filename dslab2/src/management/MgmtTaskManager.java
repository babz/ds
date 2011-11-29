package management;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import management.TaskInfo.StatusType;

public class MgmtTaskManager {

	private static int taskIdAssign = 0;
	private static MgmtTaskManager instance;
	
	private Map<Integer, TaskInfo> preparedTasks = new HashMap<Integer, TaskInfo>();

	private MgmtTaskManager() {}
	
	public static synchronized MgmtTaskManager getInstance() {
		if(instance == null) {
			instance = new MgmtTaskManager();
		}
		return instance;
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
	 * @param companyName name of company that requests the task
	 * @return true if task was successfully prepared, false if invalid taskName
	 */
	public int prepareTask(String taskName, String type, String companyName) {

		if(!validType(type)) {
			return -1;
		}
		
		int newTaskId = ++taskIdAssign;
		preparedTasks.put(newTaskId, new TaskInfo(newTaskId, taskName, type, companyName, StatusType.PREPARED));
		return newTaskId;
	}
	
	//TODO methods for getting finished tasks by company by type
	
	public Set<Integer> getPreparedTasksByCompany(String name) {
		Set<Integer> tasks = new TreeSet<Integer>();
		for(Entry<Integer, TaskInfo> task: preparedTasks.entrySet()) {
			if(name.equals(task.getValue().getCompanyName())) {
				tasks.add(task.getKey());
			}
		}
		return tasks;
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
