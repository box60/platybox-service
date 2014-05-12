package com.platybox.utils.database;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.platybox.utils.database.ScriptRunner;

/**
 * Servlet implementation class InitializeDatabase
 */
public class InitializeDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SQL_CREATE_FILEPATH = "/WEB-INF/platybox.sql";
	
	private Logger logger = Logger.getLogger(InitializeDatabase.class);

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init (ServletConfig config) throws ServletException {

		// create database if does not exist already
		try {
			
			Connection conn;		
			conn = DatabaseUtils.openDb();
			
			ScriptRunner runner = new ScriptRunner(conn, false, false);
				
			try {
				InputStream in = config.getServletContext().getResourceAsStream(SQL_CREATE_FILEPATH);
				runner.runScript(new InputStreamReader(in));

			} catch (IOException e) {
				logger.error(e.getMessage());
			}

			DatabaseUtils.closeDb(conn);				
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		} 
		
	}
	
	//There's two files with the following information. DatabaseUtils and InitializeDatabase, although it's repeated i rather
	//have this here compiled than having it on clear text on a context.xml file.
	/*
	private static synchronized Connection openDb () throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Connection conn = null;

		String url = "jdbc:mysql://localhost:3306/";
		String dbName = "platybox";
		String driver = "com.mysql.jdbc.Driver";
		String dbUsername = "platybox"; 
		String dbPassword = "f2A5HmtqXRZraG7Q";
		Class.forName(driver).newInstance();
		conn = DriverManager.getConnection(url+dbName,dbUsername,dbPassword);

		return conn;
	}
	
	
	
	private static synchronized void closeDb (Connection conn) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		conn.close();
	}
	*/
	

}
