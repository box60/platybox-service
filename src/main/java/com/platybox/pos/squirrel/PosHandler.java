package com.platybox.pos.squirrel;

import com.platybox.models.pos.PosModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.promos.PromosPosSquirrelModel;
import com.platybox.sms.TropoSMSConnector;
import com.platybox.utils.database.DatabaseUtils;

/*
 * This class is a thread as we must asure that messages are submitted, thus we will retry.
 * */

public class PosHandler extends Thread {

	PosModel placePos;
	PromoModel promo;
	String username;
	String users_id;
	String places_id;

	public PosHandler (String places_id, String users_id, PromoModel promo) {
		this.places_id = places_id;
		this.placePos = PosModel.selectPos(places_id);
		this.promo = promo;
		this.users_id = users_id;
		this.username = DatabaseUtils.getUsername(Integer.parseInt(users_id));
	}

	public void run() {
		String type = this.placePos.getPos_type();
		String address = this.placePos.getPosaddress();
		if (type.equalsIgnoreCase("sms") && address!=null){
			String numberToDial = this.placePos.getPosaddress();
			if (numberToDial!=null && promo.getPromo().get("processing").equalsIgnoreCase("0")){
				sendTextMessage (numberToDial);
			}
		} else if (type.equalsIgnoreCase("squirrel") && address!=null) {
			String promos_id = promo.getPromo().get("id");
			PromosPosSquirrelModel promoAtPos = PromosPosSquirrelModel.selectPos(promos_id);		
			sendSquirrelOrder (promoAtPos);
		} else {
			//Do nothing.
		}
	}
	
	private void sendSquirrelOrder (PromosPosSquirrelModel promoAtPos) {
		String positem = promoAtPos.getPositem();
		String pospromo = promoAtPos.getPospromo();
		String posbadge = promoAtPos.getPosbadge();
		String posdepartment = promoAtPos.getPosdepartment();
		String postable = promoAtPos.getPostable();
		String posseat = promoAtPos.getPosseat();
		String posaddress = promoAtPos.getPosaddress();
		
		SquirrelConnector squirrelConnector = new SquirrelConnector(positem, pospromo, posbadge, posdepartment, postable, posseat, posaddress);
		squirrelConnector.sendOrder();
		
	}
	
	private void sendTextMessage (String numberToDial) {
		String description = promo.getPromo().get("description");
		String promoId = promo.getPromo().get("id");
		String msg = username + " has purchased promo #" + promoId + ". " + description;
		TropoSMSConnector sms = new TropoSMSConnector(numberToDial, msg);
		Boolean sent = sms.sendSMS();
		if (sent) {
			String promos_id = promo.getPromo().get("id");
			PromoModel.processingPromo(promos_id, users_id);
		}
			
	}
	
}
