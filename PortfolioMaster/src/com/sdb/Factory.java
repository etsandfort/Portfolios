package com.sdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Factory.java
 * RAIK 184H
 * This class provides factory methods for use in accessing the database.
 * @author Libby Gentry, Jacob Melcher, Elliot Sandfort
 * @version 1.0
 */
public class Factory {
	
	static org.apache.log4j.Logger log = Logger.getLogger(Factory.class.getName());
	
	/**
	 * The getDriver() method loads a new JDBC driver
	 */
	public static void getDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			log.error("InstantiationException: " + e);
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			log.error("IllegalAccessException: " + e);
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFoundException: " + e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * The closeResources method checks if the ResultSet, PreparedStatement and Connection
	 * instances were closed. Everything that wasn't previously closed is closed.
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public static void closeResources(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * The closeResources method checks if the PreparedStatement and Connection
	 * instances were closed. Everything that wasn't previously closed is closed.
	 * @param ps
	 * @param conn
	 */
	public static void closeResources(PreparedStatement ps, Connection conn){
		try {
			
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	* Gets a connection with the database
	*/
	public static Connection getConnection(){
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			return conn;
		} catch (SQLException e) {
			log.error("SQLException: " + e);
			throw new RuntimeException(e);
		}
	}
}