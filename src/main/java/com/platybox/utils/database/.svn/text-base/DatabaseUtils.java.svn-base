package com.platybox.utils.database;

//TODO: rename and break db into classess, rather than having a massive file.

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import net.oauth.OAuthAccessor;




public class DatabaseUtils {

	private static String aesKey;


	public DatabaseUtils () {
		this.aesKey = "xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0";
	}
		
	/***********************    REGISTER USER SPECIFIC TABLE METHODS       ***************************/
	
	public static boolean userExists (String usernameOrEmail, String password) throws IOException{

		boolean result = false;

		try {
			Connection conn = null;
			conn = openDb();

			//Try username
			String strQuery="SELECT username FROM users " +
					"WHERE (username='"+usernameOrEmail+"' OR email='"+usernameOrEmail+"') AND password=AES_ENCRYPT('"+password+"','xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0')";			
			Statement st = null;
			st = conn.createStatement();
			ResultSet rs = null;
			rs = st.executeQuery(strQuery);
			
			if(rs.next() && rs.getString("username") != null){				
				result = true;					
			} else {
				result = false;
			}
			st = null;
			rs = null;
			closeDb(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	   
		public static boolean availableEmail (String email){
			
			boolean result = true;
			try {
				Connection conn = null;
				conn = openDb();

				String strQuery="SELECT email FROM users WHERE email ='"+email+"'";
				Statement st = null;
				st = conn.createStatement();
				ResultSet rs = null;
				rs = st.executeQuery(strQuery);

				if(rs.next()){
					result = false;
				}
				st = null;
				rs = null;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;

		}
		
		
		public static boolean availableUsername (String username){
	   
			boolean result = true;		   
			
			try {
				Connection conn = null;
				conn = openDb();
				
				String strQuery="SELECT username FROM users WHERE username ='"+username+"'";
				Statement st = null;
				st = conn.createStatement();
				
				ResultSet rs = null;
				rs = st.executeQuery(strQuery);

				if(rs.next()){
					result = false;
				}
				st = null;
				rs = null;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;

		}

		/***********************    USER DATA SPECIFIC TABLE METHODS       ***************************/
		

		public static String getUsername (int userId) {
		
			String result = null;
			
			try {
				Connection conn = null;
				conn = openDb();				
				String strQuery="SELECT username FROM users WHERE id ='"+userId+"'";
				Statement st = null;
				st = conn.createStatement();
				ResultSet rs = null;
				rs = st.executeQuery(strQuery);
		
				if(rs.next()){					
					result = rs.getString("username");					
				}
				st = null;
				rs = null;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
			
		}


		public static int getUserId (String usernameOrEmail) {
			
			int result = 0;
			
			try {
				Connection conn = null;
				conn = openDb();				
				String strQuery="SELECT id FROM users WHERE username ='"+usernameOrEmail+"' " +
						"OR email='"+usernameOrEmail+"'";
				Statement st = null;
				st = conn.createStatement();
				
				ResultSet rs = null;
				rs = st.executeQuery(strQuery);
				
				if(rs.next()){					
					result = rs.getInt("id");					
				}
				st = null;
				rs = null;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
			
		}
		
		public static String getUserType (String id) {
			
			String result = "";
			
			try {
				Connection conn = null;
				conn = openDb();
				String strQuery="SELECT users_types.type " +
						"FROM users LEFT JOIN users_types ON users.users_types_id=users_types.id " +
						"WHERE users.id="+id;
				Statement st = null;
				st = conn.createStatement();
				
				ResultSet rs = null;
				rs = st.executeQuery(strQuery);
				
				if(rs.next()){					
					result = rs.getString("type");					
				}
				st = null;
				rs = null;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;			
		}
		
		
				
		/**
		 * Return the user ID only if it has an access token
		 * @param accessor
		 * @return
		 */
		public static int getUserId (OAuthAccessor accessor) {
			
			int result = 0;
			
			String access_token = accessor.accessToken;
			String request_token = accessor.requestToken ;
			
			try {
				Connection conn = null;
				conn = openDb();				
				String strQuery="SELECT users_id FROM accessors " +
						"WHERE  access_token='"+access_token+"'";
				Statement st = null;
				st = conn.createStatement();
				ResultSet rs = null;
				rs = st.executeQuery(strQuery);
				if(rs.next()){					
					result = rs.getInt("users_id");					
				}
				st = null;
				rs = null;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;
			
		}
				
		
		/***********************    CONSUMER DATA SPECIFIC TABLE METHODS       ***************************/
		
		public static int getConsumerId (String consumerKey) {
			int result = 0;
			
			try {
				Connection conn;
				conn = openDb();				
				String strQuery="SELECT id FROM consumers WHERE consumer_key ='"+consumerKey+"'";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(strQuery);
				if(rs.next()){					
					result = rs.getInt("id");					
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;			
			
		}
		
		public static int getConsumerUsersId (String consumerKey) {
			int result = 0;
			
			try {
				Connection conn;
				conn = openDb();				
				String strQuery="SELECT users_id FROM consumers WHERE consumer_key ='"+consumerKey+"'";
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(strQuery);
				if(rs.next()){					
					result = rs.getInt("users_id");					
				}
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;			
			
		}
		
		public static int getConsumerLevel (String consumerKey) {
			int result = 0;
			
			try {
				Connection conn = null;
				conn = openDb();				
				String strQuery="SELECT level FROM consumers WHERE consumer_key ='"+consumerKey+"'";
				
				Statement st = null;
				st = conn.createStatement();
				ResultSet rs = null;
				rs = st.executeQuery(strQuery);
				if(rs.next()){					
					result = rs.getInt("level");					
				}
				st = null;
				rs = null;
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return result;			
			
		}
		
		/**
		 * Assemble a list rows (each one containing a hashmap that represents each column)
		 * 
		 */
		public static ArrayList<HashMap<String,String>> assembleResult (ResultSet resultSet) throws SQLException {

			ArrayList<HashMap<String,String>> result = new ArrayList<HashMap<String,String>>();

			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			while(resultSet.next()){	    	    	
				
				int columns = rsmd.getColumnCount();				
				
				HashMap<String,String> row = new HashMap<String,String>();				
				for (int i=1; i <= columns ;i++ ){
					String key = rsmd.getColumnName(i); 
					String value = resultSet.getString(i);	    	    		
					row.put(key, value);
				}
				
				result.add(row);
			}
			return result;

		}
		
		/***********************    OPEN CLOSE EXECUTE METHODS ***************************/

		
		public static Connection openDb () throws SQLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, NamingException {
			Connection conn = null;
			InitialContext initCtx = new InitialContext();
			DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/PlatyboxDB");
			conn = ds.getConnection();
			return conn;
		}
		
		
		/**
		 * Execute a SEARCH query
		 * @param strQuery
		 * @return An ArrayList<HashMap<String,String>> 
		 */		
		public static ArrayList<HashMap<String,String>> executeQuery (String strQuery) {
			
			ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
						
			try {
				Connection conn = null;
				conn = DatabaseUtils.openDb();
				
				Statement statement = null;
				statement = conn.createStatement();		
				
				ResultSet resultSet = null;
				resultSet = statement.executeQuery(strQuery);	
				
				result = DatabaseUtils.assembleResult(resultSet);
				statement = null;
				resultSet = null;				
				DatabaseUtils.closeDb(conn);

			} catch (Exception e) {
				e.printStackTrace();
			}
						
			return result;
				
		}
		
		/**
		 * 
		 * @param strUpdate
		 * @return the autogenerated key
		 */
		public static int executeUpdate (String strUpdate) {
			int result = 0;

			try {
				Connection conn = null;
				conn = DatabaseUtils.openDb();
				
				Statement statement = null;				
				statement = conn.createStatement();				
				statement.executeUpdate(strUpdate);			
				
				ResultSet generatedKeys = null;
				generatedKeys = statement.getGeneratedKeys();				
				if (generatedKeys.next()){
					result = (int) generatedKeys.getInt(1);
				}
				
				statement = null;
				generatedKeys = null;
				DatabaseUtils.closeDb(conn);

			} catch (Exception e) {
				e.printStackTrace();
			}
						
			return result;
				
		}		
		
		public static void closeDb (Connection conn) throws SQLException,
			InstantiationException, IllegalAccessException, ClassNotFoundException {
			conn.close();
			conn = null;			
		}
		
}
