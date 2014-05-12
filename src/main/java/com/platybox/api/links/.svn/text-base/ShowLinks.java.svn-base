package com.platybox.api.links;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.links.LinksModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Example response:
 * {"links":[{"link":{"id":"3","link_bits_id":"13","users_id":"2"},"bit":{"bit":{"id":"13","qr_image_url":"http://localhost/1/resources/images/qr/13.gif","places_id":"1","description":"Congratulations, you have got got beer!.Ask your server about this promo.","name":"A pink promo.","bits_types_id":"9","type":"promos"}}}]}
 */

@Controller
public class ShowLinks extends HttpServlet {
   
	@RequestMapping(value="/links/show", method=RequestMethod.POST)	
	public @ResponseBody LinksModel show(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String id) throws IOException, ServletException {
      try{        	
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
                        
            String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
            return LinksModel.selectLinks(users_id);            	    
            
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, false);
        }
        return LinksModel.error();
    }    
    
    private static final long serialVersionUID = 1L;
    
}