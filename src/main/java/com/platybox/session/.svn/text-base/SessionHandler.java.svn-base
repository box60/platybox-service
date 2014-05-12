package com.platybox.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionHandler {
	
	public static String sessionExists (HttpServletRequest request, HttpServletResponse response) {		
		HttpSession session = request.getSession(true);
		String check = (String)(session.getAttribute("THOR"));
		if(check!=null) { //IF LOGGED IN 
			return check;
		}		
		return null;		
	}
		
}
