package com.platybox.models.places;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.models.masteries.MasteryModel;
import com.platybox.models.users.UserScoresPlacesModel;
import com.platybox.utils.database.DatabaseUtils;

public final class PlaceModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> place;
	private MasteryModel mastery;
	private UserScoresPlacesModel scores;
	
	/*Declare a default error.*/
	private static final PlaceModel AVAILABLE_INSTANCE = new PlaceModel(
			null, null, null
			/*
		   new HashMap<String,String>(){private static final long serialVersionUID = 1L;{
				put("error", "empty response");
			}}, MasteryModel.error(), PlaceScoresModel.error()
			*/
			);

	public PlaceModel(HashMap<String,String> place, MasteryModel mastery, UserScoresPlacesModel scores) {		
		this.place = place;
		this.mastery = mastery;
		this.scores = scores;
	}
	
	/*
	 * Getters and setters.
	 */

	public void setPlace(HashMap<String,String> place) {
		AVAILABLE_INSTANCE.place = place;
	}

	public HashMap<String,String> getPlace() {
		return place;
	}
	
	public void setMastery (MasteryModel mastery) {
		AVAILABLE_INSTANCE.mastery = mastery;		
	}
	
	public MasteryModel getMastery () {
		return mastery;
	}
	public void setScores (UserScoresPlacesModel scores) {
		AVAILABLE_INSTANCE.scores = scores;
	}
	public UserScoresPlacesModel getScores() {
		return scores;
	}
	
	/*
	 * Methods.
	 */
	
	/**
	 * Select a place by it's id.
	 * @param id The id of the place to select
	 * @return A places model.
	 */
	public static PlaceModel selectPlace (String places_id, String users_id) { //, String users_id
					
		ArrayList<HashMap<String,String>> placeQuery
				= new ArrayList<HashMap<String,String>>();

		String strQuery = "SELECT * FROM places WHERE id=" + places_id;
		placeQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (placeQuery.isEmpty() == false ){
			return new PlaceModel(placeQuery.get(0),
								MasteryModel.showMastery(users_id, places_id),
								UserScoresPlacesModel.selectScore(users_id, places_id));
		} else
			return error();
	}

	public static String findPlaceIdByBit (String bits_id) {
		ArrayList<HashMap<String,String>> query
					= new ArrayList<HashMap<String,String>>();
		String result = null;
		String strQuery = "SELECT places_id FROM bits WHERE bits_types_id=2 AND id="+bits_id;
		query = DatabaseUtils.executeQuery(strQuery);
		if (query.isEmpty() == false ){
			result = query.get(0).get("places_id");
		}		
		return result; 
	}
	public static String findPlaceNameByPlacesId (String places_id) {
		ArrayList<HashMap<String,String>> query
				= new ArrayList<HashMap<String,String>>();
		String result = null;
		String strQuery = "SELECT name FROM places WHERE id="+places_id;
		query = DatabaseUtils.executeQuery(strQuery);
		if (query.isEmpty() == false ){
			result = query.get(0).get("name");
		}		
		return result;
	}
	
	public static String findPlaceTimeZone (String places_id) {
		ArrayList<HashMap<String,String>> query
			= new ArrayList<HashMap<String,String>>();
		String result = null;
		String strQuery = "SELECT timezone FROM places WHERE id="+places_id;
		query = DatabaseUtils.executeQuery(strQuery);
		if (query.isEmpty() == false ){
			result = query.get(0).get("timezone");
		}		
		return result;
	}
	
	
	public static PlaceModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
