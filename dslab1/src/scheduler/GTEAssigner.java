package scheduler;

import genericTaskEngine.EngineIdentifier;

import java.util.Hashtable;

/**
 * handles client requests for engines
 * @author babz
 *
 */
public class GTEAssigner {

	private Hashtable<EngineIdentifier, GTEInfo> engines;

	public GTEAssigner(Hashtable<EngineIdentifier, GTEInfo> allEngines) {
		engines = allEngines;
	}

	public EngineIdentifier getEngine(String taskEffort) {
		
	}

}
