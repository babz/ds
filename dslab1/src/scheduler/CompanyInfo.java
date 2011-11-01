package scheduler;

/**
 * Company with all attributes
 * @author babz
 *
 */
public class CompanyInfo {

	private String name, pw;
	private int lowRequests, middleRequests, highRequests;
	private StatusFlag status;
	
	public CompanyInfo(String name, String pw) {
		super();
		this.name = name;
		this.pw = pw;
	}
	
	private enum StatusFlag {ONLINE, OFFLINE}
	
	public boolean isOnline() {
		return StatusFlag.ONLINE == status;
	}

	public void setOnline() {
		status = StatusFlag.ONLINE;
	}

	public int getLowRequests() {
		return lowRequests;
	}

	public void setLowRequests(int lowRequests) {
		this.lowRequests = lowRequests;
	}

	public int getMiddleRequests() {
		return middleRequests;
	}

	public void setMiddleRequests(int middleRequests) {
		this.middleRequests = middleRequests;
	}

	public int getHighRequests() {
		return highRequests;
	}

	public void setHighRequests(int highRequests) {
		this.highRequests = highRequests;
	}

	@Override
	public String toString() {
		return name + "(" + status + "): LOW " + lowRequests + ", MIDDLE " + middleRequests
				+ ", HIGH " + highRequests;
	}
}
