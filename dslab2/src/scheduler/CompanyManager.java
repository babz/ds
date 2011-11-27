package scheduler;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import propertyReader.UserReader;

import management.UserInfo;

/**
 * reads company properties-file and provides methods for companies
 * @author babz
 *
 */
public class CompanyManager {
	private static CompanyManager instance;
	private Map<String, UserInfo> allUsers; //map with names and passwords

	private CompanyManager() throws IOException {
		allUsers = new UserReader().getAllUsers();
	}

	public static synchronized CompanyManager getInstance() throws IOException {
		if(instance == null) {
			instance = new CompanyManager();
		}
		return instance;
	}

	public boolean login(String name, String pw) {
		if(!allUsers.containsKey(name)) {
			return false;
		} 
		return allUsers.get(name).loginIfPasswordCorrect(pw);
	}

	public UserInfo getUserInfo(String name) {
		return allUsers.get(name);
	}

	/**
	 * @param username 
	 * @return true if logout successful
	 */
	public boolean logout(String username) {
		UserInfo user = allUsers.get(username);
		if(user.isOnline()) {
			user.setOffline();
			return true;
		}
		return false;
	}
	
	//TODO check online status and amount of requested tasks per category

	public String toString() {
		String userList = "";
		for(Entry<String, UserInfo> user: allUsers.entrySet()) {
			userList += user.getValue() + "\n";
		}
		return userList;
	}

}
