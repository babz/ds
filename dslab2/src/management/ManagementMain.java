package management;

import java.io.File;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import propertyReader.RegistryReader;
import remote.ILogin;
import remote.LoginImpl;


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
		File taskDir = new File(args[4]); //optional

		try {
			RegistryReader registryLocation = new RegistryReader();
			//Creates and exports a Registry instance on the local host that accepts requests on the specified port.
			Registry registry = LocateRegistry.createRegistry(registryLocation.getRegistryPort());
			ILogin login = new LoginImpl(preparationCosts);
			UnicastRemoteObject.exportObject(login, 0);
			//register name in registry
			registry.bind(bindingName, login);
		} catch (IOException e1) {
			System.out.println("Something wrong with registry.properties");
			e1.printStackTrace();
		} catch (AlreadyBoundException e) {
			System.err.println("mgmt registry already bound");
			e.printStackTrace();
		}

		MgmtTaskManager taskManager = null;
		ClientConnectionManager connection = null;
		//TODO remove from main
		taskManager = new MgmtTaskManager();
		try {
			connection = new ClientConnectionManager(schedulerHost, schedulerTCPPort, taskManager);
			new Thread(connection).start();
		} catch (IOException exc) {
			System.out.println("connection from management failed");
		}
		
		MgmtInfoPoint commandReader;
		try {
			commandReader = new MgmtInfoPoint();
			commandReader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
