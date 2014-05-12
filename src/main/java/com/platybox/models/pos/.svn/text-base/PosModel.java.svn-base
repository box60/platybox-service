package com.platybox.models.pos;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;


/*
 * This model was written to access the db by private methods, it's contents DO NOT COMPLY with
 * our specifications, e.g. places_id has been changed to placeid
 * */

public final class PosModel {

	/*
	 * Constructor
	 */
	String id;
	String pos_type;
	String places_id;
	String posaddress;
	String pos_receipt_messages_id;
		
	/*Declare a default error.*/
	private static final PosModel AVAILABLE_INSTANCE = new PosModel(
			null, null, null, null,null
			);
			


	public PosModel(String id, String pos_type, String places_id, String posaddress, String pos_receipt_messages_id) {
		this.id = id;
		this.pos_type = pos_type;
		this.places_id = places_id;
		this.posaddress = posaddress;
		this.pos_receipt_messages_id = pos_receipt_messages_id;
		
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setId (String id) {
		AVAILABLE_INSTANCE.id = id;				
	}
	public void setPos_type (String pos_types_id) {
		AVAILABLE_INSTANCE.pos_type = pos_type;	
	}
	public void setPlaces_id (String places_id){
		AVAILABLE_INSTANCE.places_id = places_id;
	}
	public void setPosaddress (String posaddress) {
		AVAILABLE_INSTANCE.posaddress = posaddress;
	}
	public void setPos_receipt_messages_id (String pos_receipt_messages_id) {
		AVAILABLE_INSTANCE.pos_receipt_messages_id = pos_receipt_messages_id;
	}
	
	public String getId() {
		return id;
	}
	public String getPos_type() {
		return pos_type;
	}
	public String getPlaces_id() {
		return places_id;
	}
	public String getPosaddress() {
		return posaddress;
	}
	
	public String getPos_receipt_messages_id() {
		return pos_receipt_messages_id;
	}
			
	/*
	 * Methods.
	 */
		
	public static PosModel selectPos (String places_id) {
					
		ArrayList<HashMap<String,String>> posQuery
				= new ArrayList<HashMap<String,String>>();
		
		//TODO: right now we are assuming that there will only be one system for
		//each place, we should iterate and create accordingly
		String strQuery = "SELECT * FROM pos " +
				"LEFT JOIN pos_types ON pos_types.id=pos_types_id " +
				"WHERE places_id='"+places_id+"'";				
		posQuery = DatabaseUtils.executeQuery(strQuery);		
		if (posQuery.isEmpty() == false) {
			String id = posQuery.get(0).get("id").toString();
			String pos_type = posQuery.get(0).get("type").toString();
			String placeid = posQuery.get(0).get("places_id").toString();
			String posaddress = posQuery.get(0).get("posaddress").toString();		
			String pos_receipt_messages_id = posQuery.get(0).get("pos_receipt_messages_id").toString();
			return new PosModel(id, pos_type , placeid, posaddress, pos_receipt_messages_id);
		}		
		return error();
	}
	
	public static PosModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
