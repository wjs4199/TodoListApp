package com.todo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnect {
	private static Connection conn = null;
	//private static DbConnect instance = new DbConnect();
	//private DbConnect() {}
	
	//public static DbConnect getInstance() {
	//	return instance;
	//}
	
	public static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:" + "todolist.db");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
}
