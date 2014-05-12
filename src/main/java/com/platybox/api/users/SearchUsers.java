package com.platybox.api.users;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.users.UsersModel;
import com.platybox.provider.core.OAuthProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Example response:
 * {"users":[{"user":{"id":"2","username":"roberto","coins":"99999971","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"30","points":"322","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},{"user":{"id":"3","username":"test","coins":"0","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"0","points":"0","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},{"user":{"id":"4","username":"omg","coins":"0","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"0","points":"0","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},{"user":{"id":"5","username":"fuck","coins":"0","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"0","points":"0","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},{"user":{"id":"6","username":"magiclab","coins":"0","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"0","points":"0","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},{"user":{"id":"1","username":"platybox","coins":null,"description":"With our powers combined, we are platypus.","name":"Platybox Client","experience":null,"points":null,"photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}}]}
 */

@Controller
public class SearchUsers extends HttpServlet {
	
    /**
     * Searches for matches on username, email, profile descriptions, profile or profile names,   
     * Search is limited to return 10 user objects for now.
     * 
     *  parameter: q: the query to run against the search 
     */
	@RequestMapping(value="/users/search", method=RequestMethod.POST)	
	public @ResponseBody UsersModel showBits(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String q)	
											throws IOException, ServletException {    	
      try{        	
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
            
            String query = q;
            
            if (query!= null) {            
            	return UsersModel.searchUsers(query);
            }
            

        } catch (Exception e){
            OAuthProvider.handleException(e, request, response, false);
        }
        return UsersModel.error();
    }     
    private static final long serialVersionUID = 1L;    
}
