package client;

public class TaskInfo {

	public enum EffortType { LOW, MIDDLE, HIGH }
	
	private String name;
	private EffortType effort;

	public TaskInfo(String taskName, String taskEffort) {
		name = taskName;
		this.setEffort(taskEffort);
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

	public void setEffort(String effortType) {
		if(effortType.equals("LOW")) {
			effort = EffortType.LOW;
		} else if (effortType.equals("MIDDLE")) {
			effort = EffortType.MIDDLE;
		} else if (effortType.equals("HIGH")) {
			effort = EffortType.HIGH;
		}
	}
	
	
}
