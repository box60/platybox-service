package com.platybox.models.users;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class UsersModel {
		
	/*
	 * Constructor
	 */
	
	private ArrayList<UserModel> users;

	/*Declare a default error.*/
	private static final UsersModel AVAILABLE_INSTANCE = new UsersModel (
			null
			/*
			new ArrayList<UserModel> () {private static final long serialVersionUID = 1L;{
				add (UserModel.error()); //(PlaceModel.error();
			}}
			*/
	);
	
	public UsersModel(ArrayList<UserModel> users) {		
		this.users = users;
		userColumns = "users.id, users.username, " +
		"users_profiles.name, users_profiles.description, users_profiles.photo, " +
		"users_scores.experience, users_scores.points, users_scores.coins";
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setUsers (ArrayList<UserModel> users) {
		AVAILABLE_INSTANCE.users = users;
	}

	public ArrayList<UserModel> getUsers() {
		return users;
	}
	
	/*
	 * Methods.
	 */
	
	private static String userColumns;
	
	public static UsersModel searchUsers (String query) { //long geolong, long geolat 
		
		ArrayList<UserModel> users
				= new ArrayList<UserModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT " + userColumns + " from users " +
			"LEFT JOIN users_profiles ON users_profiles.users_id=users.id " +
			"LEFT JOIN users_scores ON users_scores.users_id=users.id " +
			"WHERE username like '%"+query+"%' OR " +
			"email like '%"+query+"%' OR " +
			"users_profiles.description like '%"+query+"%' OR " +
			"users_profiles.name like '%"+query+"t%' ORDER BY users_profiles.name ASC LIMIT 100";
				
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				users.add(UserModel.selectUser(id.get("id")));				
			}
			return new UsersModel(users);			
		} else
			return error();
	}
	
	public static UsersModel error() {
		return AVAILABLE_INSTANCE;
	}
	
	
}
