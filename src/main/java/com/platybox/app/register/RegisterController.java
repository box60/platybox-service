package com.platybox.app.register;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.platybox.models.bits.BitsRegistrationsModel;
import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.users.UserModel;
import com.platybox.utils.CouponsUtils;
import com.platybox.utils.RegisterUtils;

@Controller
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String registerGet( HttpServletRequest request) {
			return "register";
	}

	@RequestMapping(value="/registermerchant", method=RequestMethod.GET)
	public String registerMerchantGet( HttpServletRequest request) {		
			return "registermerchant";
	}
		
	@RequestMapping(value="/register", method=RequestMethod.POST)

	public String registerPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String name = request.getParameter("name");
		String username = request.getParameter("username").toLowerCase();
		String email = request.getParameter("email").toLowerCase();
		String password = request.getParameter("password");
		String passwordverify = password;
		String users_types_id = request.getParameter("users_types_id");
		String referer = request.getHeader("referer");

		boolean valid = true;   
		
		if (username!=null && name!=null && email!=null && password!=null && passwordverify!=null && users_types_id!=null && referer!=null) {
			if (RegisterUtils.checkName(name, session) == false)
				valid = false;
			if (RegisterUtils.checkUsername(username, session) == false)
				valid = false;
			if (RegisterUtils.checkEmail(email, session)== false)
				valid = false;
			if (RegisterUtils.checkPassword(password, passwordverify, session) == false )
				valid = false;
			
			if (valid){ // all ok, create profile
			    String phone = null;
				String users_id = RegisterUtils.registerUser(username,
														   email,
														   phone,
														   password,
														   users_types_id);
				if (users_id!=null){
					UserModel.setName(users_id, name); //TODO: this shoudl call its own model
					UserModel.setActive(users_id);
					
					/*Check if they came in through a place and checkin*/
					String bits_id = null;
					if (session.getAttribute("bits_id") != null) {
						bits_id = (String)session.getAttribute("bits_id");
						BitsRegistrationsModel.logBitLedToRegistration(bits_id, users_id);
					}
					
					/*Give register coupon if available*/
					CouponsUtils.giveRegisterCoupon(users_id);
					
					/*Squedule Welcome Message*/
					EmailsQueueModel.addEmailToQueue(users_id, "register");
					
					/*Login*/
					session.setAttribute("THOR", users_id);					
					return "redirect:profile"; //TODO: change! 					
					
				} else
					return "redirect:"+referer;//TODO: test   	     
			} else
				return "redirect:"+referer;//TODO: test     
		}
		else return "redirect:"+referer;
	}
	
}
