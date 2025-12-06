package database;

import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnector {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String HOST = "localhost:3306";
	private static final String DATABASE = "joymarket";
	private static String URL = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	private Connection connection;
	private static DatabaseConnector instance;
	
	private DatabaseConnector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static DatabaseConnector getInstance() {
		if(instance == null) {
			synchronized (DatabaseConnector.class) {
				if(instance == null) {
					instance = new DatabaseConnector();
				}
			}
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
}
