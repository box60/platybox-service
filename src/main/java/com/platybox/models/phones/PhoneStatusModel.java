package com.platybox.models.phones;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class PhoneStatusModel {
	
	/*
	 * Constructor
	 */	
	String status;
	String users_id;
	String bits_id;
	
	/*Declare a default error.*/
	private static final PhoneStatusModel AVAILABLE_INSTANCE = new PhoneStatusModel(
			null, null, null);

	public PhoneStatusModel(String status, String users_id, String bits_id) {		
		this.status = status;
		this.users_id = users_id;
		this.bits_id = bits_id;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setStatus (String status) {
		AVAILABLE_INSTANCE.status=status;
	}
	public String getStatus() {
		return status;
	}
	
	public void setUsers_id (String users_id) {
		AVAILABLE_INSTANCE.users_id=users_id;
	}
	public String getUsers_id() {
		return users_id;
	}
	public void setBits_id (String bits_id) {
		AVAILABLE_INSTANCE.bits_id=bits_id;
	}
	public String getBits_id() {
		return bits_id;
	}
		
	/*
	 * Methods.
	 */	
	
	public static PhoneStatusModel findStatus (String phone) {
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>> ();
		String strQuery = "SELECT phones_statuses_types.type, " +
				"phones_statuses.users_id, phones_statuses.bits_id " +
				"FROM phones_statuses LEFT JOIN phones_statuses_types " +
				"ON phones_statuses_types.id=phones_statuses.phones_statuses_types_id " +
				"WHERE phone="+phone+" LIMIT 1";
		data = DatabaseUtils.executeQuery(strQuery);
		if (data.isEmpty())
			return error();
		else {
			String status = data.get(0).get("type");
			String users_id = data.get(0).get("users_id");
			String bits_id = data.get(0).get("bits_id");
			return new PhoneStatusModel(status, users_id, bits_id);
		}
	}
	/**
	 * Inserts a phone status
	 * @param phone
	 * @param status
	 */
	public static void insertStatus (String phone, String status) {		
		String strUpdate = "INSERT INTO phones_statuses (phone, phones_statuses_types_id) " +
				"VALUES ("+phone+",(SELECT id FROM phones_statuses_types WHERE phones_statuses_types.type='"+status+"'))";
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void changeStatus (String phone, String status) {
		String strUpdate = "UPDATE phones_statuses " +
				"SET phones_statuses_types_id = (" +
								"SELECT id FROM phones_statuses_types " +
								"WHERE phones_statuses_types.type='"+status+"') WHERE phones_statuses.phone="+phone;		
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void changeUsersId (String phone, String users_id) {
		String strUpdate = "UPDATE phones_statuses " +
				"SET users_id ="+users_id+" WHERE phone="+phone;		
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void changeBitsId (String phone, String bits_id) {
		String strUpdate = "UPDATE phones_statuses " +
				"SET bits_id ="+bits_id+" WHERE phone="+phone;		
		DatabaseUtils.executeUpdate(strUpdate);
	}	
	
	public static String selectMessage (String status) {
		String strQuery = "SELECT message FROM phones_statuses_messages WHERE phones_statuses_types_id=(" +
								"SELECT id FROM phones_statuses_types " +
								"WHERE phones_statuses_types.type='"+status+"')";
		return DatabaseUtils.executeQuery(strQuery).get(0).get("message");		
	}
	
	public static PhoneStatusModel error() {
		return AVAILABLE_INSTANCE;
	}		
	
}
