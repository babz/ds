package scheduler;

public class GTEInfo {

	private long currTime;
	private int tcpPort, minConsumption, maxConsumption;
	
	public GTEInfo(int tcpPort, int minConsumption, int maxConsumption) {
		updateTime();
		this.tcpPort = tcpPort;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
	}
	
	public void updateTime() {
		currTime = System.currentTimeMillis();
	}
}
