package com.platybox.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.platybox.models.coupons.CouponsModel;
import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.places.PlaceModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.promos.PromosModel;
import com.platybox.models.quests.QuestModel;
import com.platybox.models.users.UserModel;
import com.platybox.sms.SMSUtils;

public class CouponsUtils {
	
	public CouponsUtils () {
	}
	
	public static final void giveRegisterCoupon(String users_id) {
		ArrayList<HashMap<String,String>> coupons = CouponsModel.findCoupons();
		if (coupons != null){ //if there is a coupon available
			
			Iterator<HashMap<String, String>> itr = coupons.iterator();
			while (itr.hasNext()) {
				HashMap<String,String> coupon = itr.next();
				
				if (coupon.get("type").equalsIgnoreCase("register") 
					 && CouponsModel.couponHasNotBeenPurchased(users_id, coupon.get("id"))) {	
					String id = coupon.get("id");
					String places_id  = coupon.get("places_id");
					String price  = coupon.get("price");
					String name  = coupon.get("name");
					String description  = coupon.get("description");
					String expires  = coupon.get("expires");
					
					PromoModel promo = PromosUtils.createPromoGetInserted(users_id, name, description, expires, price, places_id, null, null, null, null);
					String promos_id = promo.getPromo().get("id");
					PromoModel.claimCouponPromo(promos_id, users_id);
					CouponsModel.consumeCoupon(id);
					CouponsModel.logCouponPurchased(users_id, id);
					EmailsQueueModel.addPromoEmailToQueue(users_id, "coupon", promos_id);
					String phone = UserModel.selectPhone(users_id);
					if (phone!=null) //TODO: this should have its own class
						SMSUtils.sendSMS(phone, "PROMO ["+promos_id +"] "+name +". Show this at "+PlaceModel.findPlaceNameByPlacesId(places_id)+" Expires: "+expires);
					break;
				}	
			}
		}
	}
	
	public static final void giveQuestCoupon(String users_id, String quests_id) {
		ArrayList<HashMap<String,String>> coupons = CouponsModel.findCoupons();
		if (coupons != null){ //if there is a coupon available
			 
			Iterator<HashMap<String, String>> itr = coupons.iterator();
			while (itr.hasNext()) {
				HashMap<String,String> coupon = itr.next();
				
				if (coupon.get("type").equalsIgnoreCase("quest") 
						&& coupon.get("quests_id").equalsIgnoreCase(quests_id)
						  && CouponsModel.couponHasNotBeenPurchased(users_id, coupon.get("id"))) {										
					String id = coupon.get("id");
					String places_id  = coupon.get("places_id");
					String price  = coupon.get("price");
					String name  = coupon.get("name");
					String description  = coupon.get("description");
					String expires  = coupon.get("expires");
										
					PromoModel promo = PromosUtils.createPromoGetInserted(users_id, name, description, expires, price, places_id, null, null, null, null);
					String promos_id = promo.getPromo().get("id");
					PromoModel.claimCouponPromo(promos_id, users_id);
					CouponsModel.consumeCoupon(id);
					CouponsModel.logCouponPurchased(users_id, id);
					EmailsQueueModel.addPromoEmailToQueue(users_id, "coupon", promos_id);
					String phone = UserModel.selectPhone(users_id);
					if (phone!=null) //TODO: this should have its own class
						SMSUtils.sendSMS(phone, "PROMO ["+promos_id +"] "+name +". Show this at "+PlaceModel.findPlaceNameByPlacesId(places_id)+" Expires: "+expires);
					break;
				}					
			}	
		}		
	}
	
	public static final void giveCheckinCoupon(String users_id, String bits_id) {
		ArrayList<HashMap<String,String>> coupons = CouponsModel.findCoupons();
		if (coupons != null){ //if there is a coupon available
			
			Iterator<HashMap<String, String>> itr = coupons.iterator();
			while (itr.hasNext()) {
				HashMap<String,String> coupon = itr.next();
				
				if (coupon.get("type").equalsIgnoreCase("checkin")
						&& coupon.get("bits_id").equalsIgnoreCase(bits_id)
						  && CouponsModel.couponHasNotBeenPurchased(users_id, coupon.get("id"))) {										
					String id = coupon.get("id");
					String places_id  = coupon.get("places_id");
					String price  = coupon.get("price");
					String name  = coupon.get("name");
					String description  = coupon.get("description");
					String expires  = coupon.get("expires");
										
					PromoModel promo = PromosUtils.createPromoGetInserted(users_id, name, description, expires, price, places_id, null, null, null, null);
					String promos_id = promo.getPromo().get("id");
					PromoModel.claimCouponPromo(promos_id, users_id);
					CouponsModel.consumeCoupon(id);
					CouponsModel.logCouponPurchased(users_id, id);					
					EmailsQueueModel.addPromoEmailToQueue(users_id, "coupon", promos_id);
					String phone = UserModel.selectPhone(users_id);
					if (phone!=null) //TODO: this should have its own class
						SMSUtils.sendSMS(phone, "PROMO ["+promos_id +"] "+name +". Show this at "+PlaceModel.findPlaceNameByPlacesId(places_id)+" Expires: "+expires);
					break;					
				}	
			}		
		}
	}
}
