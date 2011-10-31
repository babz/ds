package scheduler;

import java.net.InetAddress;

public class GTEInfo {

	private long currTime;
	private InetAddress ip;
	private int tcpPort, udp, minConsumption, maxConsumption, load;
	private StatusFlag status;
	
	public GTEInfo(InetAddress ip, int udpPort, int tcpPort, int minConsumption, int maxConsumption) {
		updateTime();
		this.ip = ip;
		this.udp = udpPort;
		this.tcpPort = tcpPort;
		this.minConsumption = minConsumption;
		this.maxConsumption = maxConsumption;
		status = StatusFlag.online;
	}
	
	private enum StatusFlag {suspended, online, offline}
	
	public long getTime() {
		return currTime;
	}
	
	public void updateEngine(int udpPort) {
		updateTime();
		udp = udpPort;
	}
	
	public void updateTime() {
		currTime = System.currentTimeMillis();
	}
	
	public boolean isSuspended() {
		return StatusFlag.suspended == status; 
	}
	
	public void suspendGTE() {
		status = StatusFlag.suspended;
	}
	
	public void setOffline() {
		status = StatusFlag.offline;
	}
	
	public void setOnline() {
		status = StatusFlag.online;
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

	@Override
	public String toString() {
		return "IP:" + ip + ", TCP:" + tcpPort + ", UDP: " + udp + ", " + status 
				+ ", Energy Signature: min " + minConsumption + "W, max "
				+ maxConsumption + "W, " + ", Load: " + load + "%";
	}
}
