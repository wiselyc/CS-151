package application;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DBManager {
	private String directory;
	private ObjectMapper mapper;
	
	public DBManager(String dataDir) {
		directory = dataDir;
		mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	public User getUser() {
		try {
			String dataStr = directory + "/UserData.json";
			File dataFile = new File(dataStr);
			User user = null;
			
			if (dataFile.exists()) {
				user = mapper.readValue(dataFile, User.class);
				System.out.println("retrieved user data");
			} else {
				dataFile.createNewFile();
				user = User.createDefaultUser();
				mapper.writeValue(dataFile, user);
				System.out.println("created new user");
			}
			
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public boolean updateUserData(User user) {
		if (user == null) return false;
		
		try {
			String dataStr = directory + "/UserData.json";
			File dataFile = new File(dataStr);
			mapper.writeValue(dataFile, user);
			System.out.println("updated user data");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		try {
			// modify this path as necessary
			String dir = System.getProperty("user.dir") + "/src/application/data";
			File dirFile = new File(dir);
			if (!dirFile.exists()) {
				if (dirFile.mkdir()) {
					System.out.println("created directory");
				}
			}
			
			DBManager manager = new DBManager(dir);
			User user = manager.getUser();
			user.getBudget().updateBudget(100);
			manager.updateUserData(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
