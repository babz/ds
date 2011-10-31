package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.logging.Logger;

public class GTESuspender implements Runnable {
	private static Logger log = Logger.getLogger("class GTE suspender");
	
	private DatagramSocket datagramSocket;
	private Hashtable<EngineIdentifier, GTEInfo> engines;
	private int min, max, timeout, checkPeriod;

	public GTESuspender(DatagramSocket datagramSocket, Hashtable<EngineIdentifier, GTEInfo> engines, int min, int max, int timeout, int checkPeriod) {
		this.datagramSocket = datagramSocket;
		this.engines = engines;
		this.min = min;
		this.max = max;
		this.timeout = timeout;
		this.checkPeriod = checkPeriod;
	}

	@Override
	public void run() {
		log.info("run");
		while (true) {
			for(Entry<EngineIdentifier, GTEInfo> engine: engines.entrySet()) {
				//compare timestamp: timeout & getTime
				
				//check suspend
			}
			//sleep(checkPeriod)
		}
	}

}
