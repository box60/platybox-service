package com.platybox.app.register;


import java.io.IOException;
import java.util.HashMap;

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
import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.users.UserModel;
import com.platybox.utils.RegisterUtils;
import com.platybox.utils.TokenGenerator;
import com.platybox.utils.database.DatabaseUtils;
@Controller
public class RecoverPasswordController extends PlatyboxEmailManager {
		
	@RequestMapping(value="/recover", method=RequestMethod.GET)
	public String showForm () {
		return "recoverform";
	}
	
	@RequestMapping(value="/r", method=RequestMethod.GET)
	public String udpatePasswordForm (HttpServletRequest request,
								  String i,
								  String t,
								  ModelMap map) {
		if (i!=null || t !=null ){
			String users_id = i;
			String token = t;
			UserModel user = UserModel.selectTokenRecover(users_id, token);
			if (user.getUser()!=null ){
				UserModel.deleteTokenRecover(users_id, token);								
				map.put("userId", users_id);
				return "updateform";
			} else 
				return "updateerror";
		}
		else return "updateerror";
	}
	
	@RequestMapping(value="/r", method=RequestMethod.POST)
	public String updatePassword(HttpServletRequest request)
		throws ServletException,IOException{
		
		HttpSession session = request.getSession(true);
		String users_id = request.getParameter("userId");
		session.setAttribute("userId", users_id);
		String password = request.getParameter("password");
		String passwordverify = request.getParameter("passwordverify");

		Boolean valid = RegisterUtils.checkPassword(password, passwordverify, session);
				
		if (valid){
			UserModel user = UserModel.updatePassword(users_id, password);
			if (user.getUser()==null ){
				return "updateerror";
			} else {
				session.setAttribute("THOR", users_id);
				return "redirect:profile";
			}		
		} else
			return "updateform";
		
	}
	
		
	@RequestMapping(value="/recover", method=RequestMethod.POST)
	public String recoverPassword(HttpServletRequest request)
		throws ServletException,IOException{
		
		HttpSession session = request.getSession(true);
		String email = request.getParameter("email");
		int id = DatabaseUtils.getUserId(email);		
		if (id > 0) {			
			String users_id = String.valueOf(id);
			String type = "recover";
			EmailsQueueModel.addEmailToQueue(users_id, type);			
			return "recoversent";
		} else {
			session.setAttribute("validemail", "false");
			return "recoverform";
		}			
	
	}
	
}
