package com.platybox.models.users;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public final class UserScoresPlacesModel {

	/*
	 * Constructor
	 */
	//private HashMap<String,String> score;
	String points;
	String promos;
	String coins;
	
	/*Declare a default error.*/
	private static final UserScoresPlacesModel AVAILABLE_INSTANCE = new UserScoresPlacesModel(
			null, null, null);


	public UserScoresPlacesModel(String points, String promos, String coins) {		
		this.points = points;
		this.promos = promos;
		this.coins = coins;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setPoints (String points) {
		AVAILABLE_INSTANCE.points = points;
	}

	public String getPoints() {
		return points;
	}
	
	public void setPromos (String promos) {
		AVAILABLE_INSTANCE.promos = promos;
	}

	public String getPromos() {
		return promos;
	}
	
	public void setCoins (String coins) {
		AVAILABLE_INSTANCE.coins = coins;
	}

	public String getCoins() {
		return coins;
	}
		
	/*
	 * Methods.
	 */
		
	public static UserScoresPlacesModel selectScore (String users_id, String places_id) {
					
		ArrayList<HashMap<String,String>> scoresQuery
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM users_scores_places WHERE " +
				"users_id='"+users_id+"' AND " +
				"places_id='"+places_id+"'";				
		scoresQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (scoresQuery.isEmpty() == false) {
			String points = scoresQuery.get(0).get("points").toString();
			String promos = scoresQuery.get(0).get("promos").toString();
			String coins = scoresQuery.get(0).get("coins").toString();
			return new UserScoresPlacesModel(points, promos, coins);
		}
		
		return error();				
	}

	/**
	 * Log scores place checkin, or create if doesn't exist. As this is a log, don't return anything
	 * We use this method in the checkin controller as part of a procedure to update all scores.
	 * 
	 * @param users_id
	 * @param bits_id
	 */
	public static void addOnePointAndCoin (String users_id, String bits_id) {
	
			//log scores place checkins
			String strQuery = "SELECT id FROM users_scores_places WHERE " +
					"users_id="+users_id+" AND " +
					"places_id=(SELECT places_id FROM bits WHERE bits.id='"+bits_id+"')";
			String strUpdate;			
			
			if (DatabaseUtils.executeQuery(strQuery).size() >= 1) {
				strUpdate = "UPDATE users_scores_places " +
						"SET points=points+1, coins=coins+1 WHERE " +
						"users_id="+users_id+" AND " +
						"places_id =(SELECT places_id FROM bits WHERE bits.id='"+bits_id+"')";
			} else {
				strUpdate = "INSERT INTO users_scores_places (points, coins, users_id, places_id) " +
						"VALUES (1, 1,'"+users_id+"', (SELECT places_id FROM bits WHERE bits.id='"+bits_id+"'))";
			}
			DatabaseUtils.executeUpdate(strUpdate);			
	}
	
	public static UserScoresPlacesModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
