package com.platybox.models.places;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.models.places.PlaceModel;
import com.platybox.utils.database.DatabaseUtils;

public final class PlacesModel {

	/*
	 * Constructor
	 */
		
	private ArrayList<PlaceModel> places;

	/*Declare a default error.*/
	private static final PlacesModel AVAILABLE_INSTANCE = new PlacesModel (
			null
			/*
			new ArrayList<PlaceModel> () {private static final long serialVersionUID = 1L;{
				add (PlaceModel.error()); 
			}}
			*/
	);
	
	public PlacesModel(ArrayList<PlaceModel> places) {		
		this.places = places;
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setPlaces (ArrayList<PlaceModel> places) {
		AVAILABLE_INSTANCE.places = places;
	}

	public ArrayList<PlaceModel> getPlaces() {
		return places;
	}
	
	/*
	 * Methods.
	 */			
	public static PlacesModel searchPlaces (String users_id) { //long geolong, long geolat 
		
		ArrayList<PlaceModel> places
				= new ArrayList<PlaceModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT id FROM places";
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				places.add(PlaceModel.selectPlace(id.get("id"), users_id));				
			}
			return new PlacesModel(places);			
		} else
			return error();
	}
	
	public static PlacesModel error() {
		return AVAILABLE_INSTANCE;
	}
}