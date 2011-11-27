package propertyReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import management.UserInfo;

public class UserReader {
	private Map<String, UserInfo> user = new HashMap<String, UserInfo>();

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
	
	public Map<String, UserInfo> getAllUsers()  {
		return user;
	}
	
	
}
