package com.platybox.models.bits;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.platybox.utils.database.DatabaseUtils;

/*
 * Logging the bits that led to registrations
 */

public class BitsRegistrationsModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> data;
	
	/*Declare a default error.*/
	private static final BitsRegistrationsModel AVAILABLE_INSTANCE = new BitsRegistrationsModel(
			null);

	public BitsRegistrationsModel(HashMap<String,String> data) {
		this.data = data;		
	}
	
	/*
	 * Getters and setters.
	 */

	public void setData(HashMap<String,String> data) {
		AVAILABLE_INSTANCE.data = data;
	}

	public HashMap<String,String> getData() {
		return data;
	}
	
	/*
	 * Methods.
	 */
	public static void logBitLedToRegistration (String bits_id, String users_id) {		
		if (bits_id != null){
			String strUpdate = "INSERT INTO bits_registrations (bits_id, users_id) " +
								"VALUES ('"+bits_id+"', '"+users_id+"')";					
			DatabaseUtils.executeUpdate(strUpdate); 			
		} 		 
	}
		
	public static BitsRegistrationsModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
