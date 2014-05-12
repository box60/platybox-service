package com.platybox.models.checkins;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.platybox.models.places.PlaceModel;
import com.platybox.models.users.UserScoresPlacesModel;
import com.platybox.models.users.UserModel;
import com.platybox.utils.CouponsUtils;
import com.platybox.utils.DateUtils;
import com.platybox.utils.database.DatabaseUtils;

public class BitCheckinModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> checkin;
	private UserModel user;
	private UserScoresPlacesModel score;
	
	/*Declare a default error.*/
	private static final BitCheckinModel AVAILABLE_INSTANCE = new BitCheckinModel(
			null, null, null);

	public BitCheckinModel(HashMap<String,String> checkin, UserModel user, UserScoresPlacesModel score) {		
		this.checkin = checkin;
		this.user= user;
		this.score = score;
	}
	
	/*
	 * Getters and setters.
	 */

	public void setCheckin(HashMap<String,String> checkin) {
		AVAILABLE_INSTANCE.checkin = checkin;
	}

	public HashMap<String,String> getCheckin() {
		return checkin;
	}
	
	public void setUser (UserModel user) {
		AVAILABLE_INSTANCE.user = user;		
	}
	public UserModel getUser () {
		return user;
	}
	public void setPlacescore (UserScoresPlacesModel score) {
		AVAILABLE_INSTANCE.score = score;		
	}
	public UserScoresPlacesModel getScore () {
		return score;
	}
	/*
	 * Methods.
	 */
	
	public static BitCheckinModel selectBitCheckin (String checkins_bits_id, String users_id) {
		
		ArrayList<HashMap<String,String>> checkinQuery
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM checkins_bits WHERE id=" + checkins_bits_id;
		checkinQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (checkinQuery.isEmpty() == false ){
			
			String bits_id = checkinQuery.get(0).get("bits_id");			
			strQuery = "SELECT places_id FROM bits where id="+bits_id;
			String places_id = DatabaseUtils.executeQuery(strQuery).get(0).get("places_id").toString();	
			
			return new BitCheckinModel(checkinQuery.get(0),
										UserModel.selectUser(users_id),
										UserScoresPlacesModel.selectScore(users_id, places_id));
		} else
			return error();
	}

	public static BitCheckinModel insertBitCheckin (String bits_id, String users_id) {
		String strUpdate = "INSERT INTO checkins_bits (users_id, bits_id) " +
				"VALUES ("+users_id+", '"+bits_id+"')";		
		String checkins_bits_id = String.valueOf(DatabaseUtils.executeUpdate(strUpdate));
		
		return selectBitCheckin(checkins_bits_id, users_id);
	}

	/**
	 * Returns true if a user has checked into the given bit, or another bit that belongs to the same place
	 * as the one being tested.
	 *  
	 * @param bits_id
	 * @param users_id
	 * @return True if the user checked in today, false if the user hasn't checked in.
	 */
	public static boolean userCheckedInToday (String bits_id, String users_id) {
		
		String date = DateUtils.getTodayString();
		
		String strQuery = "SELECT checkins_bits.id FROM checkins_bits " +
			"INNER JOIN bits ON checkins_bits.bits_id = bits.id " +
			"WHERE bits.places_id=(SELECT places_id FROM bits WHERE bits.id='"+bits_id+"') " +
					"AND checkins_bits.users_id='"+users_id+"' AND timestamp >= '"+date+"'";
		
		if (DatabaseUtils.executeQuery(strQuery).isEmpty()) 
			return false;
		else
			return true;
	}
	
	public static BitCheckinModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
