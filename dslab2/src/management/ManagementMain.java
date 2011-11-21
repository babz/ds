package management;

import java.io.File;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;

/**
 * handles authentication and billing etc for the clients (companies)
 * @author babz
 *
 */
public class ManagementMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// params = String bindingName, String schedulerHost, int schedulerTCPPort, int preparationCosts, String taskDir
		int noOfParams = 5;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}

		String bindingName = args[0];
		String schedulerHost = args[1];
		int schedulerTCPPort = Integer.parseInt(args[2]);
		int preparationCosts = Integer.parseInt(args[3]);
		File taskDir = new File(args[4]);

		RegistryReader registry = null;
		try {
			registry = new RegistryReader();
		} catch (IOException e1) {
			System.out.println("Something wrong with registry.properties");
			e1.printStackTrace();
		}
		//creates and exports a registry instance on localhost
		try {
			LocateRegistry.createRegistry(registry.getRegistryPort());
		} catch (IOException e) {
			System.out.println("registry.properties is empty or not found");
			e.printStackTrace();
		}

		TaskManager taskManager = null;
		ClientConnectionManager connection = null;
		taskManager = new TaskManager(taskDir);
		try {
			connection = new ClientConnectionManager(schedulerHost, schedulerTCPPort, taskManager);
			new Thread(connection).start();
		} catch (IOException exc) {
			System.out.println("connection from management failed");
		}

	}

}
