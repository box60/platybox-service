package com.platybox.app.register;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platybox.models.bits.BitModel;
import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.signupremotely.SignupRemotelyResponseModel;
import com.platybox.models.users.UserModel;
import com.platybox.utils.CreateBitQR;
import com.platybox.utils.RegisterUtils;
import com.platybox.utils.TokenGenerator;
import com.platybox.utils.database.DatabaseUtils;

/**
 * This controller provides a simple response to ping the api.
 */
@Controller
public class SignupRemotelyController {
			

	/**
	 * A temporary controller for chowvancouver signup this will be deprecated soon.
	 * @param email
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/signupemail", method=RequestMethod.GET)	
	public @ResponseBody SignupRemotelyResponseModel signupByEmail(
										String email,
										HttpServletRequest request) {
		
		HttpSession session = request.getSession(true);		
		int id = DatabaseUtils.getUserId(email);		
		Boolean valid = RegisterUtils.checkEmail(email, session);
		
		if (id <= 0 && valid) { //if it doesn't exist

			//generate a password from the email
			String password = TokenGenerator.generateToken(email);
			String users_types_id = "1"; //Register a user level
			
			//set username as email
			String username = email; 
			String phone = null;
			String users_id = RegisterUtils.registerUser(username,
													   		email,
													   		phone,
													   		password,
													   		users_types_id);
			if (users_id!=null){
			
				/*Send Signup Message*/
				String type = "signup";
				EmailsQueueModel.addEmailToQueue(users_id, type);
				
				return SignupRemotelyResponseModel.createResponse("We've sent you an invite.");

			} else
				return SignupRemotelyResponseModel.createResponse("Please try later.");				
		}
		return SignupRemotelyResponseModel.createResponse("Email already taken.");
	}
}

