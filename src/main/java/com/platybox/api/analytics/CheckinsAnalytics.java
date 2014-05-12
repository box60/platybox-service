package com.platybox.api.analytics;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platybox.models.analytics.TwoWeekCheckinsModel;
import com.platybox.models.bits.BitModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.BitsUtils;
import com.platybox.utils.database.DatabaseUtils;


/*
 * Will retreive and construct a DataTable object for google charts 
 * 
 */

@Controller
public class CheckinsAnalytics extends HttpServlet {
	
	@RequestMapping(value="/analytics/checkins", method=RequestMethod.GET)	
	public @ResponseBody TwoWeekCheckinsModel  showCheckins (HttpServletRequest request,
											HttpServletResponse response,
											@RequestParam String places_id)	
											throws IOException, ServletException {
				
		try{
            OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
            OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
            OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
    	    return TwoWeekCheckinsModel.showTwoWeekCheckins(places_id);            	            
        }catch (Exception e){
             OAuthProvider.handleException(e, request, response, false);
        }

        return TwoWeekCheckinsModel.error();
		
    }
    private static final long serialVersionUID = 1L;
}