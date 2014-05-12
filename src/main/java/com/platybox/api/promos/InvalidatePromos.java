package com.platybox.api.promos;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.promos.PromoModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Example response:
 * {"bit":{"bit":{"id":"16","qr_image_url":"http://localhost/1/resources/images/qr/16.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"10","valid":"0","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"2","bits_id":"16","available":"0"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"checkins":"15","promos":"8"}}}
 */

@Controller
public class InvalidatePromos extends HttpServlet {
	@RequestMapping(value="/promos/invalidate", method=RequestMethod.POST)	
	public @ResponseBody PromoModel showBits(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String id)	
											throws IOException, ServletException {		    	    	
    	 try{
         	
             OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
             OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
             OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
             
             String promos_id = id;
             
             if (promos_id != null) {
            	 String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
            	 return PromoModel.invalidatePromo(promos_id, users_id);
             }             
         } catch (Exception e){
             OAuthProvider.handleException(e, request, response, false);
         }
         return PromoModel.error();
    	
    }
	private static final long serialVersionUID = 1L;
}