package com.platybox.models.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.platybox.models.places.PlaceModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.promos.PromosModel;
import com.platybox.models.weeklypoints.WeeklyPointsModel;
import com.platybox.utils.database.DatabaseUtils;


public class UserScoresModel {
		
	/*
	 * Constructor
	 */
	
	private HashMap<String,String> scores;
	
	/*Declare a default error.*/
	private static final UserScoresModel AVAILABLE_INSTANCE = new UserScoresModel(
			null);
	
	public UserScoresModel(HashMap<String,String> scores) {		
		this.scores = scores;
	}
	
	/*
	 * Getters and setters.
	 */

	public void setScores (HashMap<String,String> scores) {
		AVAILABLE_INSTANCE.scores = scores;
	}

	public HashMap<String,String> getScores() {
		return scores;
	}
		
	/*
	 * Methods.
	 */

	public static UserScoresModel selectScore (String users_id) {
		
		ArrayList<HashMap<String,String>> query
							= new ArrayList<HashMap<String,String>>();
		HashMap<String,String> scores = new HashMap<String,String>();
		
		String strQuery = "SELECT * FROM users_profiles WHERE users_id="+String.valueOf(users_id);		
		query = DatabaseUtils.executeQuery(strQuery);
		
		if (query.isEmpty() == false ){
			scores = query.get(0);
			return new UserScoresModel(scores);									
		} else {
			return error();
		}
		
	}
	
	public static void addOnePoint (String users_id) { 			
		String strUpdate = "UPDATE users_scores " +
				"SET points=points+1 WHERE users_id="+users_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static UserScoresModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
