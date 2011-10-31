package scheduler;

public class GTEInfo {

	private long currTime;
	private int tcpPort, minConsumption, maxConsumption, load;
	private boolean isSuspended;
	
	public GTEInfo(int tcpPort, int minConsumption, int maxConsumption) {
		updateTime();
		this.tcpPort = tcpPort;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
		isSuspended = false;
	}
	
	public long getTime() {
		return currTime;
	}
	
	public void updateTime() {
		currTime = System.currentTimeMillis();
	}
	
	public boolean isSuspended() {
		return isSuspended;
	}
	
	public boolean suspendGTE() {
		return isSuspended = true;
	}
	
	public int getLoad() {
		return load;
	}
	
	public int getTcpPort() {
		return tcpPort;
	}
	
	public int getMinConsumption() {
		return minConsumption;
	}
	
	public int getMaxConsumption() {
		return maxConsumption;
	}
}
