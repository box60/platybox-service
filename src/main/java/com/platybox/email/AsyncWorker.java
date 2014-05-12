package com.platybox.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.platybox.email.EmailManager;
import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.users.UserModel;
import com.platybox.utils.TokenGenerator;

/**
 * An asynchronous email worker
 */
@Component("asyncWorker")
public class AsyncWorker implements Worker {
	
	@Autowired 
	private EmailManager emailManager;

	public void setEmailManager(EmailManager emailManager) {
		this.emailManager = emailManager;
	}
	
	@Async
	public void work(String id, String email, String type, String users_id, String promos_id) {
            		
            if (type.equalsIgnoreCase("register")){
            	UserModel user = UserModel.selectUser(users_id);
            	emailManager.sendWelcomeEmail(email, user);
            	EmailsQueueModel.deleteEmailFromQueue(id);
            } else if (type.equalsIgnoreCase("signup")) {
            	String token = TokenGenerator.generateToken(email + users_id);
				UserModel user = UserModel.insertTokenSignup(users_id, token);
				String link = "http://api.platybox.com/1/s?i="+users_id+"&t="+token;
            	emailManager.sendSignupEmail(email, user, link);
            	EmailsQueueModel.deleteEmailFromQueue(id);
            } else if (type.equalsIgnoreCase("recover")) {
            	String token = TokenGenerator.generateToken(email + users_id);
    			UserModel user = UserModel.insertTokenRecover(users_id, token);
    			String link = "http://api.platybox.com/1/r?i="+users_id+"&t="+token;
            	emailManager.sendPasswordRecoveryEmail(email, user, link);
            	EmailsQueueModel.deleteEmailFromQueue(id);
            } else if (type.equalsIgnoreCase("coupon")) {
    			UserModel user = UserModel.selectUser(users_id);
    			PromoModel promo = PromoModel.selectPromo(promos_id, users_id);
    			emailManager.sendCouponEmail(email, user, promo);
            	EmailsQueueModel.deleteEmailFromQueue(id);
            }
      
   }
}
