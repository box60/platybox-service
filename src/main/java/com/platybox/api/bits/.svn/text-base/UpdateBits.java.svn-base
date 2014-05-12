package com.platybox.api.bits;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.bits.BitModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Example response:
 * {"bit":{"id":"2","qr_image_url":"http://localhost/1/resources/images/qr/2.gif","places_id":"2","description":"Changed description","name":"A pink promo modified","bits_types_id":"3","type":"table"},"place":{"place":{"id":"2","geolong":"-123.184674","name":"Box 60 Inc.","geolat":"49.263495"},"mastery":{"checkins":"16","promos":"0"}}}
 */

@Controller
public class UpdateBits extends HttpServlet {
	
	@RequestMapping(value="/bits/update", method=RequestMethod.POST)	
	public @ResponseBody BitModel showBits(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String id,
											//optional
											@RequestParam String bits_types_id,
											@RequestParam String name,
											@RequestParam String description,
											@RequestParam String places_id)
											throws IOException, ServletException {	
    	
    	 try{         	
             OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
             OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
             OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
             
             String bits_id = id;             	
             if (bits_id!=null) { 
            	 
            	 String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
            	 
            	 //to prevent db errors we will assemble a hashmap only with valid parameters
            	 HashMap <String,String> values = new HashMap<String,String>();
            	 if (bits_types_id != null)
            		 values.put("bits_types_id", bits_types_id);
            	 if (name != null)
            		 values.put("name", name);
            	 if (description != null)
            		 values.put("description", description);
            	 if (places_id != null)
            		 values.put("places_id", places_id);
            	 return BitModel.updateBit(bits_id, values);
             }
             
         } catch (Exception e){
             OAuthProvider.handleException(e, request, response, false);
         }
         return BitModel.error();        
    }
    private static final long serialVersionUID = 1L;
}
