package com.platybox.api.places;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.places.PlaceModel;
import com.platybox.models.places.PlaceProfileModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfilePlaces {
	
	@RequestMapping(value="/places/profile", method=RequestMethod.POST)	
	public @ResponseBody PlaceProfileModel show(	HttpServletRequest request,
											HttpServletResponse response,											
											String bits_id) throws IOException, ServletException {		        
	            if (bits_id != null) {
	            	return PlaceProfileModel.selectPlaceBiBitId(bits_id);
	            } else
	            	return PlaceProfileModel.error();	        
	}
	
}


