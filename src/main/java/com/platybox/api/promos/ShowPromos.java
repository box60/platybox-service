package com.platybox.api.promos;

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
import com.platybox.models.promos.PromoModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.CreateBitQR;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Example response:
 * {"bit":{"bit":{"id":"62","qr_image_url":"http://localhost/1/resources/images/qr/62.gif","places_id":"2","description":"Changed description","name":"A pink promo modified","bits_types_id":"9","type":"promos"}},"promo":{"id":"25","valid":"1","price":"5","places_id":"2","description":"Changed description","name":"A pink promo modified","users_id":"1","available":"1"},"place":{"place":{"id":"2","geolong":"-123.184674","name":"Box 60 Inc.","geolat":"49.263495"},"mastery":{"promos":"0","checkins":"16"}}}
 */

@Controller
public class ShowPromos extends HttpServlet {

	@RequestMapping(value="/promos/show", method=RequestMethod.POST)	
	public @ResponseBody PromoModel showPromo(	HttpServletRequest request,
											HttpServletResponse response,
											String id,
											String bits_id)												
											throws IOException, ServletException {
    	 try{
             OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
             OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
             OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
             String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
             if (id != null && bits_id == null) {
            	 String promos_id = id;
            	 return PromoModel.selectPromo(promos_id, users_id);            	 
             } else if ( bits_id != null && id == null) {
            	 return PromoModel.selectPromoByBit(bits_id, users_id);
             }
         } catch (Exception e){
             OAuthProvider.handleException(e, request, response, false);
         }
         return PromoModel.error();
    }
    private static final long serialVersionUID = 1L;
}