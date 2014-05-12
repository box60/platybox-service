package com.platybox.utils;

import java.util.HashMap;

import javax.servlet.ServletContext;

import com.platybox.models.bits.BitModel;
import com.platybox.models.promos.PromoModel;
import com.platybox.models.promos.PromosModel;
import com.platybox.utils.database.DatabaseUtils;

public class PromosUtils {
	
	PromosUtils () {		
	}
	 
	/*REturn all the available promos, preferred for the api*/
	 public final static PromosModel createPromo (String users_id, String name, String description, String expires, String price, String places_id,
																						    String dtstart,
																							String dtend,
																							String rrule,
																							String rdatelist ) {            	
    	 String bits_types_id = "9";             
         String qr_image_url = ""; //insert empty for now.	 
    	 //String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
    	 //TODO: simplify this within bitQRURL
    	 BitModel bit = BitModel.insertBit(bits_types_id,
		            			 			name,
		            			 			description,
		            			 			qr_image_url,
		            			 			places_id
		            			 			);
    	 //Good, now we create its qrcode
    	 String bits_id = bit.getBit().get("id");
    	 int bitId = Integer.parseInt(bits_id);
    	 CreateBitQR bitQr = new CreateBitQR(bitId);	               	         		 
         String bitQrUrl = bitQr.create();
         if (bitQrUrl != null) {	            			 
        	 HashMap <String,String> values = new HashMap<String,String>();            	             	  
        	 values.put("qr_image_url", bitQrUrl);
        	 BitModel.updateBit(bits_id, values);  
         }
         
 		 //Now let's create a promo!.
 		 return PromosModel.insertPromo(bits_id,
 				 						places_id,
 				 						price,
 				 						name,
 				 						description,
 				 						expires,
 				 						users_id,
 				 						dtstart,
 				 						dtend,
 				 						rrule,
 				 						rdatelist);
     }
	 
	 /*REturn only the created model*/
	 public final static PromoModel createPromoGetInserted (String users_id, String name, String description, String expires, String price, String places_id,
			 String dtstart,
			 String dtend,
			 String rrule,
			 String rdatelist ) {            	
		 String bits_types_id = "9";             
		 String qr_image_url = ""; //insert empty for now.	 
		 //String users_id = String.valueOf(DatabaseUtils.getUserId(accessor));
		 //TODO: simplify this within bitQRURL
		 BitModel bit = BitModel.insertBit(bits_types_id,
				 name,
				 description,
				 qr_image_url,
				 places_id
		 );
		 //Good, now we create its qrcode
		 String bits_id = bit.getBit().get("id");
		 int bitId = Integer.parseInt(bits_id);
		 CreateBitQR bitQr = new CreateBitQR(bitId);	               	         		 
		 String bitQrUrl = bitQr.create();
		 if (bitQrUrl != null) {	            			 
			 HashMap <String,String> values = new HashMap<String,String>();            	             	  
			 values.put("qr_image_url", bitQrUrl);
			 BitModel.updateBit(bits_id, values);  
		 }

		 //Now let's create a promo!.
		 return PromosModel.insertPromoGetInserted(bits_id,
				 places_id,
				 price,
				 name,
				 description,
				 expires,
				 users_id,
				 dtstart,
				 dtend,
				 rrule,
				 rdatelist);
	 }
	

}
