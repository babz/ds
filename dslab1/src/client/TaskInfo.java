package client;

public class TaskInfo {

	private enum EffortType { LOW, MIDDLE, HIGH }
	
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

	public EffortType getEffort() {
		return effort;
	}

	public void setEffort(String effordType) {
		if(effordType.equals("LOW")) {
			effort = EffortType.LOW;
		} else if (effordType.equals("MIDDLE")) {
			effort = EffortType.MIDDLE;
		} else if (effordType.equals("HIGH")) {
			effort = EffortType.HIGH;
		}
	}
	
	
}
