package com.platybox.models.promos;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.CharEscaper;
import com.platybox.utils.IcalScheduler;
import com.platybox.utils.database.DatabaseUtils;

public class PromosModel {
	
	/*
	 * Constructor
	 */
		
	private ArrayList<PromoModel> promos;

	/*Declare a default error.*/
	private static final PromosModel AVAILABLE_INSTANCE = new PromosModel (
			null
	);
	
	public PromosModel(ArrayList<PromoModel> promos) {		
		this.promos = promos;
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setPromos (ArrayList<PromoModel> promos) {
		AVAILABLE_INSTANCE.promos = promos;
	}

	public ArrayList<PromoModel> getPromos() {
		return promos;
	}
	
	/*
	 * Search all promos in a place, don't analyze if they are on schedule
	 */		
	public static PromosModel searchAllPlacePromos(String places_id, String users_id) {
		ArrayList<PromoModel> promos
				= new ArrayList<PromoModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
					
		String strQuery = "SELECT promos.id FROM promos " +
				"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
				"WHERE (promos.places_id="+places_id+" AND promos_statuses.valid=true AND promos.expires >= CURRENT_DATE)";
		
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				promos.add( PromoModel.selectPromo(id.get("id"), users_id));				
			}
			return new PromosModel(promos);			
		} else
			return error();
	}
	
	/*
	 * Search all avaialble promos in a place that are avalable, don't analyze if they are
	 * on schedule.
	 */
	public static PromosModel searchAllAvailablePlacePromos (String places_id, String users_id) {
		ArrayList<PromoModel> promos
								= new ArrayList<PromoModel>();		
		ArrayList<HashMap<String,String>> queryResult
								= new ArrayList<HashMap<String,String>>();

		String strQuery = "SELECT promos.id, promos_schedules.* " +		
		"FROM promos LEFT JOIN promos_schedules ON promos_schedules.promos_id=promos.id " +
		"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
		"WHERE (promos.places_id="+places_id+" AND promos_statuses.available=true AND promos.expires >= CURRENT_DATE)";

		queryResult = DatabaseUtils.executeQuery(strQuery);

		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				promos.add( PromoModel.selectPromo(id.get("id"), users_id));				
			}
			return new PromosModel(promos);			
		} else
			return error();		
	}
	
	 /* Search all valid promos owned by user are avalable, don't analyze if they are
	 * on schedule.
	 */
	public static PromosModel searchValidUserPromos (String users_id) {
		ArrayList<PromoModel> promos
								= new ArrayList<PromoModel>();		
		ArrayList<HashMap<String,String>> queryResult
								= new ArrayList<HashMap<String,String>>();

		String strQuery = "SELECT promos.id, promos_schedules.* " +		
		"FROM promos LEFT JOIN promos_schedules ON promos_schedules.promos_id=promos.id " +
		"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
		"WHERE (promos_statuses.users_id="+users_id+" AND promos_statuses.valid=true AND promos.expires >= CURRENT_DATE)";

		queryResult = DatabaseUtils.executeQuery(strQuery);

		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				promos.add( PromoModel.selectPromo(id.get("id"), users_id));		
			}
			return new PromosModel(promos);			
		} else
			return error();		
	}
	
	
	
	/*
	 * Search valid place promos and query if they are on schedule
	 * */
	
	public static PromosModel searchValidPlacePromos (String places_id, String users_id) {
		ArrayList<PromoModel> promos
								= new ArrayList<PromoModel>();		
		ArrayList<HashMap<String,String>> queryResult
								= new ArrayList<HashMap<String,String>>();

		String strQuery = "SELECT promos.id, promos_schedules.* " +		
		"FROM promos LEFT JOIN promos_schedules ON promos_schedules.promos_id=promos.id " +
		"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
		"WHERE (promos.places_id="+places_id+" AND promos_statuses.valid=true AND promos.expires >= CURRENT_DATE)";

		queryResult = DatabaseUtils.executeQuery(strQuery);

		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {
				String dtstart = id.get("dtstart");
				String dtend = id.get("dtend");
				String rrule = id.get("rrule");
				String rdatelist = id.get("rdatelist");
				IcalScheduler scheduler = new IcalScheduler(dtstart, dtend, rrule, rdatelist);				
				if (scheduler.isAvailable())
					promos.add( PromoModel.selectPromo(id.get("id"), users_id));				
			}
			return new PromosModel(promos);
		} else
			return error();
	}
	
	/*
	 * Search for promos that are owned by the user
	 * */
	public static PromosModel searchUserOwnedPromos (String users_id) {
		ArrayList<PromoModel> promos
						= new ArrayList<PromoModel>();		
		ArrayList<HashMap<String,String>> queryResult
						= new ArrayList<HashMap<String,String>>();

		String strQuery = "SELECT promos.id FROM promos " +
				"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
				"WHERE promos.expires >= CURRENT_DATE AND promos_statuses.users_id="+users_id;
		queryResult = DatabaseUtils.executeQuery(strQuery);

		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {
				promos.add( PromoModel.selectPromo(id.get("id"), users_id));				
			}
			return new PromosModel(promos);
		} else
			return error();
	}
	
	/*Search for promos that have been sponsored*/	
	public static PromosModel searchSponsoredPromos () {

		ArrayList<PromoModel> promos
			= new ArrayList<PromoModel>();		
		ArrayList<HashMap<String,String>> queryResult
			= new ArrayList<HashMap<String,String>>();

		String strQuery = "SELECT promos.id FROM promos " +
				"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id " +
				"WHERE promos_statuses.sponsored=true AND promos_statuses.available=true AND promos.expires >= CURRENT_DATE";

		queryResult = DatabaseUtils.executeQuery(strQuery);

		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {
				promos.add( PromoModel.selectPromo(id.get("id"), null));				
			}
			return new PromosModel(promos);
		} else
			return error();
	}
    /**
     * Create promo and return ONLY the promo inserted
     * @param bits_id
     * @param places_id
     * @param price
     * @param name
     * @param description
     * @param users_id
     * @param dtstart
     * @param dtend
     * @param rrule
     * @param rdatelist
     * @return
     */
	public static PromoModel insertPromoGetInserted (String bits_id,
			String places_id,
			String price,
			String name,
			String description,
			String expires,
			String users_id,
			String dtstart,
			String dtend,
			String rrule,
			String rdatelist) {		
		if (bits_id != null) {

			/*Escape for SQL*/			
			if (expires==null)
				expires = "30000101"; //set for 1000 years...			
			if (dtstart!=null)
				dtstart = "'"+dtstart+"'";
			if (dtend!=null)
				dtend= "'"+dtend+"'";
			if (rrule!=null)
				rrule = "'"+rrule+"'";
			if (rdatelist!=null)
				rdatelist = "'"+rdatelist+"'";

			String strUpdate = "INSERT INTO promos (bits_id, places_id, price, name, description, expires) " +
			"VALUES ("+bits_id+","+places_id+" ,"+price+", '"+CharEscaper.forQuery(name)+"' ,'"+CharEscaper.forQuery(description)+"','"+expires+"')";		
			String promos_id = String.valueOf(DatabaseUtils.executeUpdate(strUpdate));

			strUpdate = "INSERT INTO promos_schedules (promos_id, dtstart, dtend, rrule, rdatelist) " +
			"VALUES ("+promos_id+","+dtstart+","+dtend+","+rrule+","+rdatelist+")";
			DatabaseUtils.executeUpdate(strUpdate);

			strUpdate = "INSERT INTO promos_statuses (promos_id, users_id) " +
			"VALUES ("+promos_id+","+users_id+")";
			DatabaseUtils.executeUpdate(strUpdate);

			return PromoModel.selectPromo(promos_id, users_id);
			
		} else
			return PromoModel.error();

	}	
	/**
	 * Returns all promos from a place
	 * @param bits_id
	 * @param places_id
	 * @param price
	 * @param name
	 * @param description
	 * @param users_id
	 * @param dtstart
	 * @param dtend
	 * @param rrule
	 * @param rdatelist
	 * @return
	 */
	
	public static PromosModel insertPromo (String bits_id,
											String places_id,
											String price,
											String name,
											String description,
											String expires,
											String users_id,
											String dtstart,
											String dtend,
											String rrule,
											String rdatelist) {		
		if (bits_id != null) {

			/*Escape for SQL*/
			if (expires!=null)
				expires = "'"+expires+"'";
			if (expires==null)
				expires = "30000101"; //set for 1000 years...
			if (dtstart!=null)
				dtstart = "'"+dtstart+"'";
			if (dtend!=null)
				dtend= "'"+dtend+"'";
			if (rrule!=null)
				rrule = "'"+rrule+"'";
			if (rdatelist!=null)
				rdatelist = "'"+rdatelist+"'";
						
			String strUpdate = "INSERT INTO promos (bits_id, places_id, price, name, description, expires) " +
			"VALUES ("+bits_id+","+places_id+" ,"+price+", '"+CharEscaper.forQuery(name)+"' ,'"+CharEscaper.forQuery(description)+"','"+expires+"')";		
			String promos_id = String.valueOf(DatabaseUtils.executeUpdate(strUpdate));

			strUpdate = "INSERT INTO promos_schedules (promos_id, dtstart, dtend, rrule, rdatelist) " +
			"VALUES ("+promos_id+","+dtstart+","+dtend+","+rrule+","+rdatelist+")";
			DatabaseUtils.executeUpdate(strUpdate);

			strUpdate = "INSERT INTO promos_statuses (promos_id, users_id) " +
			"VALUES ("+promos_id+","+users_id+")";
			DatabaseUtils.executeUpdate(strUpdate);

			return searchAllPlacePromos(places_id, users_id); 

		} else
			return error();

	}
	
	public static PromosModel deletePromo (String promos_id, String users_id) {
		if (promos_id != null) {
			PromoModel promo = PromoModel.selectPromo(promos_id, users_id);
			if (promo!=null) {
				String places_id = promo.getPlace().getPlace().get("id");
				String strUpdate="DELETE FROM promos WHERE id="+promos_id;
				DatabaseUtils.executeUpdate(strUpdate);
				strUpdate="DELETE FROM promos_schedules WHERE promos_id="+promos_id;
				DatabaseUtils.executeUpdate(strUpdate);
				strUpdate="DELETE FROM promos_statuses WHERE promos_id="+promos_id;
				DatabaseUtils.executeUpdate(strUpdate);
				
				return searchAllPlacePromos(places_id, users_id);
			}else
				return error();
		} else
			return error();
	}	
	
	public static PromosModel error() {
		return AVAILABLE_INSTANCE;
	}

}
