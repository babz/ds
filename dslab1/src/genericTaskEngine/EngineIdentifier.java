package genericTaskEngine;

import java.net.InetAddress;

public class EngineIdentifier {
	private InetAddress address;
	private int port;
	
	public EngineIdentifier(InetAddress addr, int port) {
		address = addr;
		this.port = port;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	
	public int getPort() {
		return port;
	}
}
