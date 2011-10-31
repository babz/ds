package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.sun.xml.internal.ws.api.pipe.Engine;

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
		Hashtable<EngineIdentifier, GTEInfo> activeEngines = new Hashtable<EngineIdentifier, GTEInfo>();
		Hashtable<EngineIdentifier, GTEInfo> zeroLoadEngines = new Hashtable<EngineIdentifier, GTEInfo>();
		while (true) {
			for(Entry<EngineIdentifier, GTEInfo> engine: engines.entrySet()) {
				//if active add to activeEngines
				if(engine.getValue().isActive()) {
					activeEngines.put(engine.getKey(), engine.getValue());
					//if active and timeout, set offline and remove from activeEngines
					if((System.currentTimeMillis() - engine.getValue().getTime()) >= timeout) {
						engine.getValue().setOffline();
						activeEngines.remove(engine.getKey());
					}
					//if no load add to zeroLoadEngines
					if(engine.getValue().getLoad() == 0) {
						zeroLoadEngines.put(engine.getKey(), engine.getValue());
					}
				}
			}
			//TODO check suspend/activate
			this.checkEnergyEfficience(activeEngines, zeroLoadEngines);
			try {
				//confirm alive statements every checkPeriod, timeout otherwise
				Thread.sleep(checkPeriod);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void checkEnergyEfficience(Hashtable<EngineIdentifier, GTEInfo> activeEngines, Hashtable<EngineIdentifier, GTEInfo> zeroLoadEngines) {
		//if activeEngines <= min, do not suspend
		if(activeEngines.size() > min) {
			if(zeroLoadEngines.size() > 1) {
				//TODO suspend energy eater -> datagram
				EngineIdentifier energyEater = this.getEnergyEater(activeEngines);
				activeEngines.get(energyEater).suspendGTE(); //set suspended-flag
			}
		}
		//if activeEngines >= max, do not activate
		if(activeEngines.size() < max) {
			if(zeroLoadEngines.size() <= 1) {

			}
			//TODO activate engine
		}
		for(Entry<EngineIdentifier, GTEInfo> activeEngine: activeEngines.entrySet()) {

		}
	}

	private EngineIdentifier getEnergyEater(Hashtable<EngineIdentifier, GTEInfo> activeEngines) {
		EngineIdentifier energyEater = null;
		int maxConsumption = 0;
		for(Entry<EngineIdentifier, GTEInfo> engine: activeEngines.entrySet()) {
			if(engine.getValue().getMaxConsumption() > maxConsumption) {
				energyEater = engine.getKey();
				maxConsumption = engine.getValue().getMaxConsumption();
			}
		}
		return energyEater;
	}
	
	private EngineIdentifier getEnergySaver() {
		return null;
	}
}