package com.platybox.api.bits;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.bits.BitModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.CharEscaper;
import com.platybox.utils.CreateBitQR;
import com.platybox.utils.BitsUtils;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/*
 * Example response:
 * {"bit":{"id":"61","qr_image_url":"http://localhost/1/resources/images/qr/61.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"1","type":"bit"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"checkins":"0","promos":"7"}}}
 */

@Controller
public class CreateBits extends HttpServlet {
	
	@RequestMapping(value="/bits/create", method=RequestMethod.POST)	
	public @ResponseBody BitModel createBits(HttpServletRequest request,
											HttpServletResponse response,
											//Required
											@RequestParam String bits_types_id,
											@RequestParam String name,
											@RequestParam String description,
											//Optional
											String places_id )
											throws IOException, ServletException {
		try{
	            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
	            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
	            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
	            
	            if (bits_types_id != null || name != null || description != null ){
	            	
	            	String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
	            	return BitsUtils.createBit(users_id, bits_types_id, name, description, places_id);
	            	
	            }	            
	        }catch (Exception e){
	             OAuthProvider.handleException(e, request, response, false);
	        }

	        return BitModel.error();
	}	
    private static final long serialVersionUID = 1L;    
}