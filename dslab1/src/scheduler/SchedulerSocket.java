package scheduler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

public class SchedulerSocket {
	private static Logger log = Logger.getLogger("class server socket");

	private ServerSocket serverSocket;
	private InputStream inputStream;
	private Map<String, String> companiesNamesPW;
	private Socket clientSocket = null;
	private PrintWriter out;
	private BufferedReader in;

	public SchedulerSocket(int tcpPort, int udpPort, int min, int max, int timeout, int checkPeriod) throws IOException {
		serverSocket = new ServerSocket(tcpPort);

		inputStream = ClassLoader.getSystemResourceAsStream("company.properties");
		if (in != null) {
			Properties companies = new Properties();
			companies.load(in);
			companiesNamesPW = new HashMap<String, String>() ; 
			for (String companyName : companies.stringPropertyNames()) { // get all company names
				String password = companies.getProperty(companyName); // get password for user with company name
				companiesNamesPW.put(companyName, password);
			}
		} else {
			//TODO
			// company.properties could not be found
		} 

		clientSocket = serverSocket.accept();
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public void readStream() {
		log.info("read stream");
		
		
		
//		KnockKnockProtocol kkp = new KnockKnockProtocol();
//
//		outputLine = kkp.processInput(null);
//		out.println(outputLine);
//
		String input = null;
		try {
			while ((input = in.readLine()) != null) {
//			outputLine = kkp.processInput(inputLine);
				System.out.println(input);
//			if (outputLine.equals("Bye."))
//				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() throws IOException {
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}

	//	ServerSocket serverSocket = null;
	//	try {
	//		serverSocket = new ServerSocket(4444);
	//	} catch (IOException e) {
	//		System.err.println("Could not listen on port: 4444.");
	//		System.exit(1);
	//	}
	//
	//	Socket clientSocket = null;
	//	try {
	//		clientSocket = serverSocket.accept();
	//	} catch (IOException e) {
	//		System.err.println("Accept failed.");
	//		System.exit(1);
	//	}
	//
	//	PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
	//	BufferedReader in = new BufferedReader(
	//			new InputStreamReader(
	//					clientSocket.getInputStream()));
	//	String inputLine, outputLine;
	//	KnockKnockProtocol kkp = new KnockKnockProtocol();
	//
	//	outputLine = kkp.processInput(null);
	//	out.println(outputLine);
	//
	//	while ((inputLine = in.readLine()) != null) {
	//		outputLine = kkp.processInput(inputLine);
	//		out.println(outputLine);
	//		if (outputLine.equals("Bye."))
	//			break;
	//	}
	//	out.close();
	//	in.close();
	//	clientSocket.close();
	//	serverSocket.close();
	//}
}
