package com.platybox.models.places;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class PlaceProfileModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> place;
	private HashMap<String,String> ratings;
		
	/*Declare a default error.*/
	private static final PlaceProfileModel AVAILABLE_INSTANCE = new PlaceProfileModel(
			null, null);

	public PlaceProfileModel(HashMap<String,String> place, HashMap<String,String> ratings) {		
		this.place = place;		
		this.ratings = ratings;
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
	

	public void setRatings(HashMap<String,String> ratings) {
		AVAILABLE_INSTANCE.ratings = ratings;
	}

	public HashMap<String,String> getRatings() {
		return ratings;
	}
	
	
	/*
	 * Methods.
	 */
	

	public static PlaceProfileModel selectPlace (String places_id) { //, String users_id
					
		ArrayList<HashMap<String,String>> placeQuery
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM places WHERE id=" + places_id;
		placeQuery = DatabaseUtils.executeQuery(strQuery);
		
		
		if (placeQuery.isEmpty() == false ){
			HashMap<String,String> ratings = computeRatings(places_id);
			return new PlaceProfileModel(placeQuery.get(0), ratings);
		} else
			return error();
	}

	public static PlaceProfileModel selectPlaceBiBitId (String bits_id) { //, String users_id
		
		ArrayList<HashMap<String,String>> placeQuery
				= new ArrayList<HashMap<String,String>>();

		String strQuery = "SELECT places.* FROM places LEFT JOIN bits ON bits.places_id=places.id WHERE bits.id="+bits_id;
		placeQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (placeQuery.isEmpty() == false ){
			HashMap<String,String> ratings = computeRatings(placeQuery.get(0).get("id"));
			return new PlaceProfileModel(placeQuery.get(0), ratings);
		} else
			return error();
	}
	
	
	private static HashMap<String,String> computeRatings (String places_id) {
		HashMap <String,String> ratings
				= new HashMap<String,String>(); 
				 
		ArrayList<HashMap<String,String>> queryResults
				= new ArrayList<HashMap<String,String>>();
		//Scans
		String strQuery = "SELECT COUNT(*) FROM bits_scanned INNER JOIN bits ON bits.id=bits_scanned.bits_id WHERE bits.places_id="+places_id;
		String scans = DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)");
		
		//registrations at this place
		strQuery = "SELECT COUNT(*) FROM bits_registrations INNER JOIN bits ON bits.id=bits_registrations.bits_id WHERE bits.places_id="+places_id;
		String registrations = DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)");
		
		//checkins
		strQuery = "SELECT COUNT(*) FROM checkins_bits INNER JOIN bits ON bits.id=checkins_bits.bits_id WHERE bits.places_id="+places_id;
		String checkins = DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)");
		
		ratings.put("scans", scans);
		ratings.put("registrations", registrations);
		ratings.put("checkins", checkins);
		
		return ratings;
			
	}	
	
	public static PlaceProfileModel error() {
		return AVAILABLE_INSTANCE;
	}	
	
	
}
