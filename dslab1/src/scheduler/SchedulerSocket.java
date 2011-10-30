package scheduler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class SchedulerSocket {
	private static Logger log = Logger.getLogger("class server socket");

	private ServerSocket serverSocket;
	private DatagramSocket datagramSocket;
	private Map<String, String> companiesNamesPW;
	private Socket clientSocket = null;
	private PrintWriter out;
	private BufferedReader in;

	public SchedulerSocket(int tcpPort, int udpPort, int min, int max, int timeout, int checkPeriod) throws IOException {
		serverSocket = new ServerSocket(tcpPort);
		datagramSocket = new DatagramSocket(udpPort);

		InputStream inputStream = ClassLoader.getSystemResourceAsStream("company.properties");
		if (inputStream != null) {
			Properties companies = new Properties();
			companies.load(inputStream);
			companiesNamesPW = new HashMap<String, String>() ; 
			for (String companyName : companies.stringPropertyNames()) { // get all company names
				String password = companies.getProperty(companyName); // get password for user with company name
				companiesNamesPW.put(companyName, password);
			}
		} else {
			//TODO
			// company.properties could not be found
		} 
		
		//TODO spawn new thread for each in loop
		clientSocket = serverSocket.accept();
//		datagramSocket
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public void readStream() {
		log.info("read stream");

		String input = null;
		try {
			//TODO check auf blocking - warum wird nur 1 line gelesen?
			while ((input = in.readLine()) != null) {
				System.out.println(input);
				if (input.equals("!exit")) {
					destroy();
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("Error!");
		}
	}

	public void destroy() throws IOException {
		log.info("scheduler: close all");
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
}
