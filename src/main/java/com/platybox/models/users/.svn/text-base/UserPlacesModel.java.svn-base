package com.platybox.models.users;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class UserPlacesModel {
	
	/*
	 * Constructor
	 */
	
	ArrayList<HashMap<String,String>> places; 
	
	/*Declare a default error.*/
	private static final UserPlacesModel AVAILABLE_INSTANCE = new UserPlacesModel(
			null);

	public UserPlacesModel(ArrayList<HashMap<String,String>> places) {		
		this.places = places;		
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setPlaces (ArrayList<HashMap<String,String>>places) {
		AVAILABLE_INSTANCE.places = places;
	}
	public ArrayList<HashMap<String,String>> getPlaces() {
		return places;
	}
		
	/*
	 * Methods.
	 */

	public static UserPlacesModel searchPlacesId (String users_id) {
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>> ();		
		String strQuery = "SELECT places_id FROM users_places WHERE users_id="+users_id;
		data = DatabaseUtils.executeQuery(strQuery);
		return new UserPlacesModel(data);
	}

	public static void insertPlaceId (String users_id, String places_id) {		
		String strUpdate = "INSERT INTO users_places (users_id, places_id) VALUES " +
				"("+users_id+","+places_id+")";
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void removePlaceId (String users_id, String places_id) {		
		String strUpdate = "DELETE FROM users_places WHERE users_id="+users_id+" AND places_id="+places_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	/**
	 * Searches for a place being bound (owned) by a user. Returns the record id if true, or null if false.
	 * @param places_id
	 * @param users_id
	 * @return The row id if true, null if false.
	 */
	public static String placeIsBoundToUser (String places_id, String users_id) {
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>(); 
		String strQuery = "SELECT id from users_places WHERE users_id="+users_id+" AND places_id="+places_id;
		data = DatabaseUtils.executeQuery(strQuery);
		if (data.isEmpty()==false)
			return data.get(0).get("id");
		else
			return null;
	}	
	
	public static UserPlacesModel error() {
		return AVAILABLE_INSTANCE;
	}		
	
}
