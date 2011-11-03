package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.util.Hashtable;
import java.util.Map.Entry;

/**
 * handles client requests for engines
 * @author babz
 *
 */
public class GTEAssigner {

	private Hashtable<EngineIdentifier, GTEInfo> engines;
	private Hashtable<EngineIdentifier, GTEInfo> consideredEngines;

	public GTEAssigner(Hashtable<EngineIdentifier, GTEInfo> allEngines) {
		engines = allEngines;
	}

	/**
	 * chooses an engine for the required task
	 * @param taskEffort
	 * @return null if not enough capacity left
	 */
	public EngineIdentifier getEngine(String taskEffort) {
		int effort = getTaskEffortInInt(taskEffort);
		EngineIdentifier assignedEngine = null;
		consideredEngines = new Hashtable<EngineIdentifier, GTEInfo>();
		for (Entry<EngineIdentifier, GTEInfo> tmpEngine: engines.entrySet()) {
			//check if enough capacity
			if((100 - tmpEngine.getValue().getLoad()) >= effort) {
				consideredEngines.put(tmpEngine.getKey(), tmpEngine.getValue());
			}
		}
		
		//check welche engine energietechnisch am besten geeignet - lineare interpolation
		for (Entry<EngineIdentifier, GTEInfo> tmpEngine: consideredEngines.entrySet()) {
			//TODO
		}
		return assignedEngine;
	}

	private int getTaskEffortInInt(String taskEffort) {
		if(taskEffort.equals("LOW")) {
			return 33;
		} else if (taskEffort.equals("MIDDLE")) {
			return 66;
		} else if (taskEffort.equals("HIGH")) {
			return 100;
		} else {
			return (Integer) null;
		}
	}

}
