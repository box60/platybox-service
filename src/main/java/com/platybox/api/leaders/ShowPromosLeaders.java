package com.platybox.api.leaders;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.leaders.LeadersModel;
import com.platybox.provider.core.OAuthProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/*
 * Example response:
 * {"user":{"id":"3","username":"test","coins":"0","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"0","points":"0","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}}
 */
@Controller
public class ShowPromosLeaders extends HttpServlet {
	
	@RequestMapping(value="/leaders/promos", method=RequestMethod.POST)
	public @ResponseBody LeadersModel showPromosLeaders( HttpServletRequest request,
											HttpServletResponse response)	
											throws IOException, ServletException {		
		try {
			OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);                                    
            return LeadersModel.searchPromosLeaders();            
        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, false);
        }
        return LeadersModel.error();        
    }
	private static final long serialVersionUID = 1L;
}

