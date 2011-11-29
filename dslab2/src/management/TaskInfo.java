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

	public TaskInfo(int id, String taskName, String taskEffort, String companyName, StatusType statusType) {
		this.id = id; 
		name = taskName;
		this.setEffort(taskEffort);
		this.companyName = companyName;
		status = statusType;
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
	
	public String getInfo() {
		String out = "Task " + id + " (" + name + ")";
		out += "\nType: " + effort.toString();
		if(engineAddress != null) {
			out += "\nAssigned Engine: " + engineAddress + ":" + enginePort;
		} else {
			out += "\nAssigned Engine: none";
		}
		out += "\nStatus: " + status.toString();
		return out;
	}
}
