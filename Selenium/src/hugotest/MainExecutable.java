package hugotest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainExecutable extends Login{

	public static void main(String[] args) {
		String portMain = null;
		String sqlUserMain = null;
		String sqlPasswordMain = null;
		
		try {
			portMain = ConfigConnection.getPort();
			sqlUserMain = ConfigConnection.getSQLUser();
			sqlPasswordMain = ConfigConnection.getSQLPassword();
		} catch (IOException ioe) {
			logger.error("Error reading the the Config.properties file! Errormessage: " + ioe.getMessage());
		}
		
		try {
			String url = "jdbc:mysql://localhost:" + portMain + "/seleniumhugotest";
			String username = sqlUserMain;
			String password = sqlPasswordMain;
			String ppQuery = "SELECT ProcessProgress FROM vehicles WHERE ProcessProgress = 'Open'";
			
			Connection con = DriverManager.getConnection(url, username, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(ppQuery);
			
			while(rs.next()) {
				String Progress = rs.getString("ProcessProgress");
				while(Progress.equals("Open")) {
					beforeClass();
					completeLogin();
					afterClass();
					Progress = "Completed";
				}
			}
		} catch (Exception e) {
			logger.error("The databese couldn't be accessed!\nErrormessage: " + e.getMessage());
		}
		logger.info("all vehicles have been added.");
	}
}