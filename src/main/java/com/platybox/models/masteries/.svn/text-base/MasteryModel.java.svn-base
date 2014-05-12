package com.platybox.models.masteries;

import com.platybox.utils.database.DatabaseUtils;

public final class MasteryModel {

	/*
	 * Constructor
	 */
	
	//private HashMap<String,String> mastery;
	String checkins;
	String promos;
	
	/*Declare a default error.*/
	private static final MasteryModel AVAILABLE_INSTANCE = new MasteryModel(
			null, null
			/*
			new String("empty response"),
			new String("empty response")
			*/
			
			);


	public MasteryModel(String checkins, String promos_consumed) {		
		this.checkins = checkins;
		this.promos = promos_consumed;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setCheckins (String checkins) {
		AVAILABLE_INSTANCE.checkins = checkins;
	}

	public String getCheckins() {
		return checkins;
	}
	
	public void setPromos (String promos) {
		AVAILABLE_INSTANCE.promos = promos;
	}

	public String getPromos() {
		return promos;
	}
		
	/*
	 * Methods.
	 */
	
	/***
	 * 
	 * Computes all historical mastery by scanning the checkins and promos tables.
	 *  
	 * @param users_id The user id to find the mastery model from.
	 * @param places_id The place id that this mastery depends on.
	 * @return A mastery object
	 */
	public static MasteryModel showMastery (String users_id, String places_id) {
		
		String strQuery = "SELECT id FROM checkins_places WHERE " +
				"users_id='"+users_id+"' AND " +
				"places_id='"+places_id+"'";		
		final String checkins = String.valueOf(DatabaseUtils.executeQuery(strQuery).size());
		
		strQuery = "SELECT promos.id FROM promos " +
		"LEFT JOIN promos_statuses ON promos_statuses.promos_id=promos.id WHERE " +
		"users_id='"+users_id+"' AND " +
		"places_id='"+places_id+"' AND " +
		"available=0";		
		final String promos = String.valueOf(DatabaseUtils.executeQuery(strQuery).size());
		
		return new MasteryModel(checkins, promos);		
		}

	public static MasteryModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
