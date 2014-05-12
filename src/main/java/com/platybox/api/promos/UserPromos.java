package com.platybox.api.promos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.promos.PromosModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Will search for place promos and only return the ones valid, according to their schedule
 * 
 * Example response:
 * {"promos":[{"bit":{"bit":{"id":"13","qr_image_url":"http://localhost/1/resources/images/qr/13.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"7","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"15","qr_image_url":"http://localhost/1/resources/images/qr/15.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"9","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"17","qr_image_url":"http://localhost/1/resources/images/qr/17.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"11","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"18","qr_image_url":"http://localhost/1/resources/images/qr/18.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"12","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"19","qr_image_url":"http://localhost/1/resources/images/qr/19.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"13","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"20","qr_image_url":"http://localhost/1/resources/images/qr/20.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"14","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"21","qr_image_url":"http://localhost/1/resources/images/qr/21.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"15","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"22","qr_image_url":"http://localhost/1/resources/images/qr/22.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"16","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"23","qr_image_url":"http://localhost/1/resources/images/qr/23.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"17","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"24","qr_image_url":"http://localhost/1/resources/images/qr/24.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"18","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"25","qr_image_url":"http://localhost/1/resources/images/qr/25.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"19","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"26","qr_image_url":"http://localhost/1/resources/images/qr/26.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"20","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"27","qr_image_url":"http://localhost/1/resources/images/qr/27.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"21","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"28","qr_image_url":"http://localhost/1/resources/images/qr/28.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"22","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"29","qr_image_url":"http://localhost/1/resources/images/qr/29.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"23","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}},{"bit":{"bit":{"id":"30","qr_image_url":"http://localhost/1/resources/images/qr/30.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"24","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"1","available":"1"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"promos":"8","checkins":"15"}}}]}
 */
@Controller
public class UserPromos extends HttpServlet {
	
	@RequestMapping(value="/promos/user", method=RequestMethod.POST)	
	public @ResponseBody PromosModel showBits(	HttpServletRequest request,
											HttpServletResponse response,
											String id)	
											throws IOException, ServletException {    	
      try{        	
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
            
            String users_id;
            if (id==null)
            	users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
            else 
            	users_id = id;
                        	
            return PromosModel.searchValidUserPromos(users_id);
            
            
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, false);
        }
        return PromosModel.error();        
    }    
    private static final long serialVersionUID = 1L;
}