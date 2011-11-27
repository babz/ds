package client;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import propertyReader.RegistryReader;

import remote.LoginImpl;


public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//TODO
		
//		params = String schedulerHost, int schedulerTCPPort, String taskDir
		int noOfParams = 2;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}
		
		String mgmtComponent = args[0];
		File taskDir = new File(args[1]);
		
		try {
			RegistryReader registryDetails = new RegistryReader(); 
			Registry registry = LocateRegistry.getRegistry(registryDetails.getRegistryHost(), registryDetails.getRegistryPort());
			LoginImpl login = (LoginImpl) registry.lookup(mgmtComponent);
			//TODO comp.executeTask() -> aufruf der engineAnforderung u der prepare
		} catch (NotBoundException e) {
			System.err.println("ClientMain Exception");
			e.printStackTrace();
		} catch (IOException ioExc) {
			//TODO
		}
		
//		TaskManager taskManager = null;
//		ClientConnectionManager connection = null;
//		taskManager = new TaskManager(taskDir);
//		try {
//			connection = new ClientConnectionManager(schedulerHost, schedulerTCPPort, taskManager);
//			new Thread(connection).start();
//		} catch (IOException exc) {
//			System.out.println("connection from client failed");
//		}

	}

}
