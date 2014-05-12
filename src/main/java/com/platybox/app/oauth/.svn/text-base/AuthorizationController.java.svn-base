/*
 * Copyright 2007 AOL, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.platybox.app.oauth;

//This servlet receives post messages from our login.jsp and authorize.jsp files. This is not our api authorize servlet.

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
public class AuthorizationController {
    
	@RequestMapping(value="/authorize", method=RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        try{
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage); 
            
            if (Boolean.TRUE.equals(accessor.getProperty("authorized"))) {
                returnToConsumer(requestMessage, request, response, accessor);
            } else {
                return sendToAuthorizePage(request, response, accessor);
            }
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, true);
        }
        response.sendRedirect("profile");
		return null;
    }    
    
	@RequestMapping(value="/authorize", method=RequestMethod.POST) 
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
            
            if (session.getAttribute("THOR")!=null) { //if already logged in
            	if (request.getParameter("Authorize").equalsIgnoreCase("Deny")){
	            	//Remove accessor and send to platybox main page.
	            	OAuthProvider.deleteAccessor(requestMessage, accessor);
	            	response.sendRedirect("home");
	                return "home";           	
				} else  if (request.getParameter("Authorize").equalsIgnoreCase("Authorize")) {
					// set userId in accessor and mark it as authorized
					OAuthProvider.markAsAuthorized(requestMessage, accessor, userId);
	        		returnToConsumer(requestMessage, request, response, accessor);
				} else 
					return sendToAuthorizePage(request, response, accessor);
            } else if (user){
            	session.setAttribute("THOR", userId);            	
            } else if (!user){
            	session.setAttribute("validlogin", "false");
            }
        	return sendToAuthorizePage(request, response, accessor);            

            
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, true);
        }
        return null;
    }
    
    private String sendToAuthorizePage(HttpServletRequest request, 
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
        return "authorize";
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
        
        if("none".equals(callback) 
                && accessor.getProperty("callback_url") != null 
                    && accessor.getProperty("callback_url").toString().length() > 0){
                // first check if we have something in our properties file
                callback = accessor.getProperty("callback_url").toString();
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