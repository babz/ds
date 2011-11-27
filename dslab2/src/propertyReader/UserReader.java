package propertyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import client.UserInfo;

import wastebin.CompanyInfo;

public class UserReader {
	private HashMap<String, UserInfo> user = new HashMap<String, UserInfo>();

	public UserReader() throws IOException {
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("user.properties");
		if (inputStream != null) {
			Properties userProps = new Properties();
			userProps.load(inputStream);
			user = new HashMap<String, UserInfo>() ;
			String pw = "";
			String isAdmin = "";
			String credits = "";
			for (String userKey : userProps.stringPropertyNames()) { // get all user names
				if(userKey.contains("admin")) {
					isAdmin = userProps.getProperty(userKey);
					user.get(userKey).setAdmin(isAdmin);
				} else if (userKey.contains("credits")) {
					credits = userProps.getProperty(userKey);
					user.get(userKey).setCredits(credits);
				} else {
					pw = userProps.getProperty(userKey);
					user.put(userKey, new UserInfo(userKey, pw));
				}
			}
		} else {
			System.err.println("read user.properties failed.");
		}  
	}
	
	public boolean isAdmin(String username) {
		return user.get(username).isAdmin_();
	}
	
	public int getCredits(String companyname) {
		return user.get(companyname).getCredits();
	}
	
	public void increaseCredits(String companyname, int credit) {
		user.get(companyname).increaseCredit(credit);
	}
	
	public void decreaseCredits(String companyname, int credit) {
		user.get(companyname).decreaseCredit(credit);
	}
	
	/**
	 * checks if pw is correct
	 * @param pw typed in pw
	 * @return true if pw correct
	 */
	public boolean checkPw(String companyname, String pw) {
		return (user.get(companyname).getPw_()).equals(pw);
	}
}
