package com.platybox.api.checkins;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

import com.platybox.models.checkins.BitCheckinModel;
import com.platybox.models.quests.QuestModel;
import com.platybox.models.users.UserScoresModel;
import com.platybox.models.users.UserScoresPlacesModel;
import com.platybox.provider.core.OAuthProvider;
import com.platybox.utils.CouponsUtils;
import com.platybox.utils.database.DatabaseUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * Checkin into a place using a bit.
 * Example response
 * {"checkin":{"timestamp":"2011-03-09 01:28:51.0","id":"30","users_id":"2","bits_id":"1"},"user":{"user":{"id":"2","username":"roberto","coins":"99999971","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"30","points":"312","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},"score":{"points":"14","experience":"0"}}
 */
@Controller
public class BitCheckins extends HttpServlet {
	
	@RequestMapping(value="/checkins/bit", method=RequestMethod.POST)	
	public @ResponseBody BitCheckinModel showBits(	HttpServletRequest request,
											HttpServletResponse response,
											//required
											@RequestParam String id)	
											throws IOException, ServletException {		
		
		 try{

             OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
             OAuthAccessor accessor = OAuthProvider.getAccessor(requestMessage);
             OAuthProvider.VALIDATOR.validateMessage(requestMessage, accessor);
             
             String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
             String bits_id = id;

             if (BitCheckinModel.userCheckedInToday(bits_id, users_id)) //check if user has checked in before
            	 return BitCheckinModel.error();
             else {            	 
            	 CouponsUtils.giveCheckinCoupon(users_id, bits_id); /*Search and award Checkin coupons*/
            	 QuestModel.checkinQuest(bits_id, users_id); //Check into the quest and marked completed
            	 UserScoresPlacesModel.addOnePointAndCoin(users_id, bits_id); //Add one point and one coin in place for checkin
            	 UserScoresModel.addOnePoint(users_id);//log user scores across everything (platybox scores)
            	 return BitCheckinModel.insertBitCheckin(bits_id, users_id);
             }

		 } catch (Exception e){
			 OAuthProvider.handleException(e, request, response, false);
         }
    	return BitCheckinModel.error();
    }
	private static final long serialVersionUID = 1L;    
}