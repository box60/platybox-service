package com.platybox.api.friends;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.friends.FriendsModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Example response
 * {"friends":[{"user":{"user":{"id":"3","username":"test","coins":"0","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"0","points":"0","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},"friend":{"id":"1","friend_users_id":"3","users_id":"2"}}]}
 */

@Controller
public class CreateFriends extends HttpServlet {	
	
	@RequestMapping(value="/friends/create", method=RequestMethod.POST)	
	public @ResponseBody FriendsModel create(HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String id) throws IOException, ServletException {
    	 try{
             OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
             OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
             OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
             
             String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
             String friend_users_id = id;
             
             if (users_id.equals(friend_users_id) == false) { //avoid narcisism	             
            	 return FriendsModel.insertFriend(users_id, friend_users_id);            	    
	         }
         } catch (Exception e){
             OAuthProvider.handleException(e, request, response, false);
         }
         return FriendsModel.error();
    }
	private static final long serialVersionUID = 1L;
}