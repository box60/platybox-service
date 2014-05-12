package com.platybox.api.users;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.users.UserModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * Example response:
 * {"user":{"id":"3","username":"test","coins":"0","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"0","points":"0","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}}
 */
@Controller
public class ShowUsers extends HttpServlet {
	
	@RequestMapping(value="/users/show", method=RequestMethod.GET)
	public @ResponseBody UserModel showCurrentUser(	HttpServletRequest request,
											HttpServletResponse response)	
											throws IOException, ServletException {		
		try {
			OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);            
            String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
            return UserModel.selectUser(users_id);            
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, false);
        }
        return UserModel.error();        
    }
	
	@RequestMapping(value="/users/show", method=RequestMethod.POST)
	public @ResponseBody UserModel showUser(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String id)  	
											throws IOException, ServletException {		
		try {
			OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
            
            String users_id = id;
            
            if (users_id == null) {
            	users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
            }
            
            return UserModel.selectUser(users_id);            
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, false);
        }
        return UserModel.error();        
    }
	
    private static final long serialVersionUID = 1L;
}
