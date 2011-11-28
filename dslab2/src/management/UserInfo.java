package management;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class UserInfo {
	
	private enum StatusFlag {ONLINE, OFFLINE}
	
	private boolean isAdmin_;
	private String name_, pw_;
	private int credits_;
	private int lowRequests = 0;
	private int middleRequests = 0;
	private int highRequests = 0;
	private StatusFlag status;
	
	
	public UserInfo(String name, String pw) {
		name_ = name;
		pw_ = pw;
		status = StatusFlag.OFFLINE;
	}

	public void setAdmin(String admin) {
		if(admin.equals("false")) {
			isAdmin_ = false;
		} else {
			isAdmin_ = true;
		}
	}
	
	public void setCredits(int credits) {
		credits_ = credits;
	}

	public boolean isAdmin() {
		return isAdmin_;
	}

	public String getName() {
		return name_;
	}

	public String getPw() {
		return pw_;
	}

	public int getCredits() {
		return credits_;
	}
	
	public int increaseCredit(int c) {
		credits_ += c;
		return credits_;
	}
	
	public int decreaseCredit(int c) {
		credits_ -= c;
		return credits_;
	}

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
	
	public boolean loginIfPasswordCorrect(String password) {
		if(password.equals(pw_)) {
			setOnline();
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name_ + " (" + status.toString().toLowerCase() + "): LOW " 
				+ lowRequests + ", MIDDLE " + middleRequests + ", HIGH " + highRequests;
	}
}