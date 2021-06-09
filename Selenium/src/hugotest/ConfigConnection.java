package hugotest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigConnection {
	
	public static String getPort() throws IOException {
		String port = null;
		
		Properties config = new Properties();
		
		FileInputStream file;
		
		String path = Login.directoryPath.getParent().toString() + "\\config.properties";
		
		file = new FileInputStream(path);
		
		config.load(file);
		
		file.close();
		
		port = config.getProperty("mysql.port");
		
		
		return port;
	}
	
	
	public static String getSQLUser() throws IOException {
		String userName = null;
		
		Properties config = new Properties();
		
		FileInputStream file;
		
		String path = Login.directoryPath.getParent().toString() + "\\config.properties";
		
		file = new FileInputStream(path);
		
		config.load(file);
		
		file.close();
		
		userName = config.getProperty("mysql.username");
		
		return userName;
	}
	
	
	public static String getSQLPassword() throws IOException {
		String userPassword = null;
		
		Properties config = new Properties();
		
		FileInputStream file;
		
		String path = Login.directoryPath.getParent().toString() + "\\config.properties";
		
		file = new FileInputStream(path);
		
		config.load(file);
		
		file.close();
		
		userPassword = config.getProperty("mysql.password");
		
		return userPassword;
	}
}
