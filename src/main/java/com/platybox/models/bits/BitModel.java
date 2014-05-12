package com.platybox.models.bits;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.platybox.utils.CharEscaper;
import com.platybox.utils.database.DatabaseUtils;

public class BitModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> bit;

	/*Declare a default error.*/
	private static final BitModel AVAILABLE_INSTANCE = new BitModel(
			null
		   );

	public BitModel (HashMap<String,String> bit /*, PlaceScoresModel scores, PlaceModel place*/) {		
		this.bit = bit;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setBit (HashMap<String,String> bit) {
		AVAILABLE_INSTANCE.bit = bit;
	}
	public HashMap<String,String> getBit() {
		return bit;
	}
	
	/*
	 * Methods.
	 */
		
	public static BitModel selectBit (String bits_id) {
		
		ArrayList<HashMap<String,String>> bitQuery
				= new ArrayList<HashMap<String,String>>();
	
		String strQuery = "SELECT bits.id, bits.name, bits.description, bits.qr_image_url, " +
				"bits.places_id, bits.bits_types_id, bits_types.type " +
				"FROM bits LEFT JOIN bits_types ON bits_types.id=bits.bits_types_id " +
				"WHERE bits.id=" + bits_id;
		bitQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (bitQuery.isEmpty() == false ){
			return new BitModel(bitQuery.get(0));
		} else {
			return error();
		}		
	}
	
	public static BitModel selectBitInfo (String bits_id, String users_id) {
		
		ArrayList<HashMap<String,String>> bitQuery
				= new ArrayList<HashMap<String,String>>();
	
		String strQuery = "SELECT bits.name, bits.description, " +
				"bits.places_id, bits.bits_types_id, bits_types.type " +
				"FROM bits LEFT JOIN bits_types ON bits_types.id=bits.bits_types_id " +
				"WHERE bits.id=" + bits_id;
		bitQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (bitQuery.isEmpty() == false ){
			return new BitModel(bitQuery.get(0));
		} else {
			return error();
		}		
	}
	
	
	
	public static BitModel updateBit (String bits_id,
										HashMap<String,
										String> values) {

		for (Map.Entry<String, String> entry : values.entrySet()) {			
			if (entry.getValue()!=null){
				String strUpdate = "UPDATE bits SET "+entry.getKey()+"='"+entry.getValue()+"' " +
						"WHERE id="+String.valueOf(bits_id);
				DatabaseUtils.executeUpdate(strUpdate);				
			}					
		}		
		return selectBit(bits_id);				
	}
	
	public static BitModel insertBit (String bits_types_id,
										String name,
										String description,
										String qr_image_url,
										String places_id) {
			
		String strUpdate = "INSERT INTO bits (bits_types_id, name, description, qr_image_url, places_id) " +
		"VALUES ("+bits_types_id+", '"+CharEscaper.forQuery(name)+"','"+CharEscaper.forQuery(description)+"','"+qr_image_url+"',"+places_id+" )";
		String bits_id = String.valueOf(DatabaseUtils.executeUpdate(strUpdate));
		
		return selectBit(bits_id);
	}
	
	/**
	 * Make this bit the only one representing this place, making all other bits that match
	 * the places_id and are a bits_types_id=2 (i.e. place) point to Utopia.
	 *  
	 * @param bits_id
	 * @return Return the bits_id if successful, null if it fails.
	 */
	public static void makePlaceBitUnique (String bits_id) {
		HashMap<String,String> data = new HashMap<String,String> ();
	    String strQuery = "SELECT places_id FROM bits where id="+bits_id;
		data = DatabaseUtils.executeQuery(strQuery).get(0); 		
		if (data.isEmpty()==false) {
			String places_id = data.get("places_id");
			String strUpdate = "UPDATE bits SET places_id=1, description='Bit no longer valid.' " +
					"WHERE places_id="+places_id+" AND bits_types_id=2 AND id!="+bits_id;
			DatabaseUtils.executeUpdate(strUpdate);
		}
	}
	
	public static BitModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
