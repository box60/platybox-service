package com.platybox.email;

import com.platybox.models.promos.PromoModel;
import com.platybox.models.users.UserModel;

public interface EmailManager {
	void sendPasswordRecoveryEmail(String email, UserModel user, String link);		
	void sendWelcomeEmail(String email, UserModel user);
	void sendSignupEmail(String email, UserModel user, String link);
    void sendCouponEmail(String email, UserModel user, PromoModel promo);

}
