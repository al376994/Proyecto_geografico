package Geografico.model;

import java.sql.*;


public class DataBaseConnector {
	// Propiedades
	private static Connection conn = null;
	private String HostDB;
	private String NameDB;
	private String UserDB;
	private String PasswordDB;
	private String uri;

	// Constructor
	private DataBaseConnector(){
		String HostDB = "ec2-54-217-15-9.eu-west-1.compute.amazonaws.com";
		String NameDB = "ddaktlv8nrju80";
		String UserDB = "ioqafzsjikxaoz";
		String PasswordDB = "8e57115a9b6c9eca7a3f29e32883adbf19da00f62125ceaf21e5b1c282dc97a2";
		try{
			String url = "jdbc:postgresql://" + HostDB + ":5432/" + NameDB;
			conn = DriverManager.getConnection(url, UserDB, PasswordDB);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	} // Fin constructor

	// Métodos
	public static Connection getConnection(){
		if (conn == null){
			new DataBaseConnector();
		}
		return conn;
	} // Fin getConnection
}
