package com.platybox.models.bits;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.platybox.utils.database.DatabaseUtils;

public class BitsScannedModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> data;
	
	/*Declare a default error.*/
	private static final BitsScannedModel AVAILABLE_INSTANCE = new BitsScannedModel(
			null);

	public BitsScannedModel(HashMap<String,String> data) {
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
	public static void logBitScanned (String bits_id) {		
		if (bits_id != null){
			String strUpdate = "INSERT INTO bits_scanned (bits_id) " +
								"VALUES ('"+bits_id+"')";					
			DatabaseUtils.executeUpdate(strUpdate); 			
		}
	}
	
	public static BitsScannedModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
