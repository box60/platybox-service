package com.platybox.app.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.platybox.provider.core.OAuthProvider;
import com.platybox.session.SessionHandler;
import com.platybox.utils.database.DatabaseUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Controller
public class LoginController {
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (SessionHandler.sessionExists(request, response)!=null) {
			response.sendRedirect("profile");
			return null;
		}
		else {
			return "login";
		}
	}
		
	@RequestMapping(value="/loginmerchant", method=RequestMethod.GET)
	public String doGetMerchant(HttpServletRequest request, HttpServletResponse response) throws IOException {		
		if (SessionHandler.sessionExists(request, response)!=null) {
			response.sendRedirect("profile");
			return null;
		}
		else
			return "loginmerchant";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String doPost(HttpServletRequest request, HttpServletResponse response)
	 								throws ServletException,IOException{
		
		HttpSession session = request.getSession(true);
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");
        String referer = request.getHeader("referer");

        boolean user = DatabaseUtils.userExists (usernameOrEmail, password);
        String userId = String.valueOf(DatabaseUtils.getUserId(usernameOrEmail));

        try{
        	if (user) {
        		session.setAttribute("THOR", userId);
        		sendToRefererPage(request, response, referer);
        		return null;
        	} else {
        		session.setAttribute("validlogin", "false");
        	}
        	//sendToRefererPage(request, response, referer);
        } catch (Exception e){
        	OAuthProvider.handleException(e, request, response, true);
        }
		return "redirect:"+referer;//TODO: test     
		//return "login"; 
	}
	
	private void sendToRefererPage(HttpServletRequest request, 
            HttpServletResponse response, String referer) throws IOException, ServletException {
		response.sendRedirect(referer);
	}
	
}
