package com.platybox.email;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.platybox.models.promos.PromoModel;
import com.platybox.models.users.UserModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlatyboxEmailManager implements EmailManager {

	private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;

    public void setMailSender(JavaMailSender mailSender) {
    	this.mailSender = mailSender;
    }
    public void setVelocityEngine (VelocityEngine velocityEngine) {
    	this.velocityEngine = velocityEngine;
    }

    public void sendPasswordRecoveryEmail(final String email, final UserModel user, final String link) {

    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
    		public void prepare(MimeMessage mimeMessage) throws Exception {	        	
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    			message.setTo(email);
    			message.setFrom("Platybox <info@platybox.com>");
    			mimeMessage.setSubject("Your new password.");
    			Map <String,String> model = new HashMap<String,String>();
    			model.put("link", link);
    			model.put("username", user.getUser().get("username"));
    			model.put("year", getYear());
    			
    			String text = VelocityEngineUtils.mergeTemplateIntoString(
    					velocityEngine, "com/platybox/email/recoverpassword.vm", model);
    			message.setText(text, true);

    		}
    	};
    	this.mailSender.send(preparator);
    }

    public void sendSignupEmail(final String email, final UserModel user, final String link) {
    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
    		public void prepare(MimeMessage mimeMessage) throws Exception {	        	
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    			message.setTo(email);
    			message.setFrom("Platybox <info@platybox.com>");
    			mimeMessage.setSubject("Your Platybox invite.");
    			Map <String,String> model = new HashMap<String,String>();
    			model.put("link", link);
    			model.put("username", user.getUser().get("username"));
    			model.put("year", getYear());

    			String text = VelocityEngineUtils.mergeTemplateIntoString(
    					velocityEngine, "com/platybox/email/signup.vm", model);
    			message.setText(text, true);
    		}
    	};
    	this.mailSender.send(preparator);
    }

    public void sendWelcomeEmail(final String email, final UserModel user) {

    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
    		public void prepare(MimeMessage mimeMessage) throws Exception {	        	
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    			message.setTo(email);
    			message.setFrom("Platybox <info@platybox.com>");
    			mimeMessage.setSubject("Welcome to Platybox.");
    			Map <String,String> model = new HashMap<String,String>();
    			model.put("link", "http://platybox.com");
    			model.put("username", user.getUser().get("username"));
    			model.put("year", getYear());

    			String text = VelocityEngineUtils.mergeTemplateIntoString(
    					velocityEngine, "com/platybox/email/welcome.vm", model);
    			message.setText(text, true);

    		}
    	};
    	this.mailSender.send(preparator);    	
    }

    public void sendCouponEmail(final String email, final UserModel user, final PromoModel promo) {

    	MimeMessagePreparator preparator = new MimeMessagePreparator() {
    		public void prepare(MimeMessage mimeMessage) throws Exception {	        	
    			MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    			message.setTo(email);
    			message.setFrom("Platybox <info@platybox.com>");
    			mimeMessage.setSubject("You've got a Platybox Promo.");
    			Map <String,String> model = new HashMap<String,String>();
    			model.put("platyboxlink", "http://www.platybox.com");
    			model.put("username", user.getUser().get("username"));
    			model.put("promoName", promo.getPromo().get("name"));
    			model.put("promoDescription", promo.getPromo().get("description"));
    			model.put("placeName", promo.getPlace().getPlace().get("name"));
    			model.put("placeAddress", promo.getPlace().getPlace().get("address") );
    			model.put("placePhone", promo.getPlace().getPlace().get("phone") );
    			model.put("maplink", "http://maps.google.com/maps?q="
    					+ promo.getPlace().getPlace().get("geolat")
    					+ ","
    					+ promo.getPlace().getPlace().get("geolong"));
    			model.put("year", getYear());

    			String text = VelocityEngineUtils.mergeTemplateIntoString(
    					velocityEngine, "com/platybox/email/coupon.vm", model);
    			message.setText(text, true);

    		}
    	};
    	this.mailSender.send(preparator);    	
    }
    
    private String getYear () {
    	Calendar cal=Calendar.getInstance();
    	int year =cal.get(Calendar.YEAR);
    	return String.valueOf(year);
    }

}
