package com.platybox.utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import com.platybox.models.bits.BitModel;
import com.platybox.models.bits.BitsRegistrationsModel;
import com.platybox.models.users.UserModel;
import com.platybox.utils.database.DatabaseUtils;

public final class RegisterUtils {
	
	public static String registerUser (String username,
									 String email,
									 String phone,
									 String password,
									 String users_types_id){
		
		String result = null;
		
		/*Escape phone and email being null*/
		if (phone==null)
			phone = "NULL";
		else 
			phone = "'"+phone +"'";
		if (email==null) 
			email = "NULL";
		else 
			email = "'"+email+"'";
				
		UserModel user = UserModel.insertUser(username, email, phone, password, users_types_id);        	
		String users_id = user.getUser().get("id");
		
		if ( users_id != null) { // if successfully inserted create a user and a bit. 
			//insert an empty QR code for now.
			String bits_types_id = "8";
			String name = username;
			String description = "A physical representation of myself";
			String places_id = "1";
			String qr_image_url = "";              
			String bits_id = BitModel.insertBit(bits_types_id,
					name,
					description,
					qr_image_url,
					places_id).getBit().get("id"); 			
			//Good, now we create its qrcode
			int bitId = Integer.parseInt(bits_id);
			CreateBitQR bitQr = new CreateBitQR(bitId);	               	
			String bitQrUrl = bitQr.create();
			if (bitQrUrl != null) {
				HashMap <String,String> values = new HashMap<String,String>();            	             	  
				values.put("qr_image_url", bitQrUrl);
				BitModel.updateBit(bits_id, values);  
			}
			HashMap <String,String> values = new HashMap<String,String>();            	             	  
			values.put("bits_id", String.valueOf(bitId));
			UserModel.updateUser(users_id, values);
			
			result = users_id;
		
			}		
		return result;
	}

	
	public static boolean checkUsername (String username, HttpSession session) {
		boolean valid = true;
	//check username
		if (username.equalsIgnoreCase("")){
			session.setAttribute("missingusername", "true");
			valid = false;
		} else if (!validUsername(username)){     	
			session.setAttribute("invalidusername", "true");
			valid = false;
		} else if (!DatabaseUtils.availableUsername(username)){
			session.setAttribute("unavailableusername", "true");
			valid = false;
		} else
			session.setAttribute("username", username);
		return valid;
	}
	

	public static boolean checkUsername (String username) {
		boolean valid = true;
		if (username.equalsIgnoreCase("")){
			valid = false;
		} else if (!validUsername(username)){     	
			valid = false;
		} else if (!DatabaseUtils.availableUsername(username)){
			valid = false;
		} 
		return valid;
	}
	
	
	public static boolean checkName (String name, HttpSession session) {
		boolean valid = true;
	//check username
		if (name.equalsIgnoreCase("")){
			session.setAttribute("missingname", "true");
			valid = false;
		} else if (!validName(name)){     	
			session.setAttribute("invalidname", "true");
			valid = false;
		} else
			session.setAttribute("name", name);
		return valid;
	}
	
	public static boolean checkName (String name) {
		if (name.equalsIgnoreCase("") || !validName(name)){     	
			return false;
		} else
			return true;
	}
	
	public static boolean checkDescription (String description) {
		if (description.equalsIgnoreCase("")){     	
			return false;
		} else
			return true;
	}
	
	public static boolean checkEmail (String email, HttpSession session) {

		boolean valid = true;
		
		if (email.equalsIgnoreCase("")){
			session.setAttribute("missingemail", "true");
			valid = false;
		} else if (!validEmail(email)){     	
			session.setAttribute("invalidemail", "true");
			valid = false;        	
		} else if (!DatabaseUtils.availableEmail(email)){
			session.setAttribute("unavailableemail", "true");
			valid = false;
		} else
			session.setAttribute("email", email);
		return valid;
	}
	
	public static boolean checkEmail (String email) {
		boolean valid = true;		
		if (email.equalsIgnoreCase("")){
			valid = false;
		} else if (!validEmail(email)){     	
			valid = false;        	
		} else if (!DatabaseUtils.availableEmail(email)){
			valid = false;
		} 
		return valid;
	}
	
	public static boolean checkPassword (String password, String passwordverify,
									HttpSession session) {

		boolean valid = true;
		if (password.equalsIgnoreCase("")){
			session.setAttribute("missingpassword", "true");
			valid = false;
		} else if (!RegisterUtils.validPassword(password)){     	
			session.setAttribute("invalidpassword", "true");
			valid = false;        	
		} else if (!matchingPassword(password, passwordverify)){
			session.setAttribute("notmatchingpassword", "true");
			valid = false;
		}
		return valid;
		
	}
	
	private static boolean validName (String name){

		boolean result = false;

		Pattern p = Pattern.compile("[A-Za-z]+\\s+['A-Za-z]+");
		Matcher m = p.matcher(name);
	    
	    if (m.matches())
	    	result = true;
		
	    return result;
		
	}
	
	
	private static boolean validUsername (String userId){

		boolean result = false;

		Pattern p = Pattern.compile("^[\\w\\d\\_\\.]{1,}");
		Matcher m = p.matcher(userId);
	    
	    if (m.matches())
	    	result = true;
		
	    return result;
		
	}
	
	private static boolean validEmail (String email) {
	                  
		boolean result = false;
		
	    Pattern p = Pattern.compile("^[\\_]*([a-z0-9]+(\\.|\\_*)?)+@([a-z][a-z0-9\\-]+(\\.|\\-*\\.))+[a-z]{2,6}");
	    Matcher m = p.matcher(email);
	    
	    if (m.matches())
	    	result = true;
		
	    return result;
		  
	  }
	
	private static boolean validPassword (String password) {
        
		boolean result = false;
		
	    Pattern p = Pattern.compile("^.{6,}");
	    Matcher m = p.matcher(password);
	    
	    if (m.matches())
	    	result = true;
		
	    return result;
		  
	  }
	
	private static boolean matchingPassword (String password, String passwordverify){
		
		boolean result = false;
				
		if (password.equals(passwordverify))
			result = true;
	    return result;
		
	}
	

}
