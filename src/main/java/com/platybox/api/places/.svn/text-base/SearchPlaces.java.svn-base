package com.platybox.api.places;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platybox.models.places.PlacesModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

/**
 * Unimplemented
 * @author roberto
 *
 */

@Controller
public class SearchPlaces {
	
	@RequestMapping(value="/places/search", method=RequestMethod.POST)	
	public @ResponseBody PlacesModel show(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam int id) throws IOException, ServletException {
		try{
	            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
	            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
	            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
	            
	            if (id > 0){	            	
	            	String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
	            	return PlacesModel.searchPlaces(users_id); //TODO: NOT FOOR PRODUCTION
	            }
	            	
	        }catch (Exception e){
	             OAuthProvider.handleException(e, request, response, false);
	        }

	    return PlacesModel.error();	        
	}
	
}


