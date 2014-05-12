package com.platybox.api.places;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.places.PlaceModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShowPlaces {
	
	@RequestMapping(value="/places/show", method=RequestMethod.POST)	
	public @ResponseBody PlaceModel show(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String id, 
											String users_id) throws IOException, ServletException {
		try{
	            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
	            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
	            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
	            
	            if (id != null) {
	            	if (users_id == null)	            	
	            		users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
	            	return PlaceModel.selectPlace(id, users_id);
	            }
	            	
	        }catch (Exception e){
	             OAuthProvider.handleException(e, request, response, false);
	        }

	    return PlaceModel.error();	        
	}
	
}


