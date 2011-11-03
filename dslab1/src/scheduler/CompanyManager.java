package scheduler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import sun.security.jca.GetInstance.Instance;

/**
 * reads company properties-file and provides methods for companies
 * @author babz
 *
 */
public class CompanyManager {
	private static CompanyManager instance;
	private Map<String, CompanyInfo> companies; //map with names and passwords

	private CompanyManager() throws IOException {
		readCompanies();
	}
	
	public static synchronized CompanyManager getInstance() throws IOException {
		if(instance == null) {
			instance = new CompanyManager();
		}
		return instance;
	}

	private void readCompanies() throws IOException {
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("company.properties");
		if (inputStream != null) {
			Properties companyProps = new Properties();
			companyProps.load(inputStream);
			companies = new HashMap<String, CompanyInfo>() ; 
			for (String companyName : companyProps.stringPropertyNames()) { // get all company names
				String password = companyProps.getProperty(companyName); // get password for user with company name
				companies.put(companyName, new CompanyInfo(companyName, password));
			}
		} else {
			//TODO company.properties could not be found
			System.err.println("read companies failed.");
		} 
	}
	
	public boolean checkLogin(String name, String pw) {
		if(!companies.containsKey(name)) {
			return false;
		} 
		return companies.get(name).loginIfPasswordCorrect(pw);
	}
	
	public CompanyInfo getCompanyInfo(String name) {
		return companies.get(name);
	}
	
	/**
	 * @param username 
	 * @return true if logout successful
	 */
	public boolean logout(String username) {
		CompanyInfo company = companies.get(username);
		if(company.isOnline()) {
			company.setOffline();
			return true;
		}
		return false;
	}
	
	//TODO check online status and amount of requested tasks per category
	
	public String toString() {
		String companyList = "";
		for(Entry<String, CompanyInfo> company: companies.entrySet()) {
			companyList += company.getValue() + "\n";
		}
		return companyList;
	}
	
}
