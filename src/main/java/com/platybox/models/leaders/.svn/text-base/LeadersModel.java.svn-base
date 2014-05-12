package com.platybox.models.leaders;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.models.users.UserModel;
import com.platybox.utils.database.DatabaseUtils;

public class LeadersModel {
		
	/*
	 * Constructor
	 */
	
	private ArrayList<UserModel> leaders;

	/*Declare a default error.*/
	private static final LeadersModel AVAILABLE_INSTANCE = new LeadersModel (
			null
			/*
			new ArrayList<UserModel> () {private static final long serialVersionUID = 1L;{
				add (UserModel.error());
			}}
			*/
	);
	
	public LeadersModel(ArrayList<UserModel> leaders) {		
		this.leaders = leaders;
		userColumns = "users.id, users_scores.promos, users_scores.quests, users_scores.points, users_scores.coins";
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setLeaders (ArrayList<UserModel> leaders) {
		AVAILABLE_INSTANCE.leaders = leaders;
	}

	public ArrayList<UserModel> getLeaders() {
		return leaders;
	}
	
	/*
	 * Methods.
	 */
	
	private static String userColumns;
	
	public static LeadersModel searchPointLeaders () { //long geolong, long geolat 
		
		ArrayList<UserModel> users
				= new ArrayList<UserModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT " + userColumns + " from users " +			
			"LEFT JOIN users_scores ON users_scores.users_id=users.id " +
			"WHERE users_scores.promos > 0  " +
			"ORDER BY users_scores.points DESC LIMIT 10";				
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				users.add(UserModel.selectUser(id.get("id")));				
			}
			return new LeadersModel(users);			
		} else
			return error();
	}
	
	public static LeadersModel searchPromosLeaders () { //long geolong, long geolat 
		
		ArrayList<UserModel> users
				= new ArrayList<UserModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT " + userColumns + " from users " +			
			"LEFT JOIN users_scores ON users_scores.users_id=users.id " +
			"WHERE users_scores.promos > 0  " +
			"ORDER BY users_scores.promos DESC LIMIT 10";				
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				users.add(UserModel.selectUser(id.get("id")));				
			}
			return new LeadersModel(users);			
		} else
			return error();
	}
	
	public static LeadersModel searchQuestsLeaders () { //long geolong, long geolat 
		
		ArrayList<UserModel> users
				= new ArrayList<UserModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT " + userColumns + " from users " +			
			"LEFT JOIN users_scores ON users_scores.users_id=users.id " +
			"WHERE users_scores.quests > 0  " +
			"ORDER BY users_scores.quests DESC LIMIT 10";				
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				users.add(UserModel.selectUser(id.get("id")));				
			}
			return new LeadersModel(users);			
		} else
			return error();
	}

	public static LeadersModel error() {
		return AVAILABLE_INSTANCE;
	}
	
	
}
