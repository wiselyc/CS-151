package model;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DbConnection {
	
	// The directory to store user data.
	private String directory;
	// The ObjectMapper that maps data to the database or to a User object.
	private ObjectMapper mapper;
	// The single instance of DbConnection.
	private static DbConnection instance = new DbConnection();
	
	/**
	 * Construct a new DbConnection.
	 */
	private DbConnection() {
		// directory to store data file
		String dataDir = "src/data";
		directory = dataDir;
		File dirFile = new File(dataDir);
		
		// make directory if it does not exist
		if (!dirFile.exists()) dirFile.mkdir();
		
		// enable features and configure the ObjectMapper
		mapper = new ObjectMapper()
				.enable(SerializationFeature.INDENT_OUTPUT)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	/**
	 * Get the instance of the DbConnection.
	 * @return the instance of the DbConnection
	 */
	public static DbConnection getInstance() {
		return instance;
	}
	
	/**
	 * Get the User object.
	 * @return the User
	 */
	public User getUser() {
		try {
			// data file that stores user data
			String dataStr = directory + "/UserData.json";
			File dataFile = new File(dataStr);
			User user = null;
			
			// if the data file exists, retrieve it, otherwise create a new one
			if (dataFile.exists()) {
				user = mapper.readValue(dataFile, User.class);
			} else {
				dataFile.createNewFile();
				user = User.createDefaultUser();
				mapper.writeValue(dataFile, user);
			}
			
			return user;
			
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Update user data to the database.
	 * @param user the user that contains data
	 * @return true if data is successfully updated, otherwise false
	 */
	public boolean updateUserData(User user) {
		if (user == null) return false;
		
		try {
			// data file that stores user data
			String dataStr = directory + "/UserData.json";
			File dataFile = new File(dataStr);
			
			// update values
			mapper.writeValue(dataFile, user);
			
			return true;
			
		} catch (JsonMappingException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
}
