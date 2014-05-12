package com.platybox.models.pos;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;


/*
 * This model was written to access the db by private methods, it's contents DO NOT COMPLY with
 * our specifications, e.g. places_id has been changed to placeid
 * */

public final class PosReceiptMessageModel {

	/*
	 * Constructor
	 */

	
	
	String header;
	String welcome;
	String checkin;
	String phone;
	String url;
	

	/*Declare a default error.*/
	private static final PosReceiptMessageModel AVAILABLE_INSTANCE = new PosReceiptMessageModel(
			null, null, null, null,null
			);
			


	public PosReceiptMessageModel(String header, String welcome, String checkin, String phone, String url) {
		this.header = header;
		this.welcome = welcome;
		this.checkin = checkin;
		this.phone = phone;
		this.url = url;		
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setHeader (String header) {
		AVAILABLE_INSTANCE.header = header;
	}
	public String getHeader() {
		return header;
	}
	public void setWelcome (String welcome) {
		AVAILABLE_INSTANCE.welcome = welcome;
	}
	public String getWelcome () {
		return welcome;
	}
	public void setCheckin (String checkin) {
		AVAILABLE_INSTANCE.checkin = checkin;
	}
	public String getCheckin() {
		return checkin;
	}
	public void setPhone(String phone) {
		AVAILABLE_INSTANCE.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public void setUrl (String url) {
		AVAILABLE_INSTANCE.url = url;
	}
	public String getUrl() {
		return url;
	}	
			
	/*
	 * Methods.
	 */
		
	public static PosReceiptMessageModel selectReceiptMessage(String pos_receipt_messages_id) {
					
		ArrayList<HashMap<String,String>> query
				= new ArrayList<HashMap<String,String>>();		
		String strQuery = "SELECT * FROM pos_receipt_messages WHERE id="+pos_receipt_messages_id;		
		query = DatabaseUtils.executeQuery(strQuery);
				
		if (query.isEmpty() == false) {
			String header = query.get(0).get("header");
			String welcome = query.get(0).get("welcome");
			String checkin = query.get(0).get("checkin");
			String phone = query.get(0).get("phone");
			String url = query.get(0).get("url");			
			return new PosReceiptMessageModel(header, welcome, checkin, phone, url);
		}		
		return error();
	}
	
	public static PosReceiptMessageModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
