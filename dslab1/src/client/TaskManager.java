package client;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TaskManager {

	private static int taskIdAssign = 0;
	
	private File taskDir;
	private Map<Integer, TaskInfo> preparedTasks = new HashMap<Integer, TaskInfo>();

	public TaskManager(File taskDir) {
		this.taskDir = taskDir;
	}
	
	public Set<String> tasksInTaskDir() {
		String[] tmp = taskDir.list();
		Set<String> allTasks = new TreeSet<String>();
		for(String s: tmp) {
			allTasks.add(s);
		}
		return allTasks;
	}
	
	/**
	 * prepares task for execution
	 * @param taskName name of task
	 * @param type level of effort
	 * @return true if task was successfully prepared, false if invalid taskName
	 */
	public int prepareTask(String taskName, String type) {
		if(!valid(taskName)) {
			return -1;
		}
		int newTaskId = ++taskIdAssign;
		preparedTasks.put(newTaskId, new TaskInfo(taskName, type));
		return newTaskId;
	}
	
	/**
	 * checks if task exists in taskDir
	 * @param taskName name of task
	 * @return true if task exists in taskDir
	 */
	private boolean valid(String taskName) {
		return tasksInTaskDir().contains(taskName);
	}

	public boolean checkPrepared(int taskId) {
		return preparedTasks.containsKey(taskId);
	}
	
	public String toString() {
		String[] tmp = taskDir.list();
		String allTasks = "";
		for(int i = 0; i < tmp.length; i++) {
			allTasks += tmp[i] + "\n";
		}
		return allTasks;
	}
}
