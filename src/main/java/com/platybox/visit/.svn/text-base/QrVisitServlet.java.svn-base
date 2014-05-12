package com.platybox.visit;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.platybox.models.bits.BitsScannedModel;

/**
 * This servlet provides appropriate redirection when someone is visiting through
 * a QR code with an external client. This servlet formats a request appropriately
 * and redirects the user.
 */
public class QrVisitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();		
		String bits_id = path.substring(1, path.length() );
		
		if (bits_id != null) {
			
			//TODO: this is working code Check if it's a place bit.			
			String redirectLocation = "http://points.platybox.com/bit/"+bits_id;
			
			BitsScannedModel.logBitScanned(bits_id);
			
			HttpSession session = request.getSession(true); //Set bit id where this came from.
			session.setAttribute("bits_id", bits_id);
			
			response.sendRedirect(redirectLocation);
			
		} else {
			
			HttpSession session = request.getSession(true); //Set bit id where this came from.
			session.setAttribute("bits_id", bits_id);			
			//redirect to homepage.
			response.sendRedirect("home");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	

}
