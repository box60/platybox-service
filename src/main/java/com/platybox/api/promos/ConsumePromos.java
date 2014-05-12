package com.platybox.api.promos;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;
import com.platybox.models.pos.PosModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.users.UserScoresPlacesModel;
import com.platybox.models.users.UserModel;
import com.platybox.pos.squirrel.PosHandler;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.sms.TropoSMSConnector;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Example response:
 * {"bit":{"bit":{"id":"16","qr_image_url":"http://localhost/1/resources/images/qr/16.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}},"promo":{"id":"10","valid":"1","price":"5","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","users_id":"2","bits_id":"16","available":"0"},"place":{"place":{"id":"1","geolong":"-123.184674","name":"Utopia, Nowhere","geolat":"49.263495"},"mastery":{"checkins":"15","promos":"8"}}}
 */

@Controller
public class ConsumePromos extends HttpServlet {
	
	@RequestMapping(value="/promos/consume", method=RequestMethod.POST)	
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
            	 
            	 
            	 PromoModel promo = PromoModel.selectPromo(promos_id, users_id);
            	 int price = Integer.parseInt(promo.getPromo().get("price"));
            	 int available = Integer.parseInt(promo.getPromo().get("available"));
            	  
            	 String places_id = promo.getPromo().get("places_id");
            	         	 
            	 UserScoresPlacesModel placeScores = UserScoresPlacesModel.selectScore(users_id, places_id);
            	             	 
            	 int scoresPlaceCoins = 0;
            	 
            	 if (placeScores.getPoints()!=null )
            		 scoresPlaceCoins = Integer.parseInt(placeScores.getCoins());

            	 if  (promo.getPromo().get("users_id").equalsIgnoreCase(users_id) && available == 0) {  
            		 return promo;
            	 } else if (scoresPlaceCoins>= price) {
            		 /*Initiate thread to send data to POS*/
            		 PosHandler pos = new PosHandler(places_id, users_id, promo);
            		 pos.start();            		 
	            	 return PromoModel.consumePromo(promos_id, users_id);
            	 }
  		 
             }
         } catch (Exception e){
             OAuthProvider.handleException(e, request, response, false);
         }
    	return PromoModel.error();
    }
    private static final long serialVersionUID = 1L;
    
}