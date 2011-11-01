package scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;


public class CompanyManager {

	private Map<String, String> companiesNamesPW; //map with names and passwords

	public CompanyManager() throws IOException {
		readCompanies();
	}

	private void readCompanies() throws IOException {
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
			//TODO company.properties could not be found
			System.err.println("read companies failed.");
		} 
	}
	
	public boolean checkLogin(String name, String pw) {
		if(!companiesNamesPW.containsKey(name)) {
			return false;
		}
		return pw.equals(companiesNamesPW.get(name));
	}
	
	//TODO check online status and amount of requested tasks per category
	
	public String toString() {
		String companyList = "";
//		for(Entry<String, CompanyInfo> company: companies.entrySet()) {
//			companyList += company.getValue() + "\n";
//		}
		return companyList;
	}
	
}
