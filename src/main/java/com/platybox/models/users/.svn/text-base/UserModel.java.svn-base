package com.platybox.models.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.platybox.models.places.PlaceModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.promos.PromosModel;
import com.platybox.models.weeklypoints.WeeklyPointsModel;
import com.platybox.utils.database.DatabaseUtils;

//TODO: both points and profile should have their own object
/* 
 * This  model is for the api. We have modeled as a single object including some profile information and points.
 * We will keep points as part of the profile and not as a separate model object, because points were
 * modeled as part of a user.
 */

public class UserModel {
		
	/*
	 * Constructor
	 */
	
	private HashMap<String,String> user;
	private HashMap<String,String> profile;
	private HashMap<String,String> scores;
	private ArrayList<PlaceModel> places; //places owned by this person.
	private ArrayList<PromoModel> promos; //promos owned by this person.
	private WeeklyPointsModel weekly_scores; //TODO: this needs to go inside it's own model. CREATE a scores model.
	
	/*Declare a default error.*/
	private static final UserModel AVAILABLE_INSTANCE = new UserModel(
			null, null, null, null, null, null);
	
	public UserModel(HashMap<String,String> user, HashMap<String,String> profile, HashMap<String,String> scores, 
			ArrayList<PlaceModel> places, ArrayList<PromoModel> promos, WeeklyPointsModel weekly_scores) {		
		this.user = user;
		this.profile = profile;
		this.scores = scores;
		this.places = places;
		this.promos = promos;
		this.weekly_scores = weekly_scores;
		aesKey = "xE7CF9Jkl2eZiLE8f1VFOCEqkUDSeW0";
		//columns that define a user profile
		userColumns = "users.id, users.active, users.verified, users.username, users.email, users.phone, " +				
				"users_types.type";
	}
	
	/*
	 * Getters and setters.
	 */

	public void setUser(HashMap<String,String> user) {
		AVAILABLE_INSTANCE.user = user;
	}

	public HashMap<String,String> getUser() {
		return user;
	}

	public void setProfile(HashMap<String,String> profile) {
		AVAILABLE_INSTANCE.profile = profile;
	}

	public HashMap<String,String> getProfile() {
		return profile;
	}
	
	public void setScores (HashMap<String,String> scores) {
		AVAILABLE_INSTANCE.scores = scores;
	}
	public HashMap<String,String> getScores () {
		return scores;
	}		
	public void setPlaces (	ArrayList<PlaceModel> places) {
		AVAILABLE_INSTANCE.places = places;
	}

	public ArrayList<PlaceModel> getPlaces () {
		return places;
	}
	public void setPromos (	ArrayList<PromoModel> promos) {
		AVAILABLE_INSTANCE.promos = promos;
	}

	public ArrayList<PromoModel> getPromos () {
		return promos;
	}
	
	public void setWeekly_scores(WeeklyPointsModel weekly_scores) {
		AVAILABLE_INSTANCE.weekly_scores = weekly_scores;
	}

	public WeeklyPointsModel getWeekly_scores() {
		return weekly_scores;
	}	
	
		
	/*
	 * Methods.
	 */
	private static String aesKey;
	private static String userColumns;

	public static UserModel selectUser (String users_id) {
		
		ArrayList<HashMap<String,String>> userQuery
							= new ArrayList<HashMap<String,String>>();
		HashMap<String,String> user 
							= new HashMap<String,String>();
		ArrayList<PlaceModel> places
							= new ArrayList<PlaceModel>();
		
		String strQuery = "SELECT " + userColumns + " " +
			"FROM users " +
			"LEFT JOIN users_types ON users_types.id=users.users_types_id " +
			"WHERE users.id="+String.valueOf(users_id);		
		userQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (userQuery.isEmpty() == false ){
			user = userQuery.get(0);
			
			HashMap<String,String> profile = UserProfileModel.selectProfile(users_id).getProfile();
			HashMap<String,String> scores = UserScoresModel.selectScore(users_id).getScores();
			ArrayList <HashMap<String,String>> placesQuery = UserPlacesModel.searchPlacesId(users_id).getPlaces();
			ArrayList<PromoModel> promos = PromosModel.searchUserOwnedPromos(users_id).getPromos();
			WeeklyPointsModel weekly_scores = WeeklyPointsModel.showPoints(users_id);
			
			if (placesQuery.isEmpty() == false ){				
				for (HashMap<String,String> id : placesQuery) {
					String places_id = id.get("places_id");
					places.add(PlaceModel.selectPlace(places_id, users_id));				
				}
				return new UserModel(user, profile, scores, places, promos, weekly_scores);			
			} else
				return new UserModel(user, profile, scores, null, promos, weekly_scores);			
		} else {
			return error();
		}		
	}
	
	public static UserModel updateUser (String users_id,
									HashMap<String,String> values ) {

		for (Map.Entry<String, String> entry : values.entrySet()) {			
			if (entry.getValue()!=null){
				String strUpdate = "UPDATE users SET "+entry.getKey()+"='"+entry.getValue()+
									"' WHERE id="+String.valueOf(users_id);
				DatabaseUtils.executeUpdate(strUpdate);				
			}					
		}
		return selectUser(users_id);
	}
	
	public static void setName (String users_id, String name) {
		String strUpdate = "UPDATE users_profiles  SET name='"+name+"' WHERE users_id="+users_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void setActive (String users_id) {
		String strUpdate = "UPDATE users SET active=1 WHERE id="+users_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void setInactive (String users_id) {
		String strUpdate = "UPDATE users SET active=0 WHERE id="+users_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	public static void setVerified (String users_id) {
		String strUpdate = "UPDATE users SET verified=1 WHERE id="+users_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	public static void setUnverified (String users_id) {
		String strUpdate = "UPDATE users SET verified=0 WHERE id="+users_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	public static void setPhone (String users_id, String phone) {
		String strUpdate = "UPDATE users SET phone='"+phone+"' WHERE id="+users_id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	
	/**
	 * Select a phone of a user 
	 * @param users_id
	 * @return The phone or null if it doesn't have a phone
	 */
	public static String selectPhone (String users_id) {
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>(); 
		String strQuery = "SELECT phone FROM users WHERE id="+users_id;		
		data = DatabaseUtils.executeQuery(strQuery);
		String phone = data.get(0).get("phone"); 
		return phone; 

	}
	
	
	
	/*Tokens signup*/
			
	public static UserModel insertTokenSignup(String users_id, String token) {
		if ( users_id!=null && token!=null) {
			
			//generate a brand new token.
			String strUpdate="DELETE FROM users_tokens_signup " +
			"WHERE users_id="+users_id+"";
			DatabaseUtils.executeUpdate(strUpdate);					
			
			strUpdate="INSERT INTO users_tokens_signup (users_id, token) " +
			"VALUES ("+users_id+",'"+token+"')";
			DatabaseUtils.executeUpdate(strUpdate);
		}
		return selectUser(users_id);
	}
	
	public static UserModel selectTokenSignup(String users_id, String token) {
		if ( users_id!=null && token!=null) {
			String strQuery="SELECT * FROM users_tokens_signup " +
			"WHERE users_id="+users_id+" AND token='"+token+"'";
			ArrayList<HashMap<String,String>> data = DatabaseUtils.executeQuery(strQuery);
			if (!data.isEmpty())
				return selectUser(users_id);
			else 
				return error();
		}
		return null;
	}
	
	public static UserModel deleteTokenSignup(String users_id, String token) {
		if ( users_id!=null && token!=null) {
			String strUpdate="DELETE FROM users_tokens_signup " +
			"WHERE users_id="+users_id+" AND token='"+token+"'";
			DatabaseUtils.executeUpdate(strUpdate);		
		}
		return selectUser(users_id);
	}
		
	/*Tokens recover*/
	public static UserModel insertTokenRecover(String users_id, String token) {
		if ( users_id!=null && token!=null) {
			
			//generate a brand new token.
			String strUpdate="DELETE FROM users_tokens_recover " +
			"WHERE users_id="+users_id+"";
			DatabaseUtils.executeUpdate(strUpdate);					
			
			strUpdate="INSERT INTO users_tokens_recover (users_id, token) " +
			"VALUES ("+users_id+",'"+token+"')";
			DatabaseUtils.executeUpdate(strUpdate);
		}
		return selectUser(users_id);
	}
	
	public static UserModel selectTokenRecover(String users_id, String token) {
		if ( users_id!=null && token!=null) {
			String strQuery="SELECT * FROM users_tokens_recover " +
			"WHERE users_id="+users_id+" AND token='"+token+"'";
			ArrayList<HashMap<String,String>> data = DatabaseUtils.executeQuery(strQuery);
			if (!data.isEmpty())
				return selectUser(users_id);
			else 
				return error();
		}
		return null;
	}
	
	public static UserModel deleteTokenRecover(String users_id, String token) {
		if ( users_id!=null && token!=null) {
			String strUpdate="DELETE FROM users_tokens_recover " +
			"WHERE users_id="+users_id+" AND token='"+token+"'";
			DatabaseUtils.executeUpdate(strUpdate);		
		}
		return selectUser(users_id);
	}
	
	public static UserModel updatePassword (String users_id, String password) {		
		String strUpdate = "UPDATE users SET password=AES_ENCRYPT('"+password+"','"+aesKey+"') "+
		"WHERE id="+String.valueOf(users_id);
		DatabaseUtils.executeUpdate(strUpdate);			
		return selectUser(users_id);		
	}	
	
	/**
	 * A very special model case
	 * It creates a basic user and a profile, it initiates all user related sub tables.
	 * Attention!!! this should be called only on registering a user! 
	 * Since we're creating a profile, generating a bit and other stuff we will only return the user id
	 * 
	 * @return the newly inserted user
	 */	
	public static UserModel insertUser (String username,
									String email,
									String phone,
									String password,
									String users_types_id) {
		if ( username!=null && email !=null && password !=null) {
			String strUpdate="INSERT INTO users (username,email, phone, password,users_types_id) " +
			"VALUES ('"+username+"',"+email+", "+phone+" , AES_ENCRYPT('"+password+"','"+aesKey+"')," +
					"'"+users_types_id+"')";		
			String users_id = String.valueOf(DatabaseUtils.executeUpdate(strUpdate));
			
			if (users_id.length() > 0) {
				//users_profiles
				strUpdate="INSERT INTO users_profiles (users_id) " +
							"VALUES ('"+users_id+"')";
				 DatabaseUtils.executeUpdate(strUpdate);			
				
				//users_scores
				strUpdate="INSERT INTO users_scores (users_id) " +
							"VALUES ( '"+users_id+"')";
				DatabaseUtils.executeUpdate(strUpdate);
				return selectUser(users_id);
			}
		}
		return error();		
	}
	
	/**
	 * Returns the id of the user if it's registered, or null if it doesn't exist
	 * @param phone
	 * @return the user id if it exists, null if it doesn't exist. 
	 */
	public static String existsByPhone (String phone) {
		String strQuery = "SELECT id FROM users WHERE phone='"+phone+"'";
		ArrayList<HashMap<String,String>> result = DatabaseUtils.executeQuery(strQuery);		
		if (result.isEmpty())
			return null;
		else
			return result.get(0).get("id");
	}
	
	/**
	 * Returns the id of the user if it's registered, or null if it doesn't exist
	 * @param email
	 * @return the user id if it exists, null if it doesn't exist. 
	 */
	public static String existsByEmail (String email) {
		String strQuery = "SELECT id FROM users WHERE email='"+email+"'";
		ArrayList<HashMap<String,String>> result = DatabaseUtils.executeQuery(strQuery);		
		if (result.isEmpty())
			return null;
		else
			return result.get(0).get("id");
	}
	
	/**
	 * Returns the id of the user if it's registered, or null if it doesn't exist
	 * @param username
	 * @return the user id if it exists, null if it doesn't exist. 
	 */
	public static String existsByUsername (String username) {
		String strQuery = "SELECT id FROM users WHERE username='"+username+"'";
		ArrayList<HashMap<String,String>> result = DatabaseUtils.executeQuery(strQuery);		
		if (result.isEmpty())
			return null;
		else
			return result.get(0).get("id");
	}
	
	
	/**
	 * Returns true if the user has activated his/her account, false if it doesn't. 
	 * @param id
	 * @return true if the user has activated his/her account, false if it hasnt
	 */
	public static boolean isActive (String users_id) {
		String strQuery = "SELECT active FROM users WHERE id='"+users_id+"'";
		ArrayList<HashMap<String,String>> result = DatabaseUtils.executeQuery(strQuery);		
		if (result.isEmpty()!=false ) {
			String isActive = result.get(0).get("active");
			if (isActive.equalsIgnoreCase("1"))
				return true;
			else
				return false;						
		}
		return false;			
	}
	
	
	public static UserModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
