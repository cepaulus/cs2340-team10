package main.java.edu.gatech.cs2340.risk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class RiskDatabaseUtil {
	
	private static Logger log = Logger.getLogger(RiskDatabaseUtil.class);
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_NAME = "Risk_Database";
	private static final String DB_URL = "jdbc:mysql://localhost/";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "mypass";

	
	public static void buildDatabase()  {
		try {
			// try to create a new database 
			createNewDatabase();
			log.debug("Successfully created database risk_database");
		}
		catch (SQLException e) {
			// assume that database already exists - SHOULD NOT (TODO- deal with this)
			log.debug("Risk_database already exists");
		} catch (InstantiationException e) {
			// TODO add log statements
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO add log statements
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// add log statements
			e.printStackTrace();
		}
	}
	
	private static void createNewDatabase() 
			throws InstantiationException, IllegalAccessException, 
			ClassNotFoundException, SQLException {

	      Class.forName(JDBC_DRIVER);
	      log.debug("Connecting to database...");
	      Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	      log.debug("Creating database...");
	      Statement stmt = conn.createStatement();
	      
	      String sql = "CREATE DATABASE " + DB_NAME +";";
	      stmt.executeUpdate(sql);
	      log.debug("Database created successfully...");
	}
	
	public static void checkForExistingTable(String tableName) {
		try {
			createEmptyTableInDatabase(tableName);
			log.debug("Successfully created table " + tableName);
		}
		catch (SQLException e) {
			// assume that table exists
			log.debug("Table " + tableName + " already exists");
		} catch (ClassNotFoundException e) {
			// TODO do something here
			e.printStackTrace();
		}
	}
	
	private static void createEmptyTableInDatabase(String tableName) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		s.execute("CREATE TABLE " + tableName + 
				"( id INTEGER not NULL, " +
				"name CHAR(30) NOT NULL, " +
				"PRIMARY KEY (id) )");
	}
	
	public static void checkForExistingColumn(String tableName, String columnName, String type) {
		try {
			addColumnToTable(tableName, columnName, type);
		}
		catch (SQLException e) {
			// assume column existing
		} catch (ClassNotFoundException e) {
			// TODO do something here
			e.printStackTrace();
		}
	}
	
	private static void addColumnToTable(String tableName, String columnName, String type) 
			throws ClassNotFoundException, SQLException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "ALTER TABLE "+ tableName +" ADD "+ columnName + " " + type + ";";
		s.execute(sql);
	}
	
	public static void deleteDatabaseIfExists() {
		try {
			deleteDatabase();
			log.debug("Successfully deleted database");
		}
		catch (SQLException e) {
			// assume database does not exist
			e.printStackTrace();
			log.error("Database does not exist");
		} catch (ClassNotFoundException e) {
			// TODO do something here
			e.printStackTrace();
		}
	}
	
	private static void deleteDatabase() throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
		Class.forName(JDBC_DRIVER);
		
		Statement s = conn.createStatement();
		String sql = "DROP DATABASE " + DB_NAME +";";
		s.execute(sql);
	}

}
