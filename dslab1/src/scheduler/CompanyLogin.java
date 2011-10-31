package scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CompanyLogin {

	private Map<String, String> companiesNamesPW; //map with names and passwords


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
			//TODO
			// company.properties could not be found
		} 
	}
	
	public String toString() {
		String companyList = "";
		return companyList;
	}
	
}
