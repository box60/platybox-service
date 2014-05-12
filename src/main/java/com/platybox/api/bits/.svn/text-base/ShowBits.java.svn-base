package com.platybox.api.bits;

import java.io.IOException;
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
 * Example response
 * {"bit":{"id":"10","qr_image_url":"http://localhost/1/resources/images/qr/10.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}}
 */
@Controller
public class ShowBits extends HttpServlet {
   
	@RequestMapping(value="/bits/show", method=RequestMethod.POST)	
	public @ResponseBody BitModel showBits(	HttpServletRequest request,
											HttpServletResponse response,
											//Required
											@RequestParam String id)	
											throws IOException, ServletException {		
      try{
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
            
            String bits_id = id;            
            if (bits_id != null ){
            	String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
            	return BitModel.selectBit(bits_id);            	            	
            }                
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, false);
        }
        return BitModel.error();
    }      
    private static final long serialVersionUID = 1L;
}