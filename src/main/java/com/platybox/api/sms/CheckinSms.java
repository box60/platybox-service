package com.platybox.api.sms;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.provider.core.OAuthProvider;
import com.platybox.sms.SMSUtils;
import com.platybox.utils.CouponsUtils;
import com.platybox.utils.RegisterUtils;
import com.platybox.utils.TokenGenerator;
import com.platybox.utils.database.DatabaseUtils;
import com.platybox.models.bits.BitModel;
import com.platybox.models.bits.BitsScannedModel;
import com.platybox.models.checkins.BitCheckinModel;
import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.phones.PhoneStatusModel;
import com.platybox.models.places.PlaceModel;
import com.platybox.models.places.PlaceProfileModel;
import com.platybox.models.signupremotely.SignupRemotelyResponseModel;
import com.platybox.models.users.UserScoresPlacesModel;
import com.platybox.models.users.UserModel;
import com.platybox.models.weeklypoints.WeeklyPointsModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckinSms {
	
	@RequestMapping(value="/sms/checkin", method=RequestMethod.POST)	
	public void show(	HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam String token,
											@RequestParam String phone, 
											@RequestParam String message) throws IOException, ServletException {				
		
				//TODO: this class needs a cleanup, the new flow doesn't ask for "Signup" but you can still use it to register through sms
		
				//NOTE: tropo messes up the @ sign... giving it a %00, we changed it in the tropo script, just in case.
		        message = URLDecoder.decode(message, "UTF-8"); //Trying to decode @ sign		

    			String responseMessage = null;				
    			int consumerId = DatabaseUtils.getConsumerId(token);
    			
	            if (phone!= null && message!=null && consumerId > 0) {
	            	
	                String status = PhoneStatusModel.findStatus(phone).getStatus();
            		String places_id = null;
            		
            		//intercept a signup command
            		if (message.equalsIgnoreCase("SIGNUP") && status == null) {
            			status = "signupemail";
            			PhoneStatusModel.insertStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (message.equalsIgnoreCase("SIGNUP")) {
            			status = "signupemail";
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (message.equalsIgnoreCase("STOP")) {
            			//Go to the beginning (i.e. wrong code), but fake a welcome message            			
            			status = "stop";
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (status == null) { //trying to scan a code for the first time
            			//find place or set as utopia if an error occurs
            			String bits_id = "0";
            			if (SMSUtils.isMaskedHexAndToday(message)) {
    		            	bits_id = SMSUtils.maskedHexGetBitsId(message);
    		            	places_id = PlaceModel.findPlaceIdByBit(bits_id);
    		            	if (places_id==null) {
    		            		bits_id = "0";
    		            		status = "wrongcode";
    		            	} else {
    		            		status = "welcome";
    	            			BitsScannedModel.logBitScanned(bits_id);//Log this scan
    		            	}
                		} else
                			status = "wrongcode";
	            		PhoneStatusModel.insertStatus(phone, status);
            			PhoneStatusModel.changeBitsId(phone, bits_id);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
	            	} 
            		
            		//go through all statuses
            		
            		else if (status.equalsIgnoreCase("wrongcode") || status.equalsIgnoreCase("stop")) {
            			//gave a valid place?
            			if (SMSUtils.isMaskedHexAndToday(message)) {
    		            	String bits_id = SMSUtils.maskedHexGetBitsId(message);
    		            	places_id = PlaceModel.findPlaceIdByBit(bits_id);
    		            	if (places_id!=null) {
    		            		status = "welcome";
    	            			PhoneStatusModel.changeBitsId(phone, bits_id);
    	            			BitsScannedModel.logBitScanned(bits_id);//Log this scan
    		            	}
                		}
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (status.equalsIgnoreCase("welcome")) {            			
            			if (SMSUtils.isEmail(message)) {            				
            				String email = message.toLowerCase();
                			if (RegisterUtils.checkEmail(email)) {
                				String users_id = signup(email, phone);
                				String bits_id = PhoneStatusModel.findStatus(phone).getBits_id();
                				BitCheckinModel.insertBitCheckin(bits_id, users_id);
                				status = "signupemailsuccess";
                			}
                			else 
                				status = "signupemailerror";
                			PhoneStatusModel.changeStatus(phone, status);
                			responseMessage = PhoneStatusModel.selectMessage(status) ;            				
            			} else if (message.equalsIgnoreCase("SIGNUP")) {
                			status = "signupemail";
                		} else {
                			String users_id = UserModel.existsByUsername(message);                			
                			if ( users_id!=null ) {
                				PhoneStatusModel.changeUsersId(phone, users_id);
                				status = "registerpassword";
                			}
                			else
                				status = "registerusernameerror";
                		}
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status);
            		} else if (status.equalsIgnoreCase("signupemail")){
            			String email = message.toLowerCase();
            			if (RegisterUtils.checkEmail(email)) {
            				String users_id = signup(email, phone);
            				String bits_id = PhoneStatusModel.findStatus(phone).getBits_id();
            				BitCheckinModel.insertBitCheckin(bits_id, users_id);
            				status = "signupemailsuccess";
            			}
            			else 
            				status = "signupemailerror";
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (status.equalsIgnoreCase("signupemailerror")) {
        				String email = message.toLowerCase();
            			if (RegisterUtils.checkEmail(email)){
            				String users_id = signup(email, phone);
            				String bits_id = PhoneStatusModel.findStatus(phone).getBits_id();
            				BitCheckinModel.insertBitCheckin(bits_id, users_id);            				
            				status = "signupemailsuccess";
            			}
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (status.equalsIgnoreCase("registerusernameerror")) {
            			String users_id = UserModel.existsByUsername(message); 
            			if (users_id != null)
            				status = "registerpassword";
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (status.equalsIgnoreCase("registerpassword")){
            			String users_id = PhoneStatusModel.findStatus(phone).getUsers_id();
            			int userId = Integer.parseInt(users_id); 
            			String username = DatabaseUtils.getUsername(userId);
            			if (DatabaseUtils.userExists(username, message)) {
            				UserModel.setPhone(users_id, phone);
            				PhoneStatusModel.changeUsersId(phone, users_id);
            				String bits_id = PhoneStatusModel.findStatus(phone).getBits_id();
	            			BitCheckinModel.insertBitCheckin(bits_id, users_id);
            				status = "registersuccess";
            			}
            			else 
            				status = "registerpassworderror";
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (status.equalsIgnoreCase("registerpassworderror")) {
            			String users_id = PhoneStatusModel.findStatus(phone).getUsers_id();
            			int userId = Integer.parseInt(users_id); 
            			String username = DatabaseUtils.getUsername(userId);
            			if (DatabaseUtils.userExists(username, message)){
            				UserModel.setPhone(users_id, phone);
            				PhoneStatusModel.changeUsersId(phone, users_id);
            				String bits_id = PhoneStatusModel.findStatus(phone).getBits_id();
	            			BitCheckinModel.insertBitCheckin(bits_id, users_id);
            				status = "registersuccess";
            			}
            			PhoneStatusModel.changeStatus(phone, status);
            			responseMessage = PhoneStatusModel.selectMessage(status) ;
            		} else if (status.equalsIgnoreCase("registered")){ 
            			
            			if (SMSUtils.isMaskedHexAndToday(message)) {
    		            	String bits_id = SMSUtils.maskedHexGetBitsId(message);
    		            	places_id = PlaceModel.findPlaceIdByBit(bits_id);
    		            	if (places_id==null) {
    		            		responseMessage = "OOps, you miszpelled that code, or this code is no longer valid.";
    		            	} else {
    		            		
    		            		BitsScannedModel.logBitScanned(bits_id);//Log this scan
    		            		String users_id = UserModel.existsByPhone(phone);
    	            			BitCheckinModel.insertBitCheckin(bits_id, users_id);
    	            			
    	            			//get scores    	            			
    	            			//commented out for now, will use later
    	            			//PlaceScoresModel scores = PlaceScoresModel.selectScore(users_id, places_id);
    	            			WeeklyPointsModel weekpoints = WeeklyPointsModel.showPoints(users_id);
    	            			//String coins = scores.getCoins();
    	            			String scanned = weekpoints.getCheckins();
    	            			String visited = weekpoints.getFeatured();
    	            			String badges = weekpoints.getBadges();
    	            			String placeName = PlaceModel.findPlaceNameByPlacesId(places_id);    	            			
    	            			responseMessage = "Got you at "+placeName+". Your week's points:\n\n" +
    	            			scanned+" scanned, "+
    	            			visited+" featured visited, "+
    	            			badges+" badges. ";
    		            	}
    		            	
            			} else 
            				responseMessage = "OOps, you miszpelled that code, or this code is no longer valid.";
            		}
            		
	 	            if (status.equalsIgnoreCase("registersuccess") || status.equalsIgnoreCase("signupemailsuccess")) {
	 	            	String users_id = PhoneStatusModel.findStatus(phone).getUsers_id();
	 	            	WeeklyPointsModel weekpoints = WeeklyPointsModel.showPoints(users_id);
	 	            	String points = weekpoints.getPoints();
	 	            	responseMessage = responseMessage + " Your week's points: "+points+".";
	 	            }
            		
	            	response.setContentType("text/plain");
	 	            PrintWriter out;
	 	            out = response.getWriter();
	 	            out.println(responseMessage);
	 	            out.close();
	            	
	 	            
	 	            //change to registered if finished
	 	            if (status.equalsIgnoreCase("registersuccess") || status.equalsIgnoreCase("signupemailsuccess")) {	 	            	
	 	            	PhoneStatusModel.changeStatus(phone, "registered");
	 	            }
	            }
	}
	
	private String signup (String email, String phone) {
		String users_id = null;
		String password = TokenGenerator.generateToken(email);
		String users_types_id = "1"; //Register a user level		
		//set username as email
		String username = email; 
		users_id = RegisterUtils.registerUser(username,
												   		email,
												   		phone,
												   		password,
												   		users_types_id);
		if (users_id!=null){

			/*Send Signup Message*/
			String type = "signup";
			EmailsQueueModel.addEmailToQueue(users_id, type);
		}
		return users_id;
	}
	
	
}
