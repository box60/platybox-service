package com.platybox.api.ping;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platybox.sms.TropoSMSConnector;

@Controller
public class Ping extends HttpServlet {
	
	@RequestMapping(value="/ping/ping", method=RequestMethod.GET)	
	public void ping(	HttpServletRequest request,
											HttpServletResponse response)	
											throws IOException, ServletException {
		
		String pingResult = null;
		
		/*start of ping test*/
		TropoSMSConnector sms = new TropoSMSConnector("7789967359", " this is ' fucking awesome % &");
		pingResult = sms.sendSMS().toString();
		/*end*/
		
		response.setContentType("text/plain");
        PrintWriter out;
        out = response.getWriter();
        out.println(pingResult);
        out.close();
        }
    private static final long serialVersionUID = 1L;
}