
package com.platybox.app.oauth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;

import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import net.oauth.server.OAuthServlet;

@Controller
public class AuthenticationController  {
    
      
	@RequestMapping(value="/authenticate", method=RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        try{
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);            
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            
            if (Boolean.TRUE.equals(accessor.getProperty("authorized"))) {
                returnToConsumer(requestMessage, request, response, accessor);
            } else {
            	return sendToAuthenticatePage(request, response, accessor);
            }
        
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, true);
        }        
        response.sendRedirect("profile");
		return null;
    }    
    
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
    public String doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException{
    	
        try{
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);            
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            
            HttpSession session = request.getSession(true);
            String usernameOrEmail = request.getParameter("usernameOrEmail");
            String password = request.getParameter("password");            
            String userId = String.valueOf(DatabaseUtils.getUserId(usernameOrEmail)); //our accessors are indexed by userId rather than by username.                        
            boolean user = DatabaseUtils.userExists (usernameOrEmail, password);
            
            if (!user && session.getAttribute("THOR")==null){
            	session.setAttribute("validlogin", "false");
            	return sendToAuthenticatePage(request, response, accessor);
            } else if ( user ||  session.getAttribute("THOR")!=null)  { //if already logged in or logging in.
            	
            	session.setAttribute("THOR", userId);
            	int consumerLevel = DatabaseUtils.getConsumerId(accessor.consumer.consumerKey);
            	
            	//auto-authorize if consumer level is less than 3 (our client)
            	if ( consumerLevel <= 3 ){
	        		OAuthProvider.markAsAuthorized(requestMessage, accessor, userId);
	        		returnToConsumer(requestMessage, request, response, accessor);
            	}            	
            } 
            
        	//return sendToAuthenticatePage(request, response, accessor);            

        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, true);
        }
        return null;
    }
    
    
    private String sendToAuthenticatePage(HttpServletRequest request,
            HttpServletResponse response, OAuthAccessor accessor)
    throws IOException, ServletException{
        String callback = request.getParameter("oauth_callback");
        if(callback == null || callback.length() <=0) {
            callback = "none";
        }
        String consumer_description = (String)accessor.consumer.getProperty("description");
        request.setAttribute("CONS_DESC", consumer_description);
        request.setAttribute("CALLBACK", callback);
        request.setAttribute("TOKEN", accessor.requestToken);
        //request.getRequestDispatcher ("authenticate.jsp").forward(request, response);
        return "authenticate";
    }
    
    private ModelAndView returnToConsumer(OAuthMessage requestMessage, HttpServletRequest request, 
            HttpServletResponse response, OAuthAccessor accessor)
    throws IOException, ServletException{
        // send the user back to site's callBackUrl
        String callback = requestMessage.getParameter("oauth_callback");
        
        if("none".equals(callback) 
            && accessor.consumer.callbackURL != null 
                && accessor.consumer.callbackURL.length() > 0){
            // first check if we have something in our properties file
            callback = accessor.consumer.callbackURL;
        }
        
        if( "none".equals(callback) ) {
            // no call back it must be a client
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println("You have successfully authorized '" 
                    + accessor.consumer.getProperty("description") 
                    + "'. Please close this browser window and click continue"
                    + " in the client.");
            out.close();
        } else {
            // if callback is not passed in, use the callback from config
            if(callback == null || callback.length() <=0 )
                callback = accessor.consumer.callbackURL;            
            	String token = accessor.requestToken;
            if (token != null) {
                callback = OAuth.addParameters(callback, "oauth_token", token);
                //TODO: THIS IS a hack for spring security, this needs to be coded.t
                callback = OAuth.addParameters(callback, "oauth_verifier", "K32H872");
            }

            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", callback);
        }
        return new ModelAndView("redirect:"+callback);
    }

    private static final long serialVersionUID = 1L;

}