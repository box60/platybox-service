package com.platybox.models.promos;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.models.bits.BitModel;
import com.platybox.models.places.PlaceModel;
import com.platybox.utils.CharEscaper;
import com.platybox.utils.database.DatabaseUtils;


public class PromoModel {
	
	/*
	 * Constructor
	 */
	
	private HashMap<String,String> promo;
	private BitModel bit;
	private PlaceModel place;	
		
	/*Declare a default error.*/
	private static final PromoModel AVAILABLE_INSTANCE = new PromoModel(
			null, null, null
			/*
			new HashMap<String,String>(){private static final long serialVersionUID = 1L;{			
				put("error", "empty response");
			}}, BitModel.error(), PlaceModel.error(), UserModel.error()
			*/
			);
	
	public PromoModel(HashMap<String,String> promo, BitModel bit, PlaceModel place) {		
		this.promo = promo;	
		this.bit = bit;
		this.place = place;
		promosColumns = "promos.id, promos.bits_id, promos.places_id, promos.price, promos.name, promos.description, promos.expires, " +
				"promos_schedules.dtstart, promos_schedules.dtend, promos_schedules.rrule, promos_schedules.rdatelist, " +
				"promos_statuses.sponsored, promos_statuses.valid, promos_statuses.processing, " +
				"promos_statuses.available, promos_statuses.users_id";
	}
	
	/*
	 * Getters and setters.
	 */

	public void setPromo(HashMap<String,String> promo) {
		AVAILABLE_INSTANCE.promo = promo;
	}
	public HashMap<String,String> getPromo() {
		return promo;
	}
	public void setBit(BitModel bit) {
		AVAILABLE_INSTANCE.bit= bit;
	}
	public BitModel getBit() {
		return bit;
	}
	public void setPlace (PlaceModel place) {
		AVAILABLE_INSTANCE.place = place;
	}
	public PlaceModel getPlace () {
		return place;
	}
	
	/*
	 * Methods.
	 */
	
	private static String promosColumns;
	
	/*
	 * users_id can be null
	 * */
	public static PromoModel selectPromo (String promos_id, String users_id) {
		
		ArrayList<HashMap<String,String>> promoQuery
			= new ArrayList<HashMap<String,String>>();		
		
		//bits id is not chosen to protect the qr code!
		String strQuery = "SELECT " + promosColumns + " FROM promos " +
				"LEFT JOIN promos_schedules ON promos_schedules.promos_id=promos.id " +
				"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
				"WHERE id="+ promos_id;
		promoQuery = DatabaseUtils.executeQuery(strQuery);		
		
		if (promoQuery.isEmpty() == false ){

			String owners_id = promoQuery.get(0).get("users_id");
			String bits_id = promoQuery.get(0).get("bits_id"); 
			String places_id =  promoQuery.get(0).get("places_id");

			if (users_id==null) //mainly for sponsored links that are searched for without auth
				promoQuery.get(0).remove("bits_id");
			//If the owner is not asking for it remove the bit id
			else if (!users_id.equalsIgnoreCase(owners_id)) {
				promoQuery.get(0).remove("bits_id"); 
			}
			return new PromoModel(promoQuery.get(0),
									BitModel.selectBit(bits_id),
									PlaceModel.selectPlace(places_id, users_id) );			
		} else {
			return error();
		}		
	}
	
	public static PromoModel selectPromoByBit (String bits_id, String users_id) {
		ArrayList<HashMap<String,String>> promoQuery
		= new ArrayList<HashMap<String,String>>();	
		//bits id is not chosen to protect the qr code!
		
		String strQuery = "SELECT " + promosColumns + " FROM promos " +
				"LEFT JOIN promos_schedules ON promos_schedules.promos_id=promos.id " +
				"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
				"WHERE bits_id="+ bits_id;
		promoQuery = DatabaseUtils.executeQuery(strQuery);
		if (promoQuery.isEmpty())
			return error();
		else {
			String places_id =  promoQuery.get(0).get("places_id");		
			return new PromoModel(promoQuery.get(0),
					BitModel.selectBit(bits_id),
					PlaceModel.selectPlace(places_id, users_id) );
		}
	}
	

	
	/**
	 * A special case where we want to consume (i.e. will update scores and consume the promo)
	 * @param id
	 * @param users_id
	 */
	
	public static PromoModel consumePromo (String promos_id, String users_id) {
		
		if (promos_id != null) {
			PromoModel promo = selectPromo(promos_id, users_id);

			int valid = Integer.valueOf(promo.getPromo().get("valid"));
			int available = Integer.valueOf(promo.getPromo().get("available"));
			int price = Integer.valueOf(promo.getPromo().get("price"));
			int places_id = Integer.valueOf(promo.getPromo().get("places_id"));
			
			if (valid == 1 && available == 1) {
				
				String strUpdate = "UPDATE promos_statuses " +
									"SET available=false, processing=true, users_id="+users_id+" " +
									"WHERE promos_id="+promos_id;		
				DatabaseUtils.executeUpdate(strUpdate);		
				
				strUpdate = "UPDATE users_scores_places SET " +
					"coins=coins-"+price+", promos=promos+1 " +
					"WHERE users_id="+users_id+" AND places_id="+places_id;
				DatabaseUtils.executeUpdate(strUpdate);
				
				strUpdate = "UPDATE users_scores SET " +
					"promos=promos+1 WHERE users_id="+users_id;
				DatabaseUtils.executeUpdate(strUpdate);
				
				return selectPromo(promos_id, users_id);
			}
		}
		return error();
	}
	
	/**
	 * An even more special case when we give away a promo for free, part of coupons
	 * @param promos_id
	 * @param users_id
	 * @return
	 */
	public static PromoModel claimCouponPromo (String promos_id, String users_id) {
		
		if (promos_id != null) {
			PromoModel promo = selectPromo(promos_id, users_id);

			int valid = Integer.valueOf(promo.getPromo().get("valid"));
			int available = Integer.valueOf(promo.getPromo().get("available"));
						
			if (valid == 1 && available == 1) {
				
				String strUpdate = "UPDATE promos_statuses " +
									"SET available=false, processing=false, users_id="+users_id+" " +
									"WHERE promos_id="+promos_id;
				DatabaseUtils.executeUpdate(strUpdate);		
				
				//will not update a places scores, as it was a marketing promo
				
				strUpdate = "UPDATE users_scores SET " +
					"promos=promos+1 WHERE users_id="+users_id;
				DatabaseUtils.executeUpdate(strUpdate);
				
				return selectPromo(promos_id, users_id);
			}
		}
		return error();
	}	
	
	
	
	
	
	public static PromoModel invalidatePromo (String promos_id, String users_id) {
		
		if (promos_id != null) {
			String strUpdate = "UPDATE promos_statuses SET valid=false, processing=false " +
								"WHERE promos_id="+promos_id+" AND valid=true";		
			DatabaseUtils.executeUpdate(strUpdate);
			return selectPromo(promos_id, users_id);
		}
		return error();
	}
	
	public static PromoModel processingPromo (String promos_id, String users_id) {
		
		if (promos_id != null) {
			String strUpdate = "UPDATE promos_statuses SET processing=true " +
								"WHERE promos_id="+promos_id+" AND valid=true";		
			DatabaseUtils.executeUpdate(strUpdate);
			return selectPromo(promos_id, users_id);
		}
		return error();
	}
	
	public static PromoModel error() {
		return AVAILABLE_INSTANCE;
	}

}