package com.platybox.utils;

import java.util.HashMap;

import com.platybox.models.bits.BitModel;

public class BitsUtils {
	/**
	 * Create a Bit, and return it's model
	 * @param users_id
	 * @param bits_types_id
	 * @param name
	 * @param description
	 * @param places_id If null, it will default to "1"
	 * @return a BitModel representing this bit, null if it fails
	 */
	public static BitModel createBit(String users_id, String bits_types_id, String name, String description, String places_id) {
    	if ( places_id == null || places_id.equalsIgnoreCase("0")) 
    		places_id = "1";
    	 
    	
    	//insert an empty QR code for now.
        String qr_image_url = "";
        
        String bits_id = BitModel.insertBit(bits_types_id,
        									name,
        									description,
        									qr_image_url,
        									places_id).getBit().get("id"); 
       	//Good, now we create its qrcode
        int bitId = Integer.parseInt(bits_id);
       	CreateBitQR bitQr = new CreateBitQR(bitId);	               	
       	String bitQrUrl = bitQr.create();
       	if (bitQrUrl != null) {	            			 
       		HashMap <String,String> values = new HashMap<String,String>();            	             	  
       		values.put("qr_image_url", bitQrUrl);
       		
       		return BitModel.updateBit(bits_id, values);  
    	} else
    		return null;
	}
	
	/**
	 * Create a Bit, and return it's bits_id
	 * @param users_id
	 * @param bits_types_id
	 * @param name
	 * @param description
	 * @param places_id If null, it will default to "1"
	 * @return a string representing a bits_id, or null if it fails.
	 */
	public static String createBitGetId(String users_id, String bits_types_id, String name, String description, String places_id) {
    	if ( places_id == null || places_id.equalsIgnoreCase("0")) 
    		places_id = "1";
    	
    	//insert an empty QR code for now.
        String qr_image_url = "";
        
        String bits_id = BitModel.insertBit(bits_types_id,
        									name,
        									description,
        									qr_image_url,
        									places_id).getBit().get("id");
       	//Good, now we create its qrcode
        int bitId = Integer.parseInt(bits_id);
       	CreateBitQR bitQr = new CreateBitQR(bitId);	               	
       	String bitQrUrl = bitQr.create();
       	if (bitQrUrl != null) {	            			 
       		HashMap <String,String> values = new HashMap<String,String>();            	             	  
       		values.put("qr_image_url", bitQrUrl);
       		
       		return bits_id;  
    	} else
    		return null;
	}
		
	public static void disablePlaceBitsButThis (String bits_id) {		
		BitModel.makePlaceBitUnique(bits_id);
	}
	
	
	
}
