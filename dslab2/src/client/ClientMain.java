package client;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import management.MgmtInfoPoint;

import propertyReader.RegistryReader;

import remote.ILogin;
import remote.LoginImpl;


public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		params = String schedulerHost, int schedulerTCPPort, String taskDir
		int noOfParams = 2;
		if(args.length != noOfParams) {
			System.out.println("Error: Too few arguments!");
			return;
		}
		
		String mgmtComponent = args[0];
		File taskDir = new File(args[1]);
		
		ILogin login = null;
		try {
			RegistryReader registryDetails = new RegistryReader(); 
			Registry registry = LocateRegistry.getRegistry(registryDetails.getRegistryHost(), registryDetails.getRegistryPort());
			login = (ILogin) registry.lookup(mgmtComponent);
			//TODO comp.executeTask() -> aufruf der engineAnforderung u der prepare
		} catch (NotBoundException e) {
			System.err.println("ClientMain Exception");
			e.printStackTrace();
		} catch (IOException ioExc) {
			//TODO
		}
		
//		NOW IN MANAGEMENT-MAIN !!
//		TaskManager taskManager = null;
//		ClientConnectionManager connection = null;
//		taskManager = new TaskManager(taskDir);
//		try {
//			connection = new ClientConnectionManager(schedulerHost, schedulerTCPPort, taskManager);
//			new Thread(connection).start();
//		} catch (IOException exc) {
//			System.out.println("connection from client failed");
//		}

		ClientInfoPoint commandReader;
		try {
			commandReader = new ClientInfoPoint(login);
			commandReader.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
