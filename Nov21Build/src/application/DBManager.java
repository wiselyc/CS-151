package application;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DBManager {
	private String directory;
	private ObjectMapper mapper;
	private static DBManager instance = new DBManager();
	
	private DBManager() {
		// directory to store data file
		String dataDir = System.getProperty("user.dir") + "/src/application/data";
		directory = dataDir;
		File dirFile = new File(dataDir);
		// make directory if it does not exist
		if (!dirFile.exists()) dirFile.mkdir();
		
		mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static DBManager getInstance() {
		return instance;
	}
	
	public User getUser() {
		try {
			// data file that stores user data
			String dataStr = directory + "/UserData.json";
			File dataFile = new File(dataStr);
			User user = null;
			
			// if the data file exists, retrieve it
			// else create a new one
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
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
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
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
}
