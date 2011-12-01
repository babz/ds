package management;


/**
 * handles ONE task
 * @author babz
 *
 */
public class TaskInfo {

	public enum EffortType { LOW, MIDDLE, HIGH }
	public enum StatusType { PREPARED, EXECUTING, FINISHED }
	
	private int id;
	private String name;
	private EffortType effort;
	private StatusType status;
	private int enginePort;
	private String engineAddress;
	private String companyName;
	private String output;

	public TaskInfo(int id, String taskName, String taskEffort, String companyName, StatusType statusType) {
		this.id = id; 
		name = taskName;
		this.setEffort(taskEffort);
		this.companyName = companyName;
		status = statusType;
		output = null;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public EffortType getEffortType() {
		return effort;
	}
	
	public StatusType getStatus() {
		return status;
	}
	
	public void setStatus(StatusType statusType) {
		status = statusType;
	}

	public void setEffort(String effortType) {
		if(effortType.equals("LOW")) {
			effort = EffortType.LOW;
		} else if (effortType.equals("MIDDLE")) {
			effort = EffortType.MIDDLE;
		} else if (effortType.equals("HIGH")) {
			effort = EffortType.HIGH;
		}
	}

	public void assignEngine(String address, int port) {
		engineAddress = address;
		enginePort = port;
	}

	public String getAssignedEngineAddress() {
		return engineAddress;
	}

	public int getAssignedEnginePort() {
		return enginePort;
	}
	
	public int getCosts() {
		/* RELIEF:
		 * low = 0,5 min
		 * middle = 3 min
		 * high = 5 min
		 */
		//TODO not so easy cheasy babz
		int costsPerMin = 10;
		if(effort == EffortType.LOW) {
			return (int) (costsPerMin * 0.5);
		} else if (effort == EffortType.MIDDLE) {
			return costsPerMin * 3;
		} else {
			return costsPerMin * 5;
		}
	}
	
	public void setOutput(String outputFromEngine) {
		output = outputFromEngine;
	}
	
	public String getOutput() {
		return output;
	}
	
	public String getInfo() {
		String out = "Task " + id + " (" + name + ")";
		out += "\nType: " + effort.toString();
		if(engineAddress != null) {
			out += "\nAssigned Engine: " + engineAddress + ":" + enginePort;
		} else {
			out += "\nAssigned Engine: none";
		}
		out += "\nStatus: " + status.toString();
		if(status != StatusType.FINISHED) {
			out += "\nCosts: unknown";
		} else {
			out += "\nCosts: " + getCosts();
		}
		return out;
	}
}
