package scheduler;

/**
 * Company with all attributes
 * @author babz
 *
 */
public class CompanyInfo {

	private String name, pw;
	private int lowRequests = 0;
	private int middleRequests = 0;
	private int highRequests = 0;
	private StatusFlag status;
	
	public CompanyInfo(String name, String pw) {
		super();
		this.name = name;
		this.pw = pw;
		status = StatusFlag.OFFLINE;
	}
	
	private enum StatusFlag {ONLINE, OFFLINE}
	
	public boolean isOnline() {
		return StatusFlag.ONLINE == status;
	}

	public void setOnline() {
		status = StatusFlag.ONLINE;
	}
	
	public void setOffline() {
		status = StatusFlag.OFFLINE;
	}

	public int getLowRequests() {
		return lowRequests;
	}

	public void increaseRequests(String effort) {
		if(effort.equals("LOW")) {
			this.lowRequests++;
		}else if(effort.equals("MIDDLE")){
			this.middleRequests++;
		}else if (effort.equals("HIGH")) {
			this.highRequests++;
		}
	}

	public int getMiddleRequests() {
		return middleRequests;
	}

	public int getHighRequests() {
		return highRequests;
	}

	@Override
	public String toString() {
		return name + " (" + status.toString().toLowerCase() + "): LOW " 
				+ lowRequests + ", MIDDLE " + middleRequests + ", HIGH " + highRequests;
	}

	public String getPassword() {
		return pw;
	}

	public boolean loginIfPasswordCorrect(String password) {
		if(password.equals(pw)) {
			setOnline();
			return true;
		}
		return false;
	}
}
