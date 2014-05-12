package com.platybox.provider.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.platybox.utils.database.DatabaseUtils;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.OAuthValidator;
import net.oauth.SimpleOAuthValidator;
import net.oauth.server.OAuthServlet;

//TODO: remove all database calls from here!!! wtf!
// in accessors, user should not be a string, but userId (int).
// you have to change all appearances of userId if they refer to username.
// create an initialization servlet building initial db and cleaning it. (removing old and not verified users, and tokens)

public class OAuthProvider {

    public static final OAuthValidator VALIDATOR = new SimpleOAuthValidator();
      
    /**
     * Returns a consumer object from the DB
     * @param requestMessage a request message to get the key of the consumer..
     * @return An OauthConsumer 
     */
    public static synchronized OAuthConsumer getConsumer(String consumerKey)
            throws IOException, OAuthProblemException {

    	String consumer_keyString = consumerKey;
	   	String callback_url = null;
	   	String secret = null;
	   	String description = null;
	   	String id = null;
	   	boolean assemble = false;
    	
    	OAuthConsumer consumer = null;
    
    	//try to load from db, if not throw exception.
        try {        	

        	Connection conn;		
    		conn = DatabaseUtils.openDb();    		
    	   	Statement statement = conn.createStatement();
    	   	ResultSet resultSet = null;
    	   	//   	String strQuery= "SELECT consumers.callback_url,AES_DECRYPT(consumers.consumer_secret,'xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'),users.description FROM platybox.consumers WHERE consumers.consumer_key ='"+consumer_keyString+"'";
    	   	
    	   	String strQuery = "SELECT consumers.id, consumers.callback_url, AES_DECRYPT(consumers.consumer_secret,'xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), users_profiles.name " +
    	   			"FROM platybox.consumers " +
    	   			"LEFT JOIN platybox.users_profiles on consumers.id=users_profiles.users_id " +
    	   			"WHERE consumers.consumer_key ='"+consumer_keyString+"'";

    	   	
    	   	resultSet = statement.executeQuery(strQuery);
    	    if(resultSet.next()){
    	    	callback_url = resultSet.getString("callback_url");
    	    	secret = resultSet.getString(3);
    	    	description = resultSet.getString("name");
    	    	id = String.valueOf(resultSet.getInt("id"));
    	    	assemble = true;
    	    }
    	    DatabaseUtils.closeDb(conn);
    	   
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        if (assemble) {
	    	consumer = new OAuthConsumer(	    			
	    			callback_url,
                    consumer_keyString, 
                    secret,
                    null);
	    	consumer.setProperty("id", id);
            consumer.setProperty("name", consumer_keyString);
            consumer.setProperty("description", description);
        }
        
        
	   	if(consumer == null) {
            OAuthProblemException problem = new OAuthProblemException("token_rejected");
            throw problem;
        } else 
        	return consumer;

    }
    
    /**
     * Get the access token and token secret for the given oauth_token.
     * @param requestMessage Request message
     * @return an OAuthAccessor 
     */
    public static synchronized OAuthAccessor getAccessor(OAuthMessage requestMessage)
            throws IOException, OAuthProblemException {
        
    	OAuthAccessor accessor =  null;      	
    	String consumer_token = requestMessage.getToken();     	
    	String consumer_key = requestMessage.getConsumerKey();
    	
		//try to load from DB if not throw exception.
    	
    	//check for null values.
    	if (consumer_token!=null ) {  
	    	try {

	    		//Check if this is a two legged request
	    		if (consumer_token.equalsIgnoreCase("")) { //is an empty value
	
	    			OAuthConsumer consumer = OAuthProvider.getConsumer(consumer_key);
	    			accessor = new OAuthAccessor (consumer); 
	    			accessor.setProperty("user", consumer_key); //consumer_key is the username in a two legged approach.
	
	    			if ( consumer!=null ){
	    				accessor.setProperty("authorized", Boolean.TRUE);
	    			}
	
	    		} else {
	
	    			Connection conn;		
	    			conn = DatabaseUtils.openDb();
	    			Statement statement = conn.createStatement();
	    			ResultSet resultSet = null;
	
	    			String strQuery="SELECT consumers.consumer_key, accessors.access_token, accessors.request_token, " +
	    					"AES_DECRYPT(accessors.token_secret,'xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), accessors.callback_url , accessors.users_id, accessors.authorized " +
	    				"FROM platybox.accessors " +
	    				"LEFT JOIN platybox.consumers on accessors.consumers_id=consumers.id " +
	    				"WHERE accessors.request_token ='"+consumer_token+"' OR accessors.access_token='"+consumer_token+"'";	    				    				
	    				
	    			resultSet = statement.executeQuery(strQuery);		    				
	    				
	    			if(resultSet.next()){
	    				String consumerKey = resultSet.getString("consumer_key");
	    				OAuthConsumer consumer = OAuthProvider.getConsumer(consumerKey);
	    				accessor = new OAuthAccessor (consumer); 
	    				accessor.accessToken = resultSet.getString("access_token");
	    				accessor.requestToken = resultSet.getString ("request_token");
	    				accessor. tokenSecret = resultSet. getString(4); //token_secret
	    				accessor.setProperty("user", resultSet.getString("users_id"));
	    				accessor.setProperty("callback_url", resultSet.getString("callback_url"));
	    				if ( resultSet.getBoolean("authorized")){
	    					accessor.setProperty("authorized", Boolean.TRUE);
	    				}
	    				
	    			}
	
	    			DatabaseUtils.closeDb(conn);
	
	    		}
	
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
    	}

    	if(accessor == null){
        	OAuthProblemException problem = new OAuthProblemException("token_expired");
        	throw problem;
        } 
    	   
        return accessor;
    }

    
    /** REMOVE!!!
     * Generate an authorized accessor for our client, only used when registering a new user.
     * 
     * This method might be deprecated soon
     * 
     */
    /*
    public static synchronized void generateClientAccessor( int consumers_id, int users_id )
            throws OAuthException {
    	    	
        // for now use md5 of name + current time as token
        String token_data = String.valueOf(consumers_id) + String.valueOf(users_id) + System.nanoTime();
        String token = DigestUtils.md5Hex(token_data);
        // for now use md5 of name + current time + token as secret
        String secret_data = consumers_id + users_id + System.nanoTime() + token;
        String secret = DigestUtils.md5Hex(secret_data);

        //Update DB
    	try {
        	Connection conn;		
    		conn = DatabaseUtils.openDb();    		
    	   	Statement statement = conn.createStatement();
    	   	String strUpdate="INSERT INTO accessors (users_id, consumers_id, access_token, token_secret, authorized) " +
    	   					"VALUES ('"+users_id+"','"+consumers_id+"','"+token+"',AES_ENCRYPT('"+secret+"','xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), '1')";    	   	
    	   	statement.executeUpdate(strUpdate);
    	   	DatabaseUtils.closeDb(conn);    		
    	} catch (Exception e) {
			e.printStackTrace();
		}            	
    }
     */   
    /**
     * Set authorized 
     *  
     */
    
    public static synchronized void markAsAuthorized(OAuthMessage requestMessage, OAuthAccessor accessor, String userId)
            throws OAuthException {
            	
    	try {
        	Connection conn;		
    		conn = DatabaseUtils.openDb();
    		String token = requestMessage.getToken(); //check that the token is the access token.
    		String consumer_key = accessor.consumer.consumerKey;
    	   	Statement statement = conn.createStatement();
    	   	
    	   	//TODO: use only one space for tokens on DB, remove access_token and only update accordingly.
    	   	String strUpdate="UPDATE accessors SET authorized=TRUE, users_id='"+userId+"' WHERE access_token='"+token+"' OR request_token='"+token+"'";
    	   	int resultcount = statement.executeUpdate(strUpdate);
    	   	
    	   	//TODO: remove all previous authorized keys.
    	   	//TODO: do some cleanup for this consumer, clean 1000 old unauthorized tokens for this consumer 	
    	   		   	
    	   	DatabaseUtils.closeDb(conn);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    /**
     * Generate a fresh request token and secret for a consumer.
     * 
     * @throws OAuthException
     * @throws IOException 
     */
    public static synchronized HashMap <String,String> generateRequestToken(
            OAuthAccessor accessor,
            OAuthMessage requestMessage)
            
            throws OAuthException, IOException {
    	
    	HashMap result = new HashMap <String,String> ();
    	
    	String callback_url = requestMessage.getParameter("oauth_callback");
    	
    	if (callback_url == null)
    		callback_url = "";
    	
    	//Get id
    	String consumers_id = (String) accessor.consumer.getProperty("id");
    	
        // generate oauth_token and oauth_secret    	
        String consumer_key = (String) accessor.consumer.getProperty("name");
        // generate token and secret based on consumer_key
        
        
        
        // for now use md5 of name + current time as token
        String token_data = consumer_key + System.nanoTime();
        String token = DigestUtils.md5Hex(token_data);
        // for now use md5 of name + current time + token as secret
        String secret_data = consumer_key + System.nanoTime() + token;
        String secret = DigestUtils.md5Hex(secret_data);
        
        result.put("token", token);
        result.put("secret", secret);
        
        //Update DB
    	try {
        	Connection conn;		
    		conn = DatabaseUtils.openDb();
    		
    	   	Statement statement = conn.createStatement();
    	   	
    	   	//insert token and secret, leave accesstoken to null  	
    	   	String strUpdate="INSERT INTO accessors (consumers_id, request_token, token_secret, callback_url) " +
    	   					"VALUES ('"+consumers_id+"', " +
    	   							"'"+token+"', " +
    	   							"AES_ENCRYPT('"+secret+"', " +
    	   							"'xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), " +
    	   							"'"+callback_url+"')";    	   	
    	   	int resultcount = statement.executeUpdate(strUpdate);
    	   		   	
    	   	DatabaseUtils.closeDb(conn);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
        
    	return result;
    	
    }
    
    /**
     * Generate a fresh request token and secret for a consumer.
     * 
     * @throws OAuthException
     */
    public static synchronized HashMap <String,String> generateAccessToken(OAuthAccessor accessor)
            throws OAuthException {
    	
    	HashMap result = new HashMap <String,String> ();
    	
    	// generate oauth_token and oauth_secret
        String consumer_key = (String) accessor.consumer.getProperty("name");
        // generate token and secret based on consumer_key
        
        // for now use md5 of name + current time as token
        String token_data = consumer_key + System.nanoTime();
        String token = DigestUtils.md5Hex(token_data);
        // for now use md5 of name + current time + token as secret
        String secret_data = consumer_key + System.nanoTime() + token;
        String secret = DigestUtils.md5Hex(secret_data);
        
        result.put("token", token);
        result.put("secret", secret);
        
      //Update DB
    	try {
        	Connection conn;		
    		conn = DatabaseUtils.openDb();
    		
    	   	Statement statement = conn.createStatement();
    	   	
    	   	//remove request token
    	   	String request_token = accessor.requestToken;
    	   	
    	   	//TODO: there should be only one token column on the database.
    	   	String strUpdate="UPDATE accessors SET access_token='"+token+"',request_token='',token_secret=AES_ENCRYPT('"+secret+"','xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0') " +
    	   			"WHERE request_token='"+request_token+"'";
    	   	int resultcount = statement.executeUpdate(strUpdate);
    	   	
    	   	DatabaseUtils.closeDb(conn);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }

    
    public static synchronized HashMap <String,String> generateConsumer(String user_id)
    throws OAuthException, NumberFormatException, IOException, ServletException {

    	HashMap result = new HashMap <String,String> ();
    	
		// generate consumer key and consumer secret
	    String consumer_key = user_id;         	    
	    // for now use md5 of usernaname + current time as token
	    String key_data = consumer_key + System.nanoTime();
	    String key = DigestUtils.md5Hex(key_data);
	    // for now use md5 of name + current time + key as secret
	    String secret_data = consumer_key + System.nanoTime() + key;
	    String secret = DigestUtils.md5Hex(secret_data);
	    
	    result.put("token", key);
        result.put("secret", secret);
	    
	    try {
			
			Connection conn;
			conn = DatabaseUtils.openDb();
			
			String strUpdate="INSERT INTO consumers (users_id, consumer_key, consumer_secret, callback_url) " +
					"VALUES ('"+user_id+"','"+key+"',AES_ENCRYPT('"+secret+"','xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0'), '')";
			Statement st = conn.createStatement();
			st.executeUpdate(strUpdate); //executeUpdate for insert, update and delete returns nothing
			
			DatabaseUtils.closeDb(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}
	    
    	  
	    return result;
	    
    }
    
    /**
     * If a user denies acccess to a consumer we will delete ALL accessors with such consumer/user pair.
     */
    //TODO: CHECK IF NECESSARYuse only one space for tokens on DB, remove access_token and only update accordingly.
    public static synchronized void deleteAccessor (OAuthMessage requestMessage, OAuthAccessor accessor)
            throws OAuthException {
            	
    	try {
        	Connection conn;		
    		conn = DatabaseUtils.openDb();
    		String token = requestMessage.getToken(); //check that the token is the access token.
        	String consumers_id = (String) accessor.consumer.getProperty("id");
    	   	Statement statement = conn.createStatement();
    	   	String strUpdate="DELETE FROM accessors WHERE consumers_id='"+consumers_id+"' AND request_token='"+token+"'";
    	   	statement.executeUpdate(strUpdate);
    	   		   	
    	   	DatabaseUtils.closeDb(conn);
    		
    	} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    
    
    
    public static void handleException(Exception e, HttpServletRequest request,
            HttpServletResponse response, boolean sendBody)
            throws IOException, ServletException {
        String realm = (request.isSecure())?"https://":"http://";
        realm += request.getLocalName();
        OAuthServlet.handleException(response, e, realm, sendBody); 
    }
   

}
