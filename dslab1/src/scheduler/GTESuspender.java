package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * checks energy efficience of engines and manages suspension/activation; checks
 * engines for timeout
 * 
 * @author babz
 * 
 */
public class GTESuspender implements Runnable {
	private static Logger log = Logger.getLogger("class GTE suspender");

	private DatagramSocket datagramSocket;
	private Hashtable<EngineIdentifier, GTEInfo> engines;
	private int min, max, timeout, checkPeriod;
	
	private enum EngineCommands { SUSPEND, ACTIVATE }

	public GTESuspender(DatagramSocket datagramSocket,
			Hashtable<EngineIdentifier, GTEInfo> engines, int min, int max,
			int timeout, int checkPeriod) {
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
			for (Entry<EngineIdentifier, GTEInfo> engine : engines.entrySet()) {
				// if active add to activeEngines
				if (engine.getValue().isActive()) {
					activeEngines.put(engine.getKey(), engine.getValue());
					// if active and timeout, set offline and remove from activeEngines
					if ((System.currentTimeMillis() - engine.getValue()
							.getTime()) >= timeout) {
						engine.getValue().setOffline();
						activeEngines.remove(engine.getKey());
					}
					// if no load add to zeroLoadEngines
					if (engine.getValue().getLoad() == 0) {
						zeroLoadEngines.put(engine.getKey(), engine.getValue());
					}
				}
			}
			// check suspend/activate
			try {
				this.checkEnergyEfficience(activeEngines, zeroLoadEngines);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				// confirm alive statements every checkPeriod, timeout otherwise
				Thread.sleep(checkPeriod);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the suspend or offline flag for engines (activate/suspend commands)
	 * 
	 * @param activeEngines
	 *            all engines with flag set to active
	 * @param zeroLoadEngines
	 *            engines with 0% load
	 * @throws IOException 
	 */
	private void checkEnergyEfficience(
			Hashtable<EngineIdentifier, GTEInfo> activeEngines,
			Hashtable<EngineIdentifier, GTEInfo> zeroLoadEngines) throws IOException {
		// if activeEngines <= min, do not suspend
		if (activeEngines.size() > min) {
			if (zeroLoadEngines.size() > 1) {
				EngineIdentifier energyEater = this
						.getEnergyEater(activeEngines);
				//set suspended-flag
				activeEngines.get(energyEater).suspendGTE();
				//actually suspend via udp
				talkToEngine(energyEater, EngineCommands.SUSPEND);
			}
		}
		// if activeEngines >= max, do not activate
		if (activeEngines.size() < max) {
			if ((activeEngines.size() < min) || checkEngineOverload(activeEngines)) {
				EngineIdentifier energySaver = this.getEnergySaver();
				if (energySaver != null) {
					// set offline-flag
					engines.get(energySaver).setOffline(); 
					//actually activate via udp
					talkToEngine(energySaver, EngineCommands.ACTIVATE);
				}
			}
		}
	}

	/**
	 * @param activeEngines
	 * @return engine that consumes the most energy
	 */
	private EngineIdentifier getEnergyEater(
			Hashtable<EngineIdentifier, GTEInfo> activeEngines) {
		EngineIdentifier energyEater = null;
		int maxConsumption = 0;
		for (Entry<EngineIdentifier, GTEInfo> engine : activeEngines.entrySet()) {
			if (engine.getValue().getMaxConsumption() > maxConsumption) {
				energyEater = engine.getKey();
				maxConsumption = engine.getValue().getMaxConsumption();
			}
		}
		return energyEater;
	}

	/**
	 * @param activeEngines
	 * @return true if all active GTEs have at least 66% load
	 */
	private boolean checkEngineOverload(
			Hashtable<EngineIdentifier, GTEInfo> activeEngines) {
		boolean engineOverload = true;
		for (Entry<EngineIdentifier, GTEInfo> engine : engines.entrySet()) {
			if (engine.getValue().getLoad() < 66) {
				engineOverload = false;
				break;
			}
		}
		return engineOverload;
	}

	/**
	 * @return engine that consumes the fewest energy at start-up
	 */
	private EngineIdentifier getEnergySaver() {
		EngineIdentifier energySaver = null;
		int minConsumption = 1000;
		for (Entry<EngineIdentifier, GTEInfo> engine : engines.entrySet()) {
			// find all suspended engines and activate the one with the fewest
			// energy consumption
			if (engine.getValue().isSuspended()
					&& (engine.getValue().getMinConsumption() < minConsumption)) {
				energySaver = engine.getKey();
				minConsumption = engine.getValue().getMinConsumption();
			}
		}
		return energySaver;
	}

	private void talkToEngine(EngineIdentifier engine, EngineCommands message)
			throws IOException {
		String messageString = message.toString().toLowerCase();
		int packetLength = 100;
		byte[] msg = new byte[packetLength];
		msg = messageString.getBytes();
		datagramSocket.send(new DatagramPacket(msg, msg.length, engines.get(
				engine).getIpAddress(), engines.get(engine).getUdpPort()));
	}
}