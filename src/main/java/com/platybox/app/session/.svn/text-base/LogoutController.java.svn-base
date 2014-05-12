package com.platybox.app.session;

import java.io.IOException;

import javax.servlet.ServletContext;
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

@Controller
public class LogoutController {

	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String doGet(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {		
		process(request,response);		
		return null;		
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.sendRedirect("home");
		session.invalidate();		
	}
	
}
