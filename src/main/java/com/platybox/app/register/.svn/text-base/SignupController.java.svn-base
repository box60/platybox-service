package com.platybox.app.register;


import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platybox.email.EmailManager;
import com.platybox.email.PlatyboxEmailManager;
import com.platybox.models.bits.BitModel;
import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.users.UserModel;
import com.platybox.utils.CreateBitQR;
import com.platybox.utils.CouponsUtils;
import com.platybox.utils.RegisterUtils;
import com.platybox.utils.TokenGenerator;
import com.platybox.utils.database.DatabaseUtils;
@Controller
public class SignupController extends PlatyboxEmailManager {
		
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String showForm () {
			return "signupform";
	}
	
	@RequestMapping(value="/s", method=RequestMethod.GET)
	public String udpatePasswordForm (HttpServletRequest request,
								  String i,
								  String t,
								  ModelMap map) {
		if (i!=null || t !=null ){
			String users_id = i;
			String token = t;
			UserModel user = UserModel.selectTokenSignup(users_id, token);
			if (user.getUser()!=null ){
				UserModel.deleteTokenSignup(users_id, token);								
				map.put("userId", users_id);
				return "signupcreateform";
			} else 
				return "signupcreateerror";
		}
		else return "signupcreateerror";
	}
	
	@RequestMapping(value="/s", method=RequestMethod.POST)
	public String updatePassword(HttpServletRequest request)
		throws ServletException,IOException{
		
		HttpSession session = request.getSession(true);
		String users_id = request.getParameter("userId");
		session.setAttribute("userId", users_id);
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String passwordverify = password;
		
		Boolean valid = true;
		
		if (RegisterUtils.checkUsername(username, session) == false)
			valid = false;
		if (RegisterUtils.checkName(name, session) == false)
			valid = false;
		if (RegisterUtils.checkPassword(password, passwordverify, session) == false)
			valid = false;
		
		if (valid){
			UserModel user = UserModel.updatePassword(users_id, password);							
			if (user.getUser()==null ){
				return "signupcreateerror";
			}else {
				HashMap <String,String> values = new HashMap <String,String> ();
				values.put("username", username);
				user = UserModel.updateUser(users_id, values);
				UserModel.setName(users_id, name);//TODO: this shoudl call its own model
				UserModel.setActive(users_id);
				UserModel.setVerified(users_id);
				if (user.getUser()==null ) 
					return "signupcreateerror";
				else {
					
					/*Give register coupon if available*/
					CouponsUtils.giveRegisterCoupon(users_id);
					
					/*Login*/
					session.setAttribute("THOR", users_id);
					return "redirect:http://chowvancouver.com";
				}
			}
			
		} else
			return "signupcreateform";		
	}
			
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String recoverPassword(HttpServletRequest request)
		throws ServletException,IOException{
		
		HttpSession session = request.getSession(true);
		String email = request.getParameter("email").toLowerCase();
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
								
				//TODO: deprecate?
				return "signupsuccess";

			} else
				return "signupform";
		} else {
			return "signupform";
		}				
	}
}
