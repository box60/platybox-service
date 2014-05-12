package com.platybox.models.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.platybox.models.places.PlaceModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.promos.PromosModel;
import com.platybox.models.weeklypoints.WeeklyPointsModel;
import com.platybox.utils.database.DatabaseUtils;


public class UserProfileModel {
		
	/*
	 * Constructor
	 */
	
	private HashMap<String,String> profile;
	
	/*Declare a default error.*/
	private static final UserProfileModel AVAILABLE_INSTANCE = new UserProfileModel(
			null);
	
	public UserProfileModel(HashMap<String,String> profile) {		
		this.profile = profile;
	}
	
	/*
	 * Getters and setters.
	 */

	public void setProfile (HashMap<String,String> profile) {
		AVAILABLE_INSTANCE.profile = profile;
	}

	public HashMap<String,String> getProfile() {
		return profile;
	}
		
	/*
	 * Methods.
	 */

	public static UserProfileModel selectProfile (String users_id) {
		
		ArrayList<HashMap<String,String>> userQuery
							= new ArrayList<HashMap<String,String>>();
		HashMap<String,String> profile 
							= new HashMap<String,String>();
		
		String strQuery = "SELECT * FROM users_profiles " +
			"WHERE users_id="+String.valueOf(users_id);		
		userQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (userQuery.isEmpty() == false ){
			profile = userQuery.get(0);
			return new UserProfileModel(profile);									
		} else {
			return error();
		}		
	}
	
	public static UserProfileModel updateProfile (String users_id,
									HashMap<String,String> values ) {

		for (Map.Entry<String, String> entry : values.entrySet()) {			
			if (entry.getValue()!=null){
				String strUpdate = "UPDATE users_profiles SET "+entry.getKey()+"='"+entry.getValue()+
									"' WHERE users_id="+String.valueOf(users_id);
				DatabaseUtils.executeUpdate(strUpdate);				
			}					
		}
		return selectProfile(users_id);
	}
	
	public static UserProfileModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
