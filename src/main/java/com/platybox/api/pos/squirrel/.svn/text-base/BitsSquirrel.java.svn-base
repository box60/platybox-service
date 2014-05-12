package com.platybox.api.pos.squirrel;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.platybox.models.bits.BitModel;
import com.platybox.models.places.PlaceModel;
import com.platybox.models.pos.PosModel;
import com.platybox.models.pos.PosReceiptMessageModel;
import com.platybox.models.users.UserPlacesModel;
import com.platybox.sms.TropoSMSConnector;
import com.platybox.utils.BitsUtils;
import com.platybox.utils.database.DatabaseUtils;

@Controller
public class BitsSquirrel extends HttpServlet {
	/*
	 * This method is to be used by a POS system to create a new bit and bound it to it's related place
	 * As a result, previous bits will be deactivated. This method is intended as a way to automatically
	 * update the bits related to each place.
	 */
	@RequestMapping(value="/pos/squirrel/bits/create", method=RequestMethod.GET)	
	public void ping(	HttpServletRequest request,
			@RequestParam String places_id,
			@RequestParam String token,
			HttpServletResponse response)	
					throws IOException, ServletException {

		int consumerId = DatabaseUtils.getConsumerId(token);

		if (consumerId > 0) {
			//Check if the owner of this consumer token is bound to this place
			String users_id = String.valueOf(DatabaseUtils.getConsumerUsersId(token));

			String placeName = PlaceModel.findPlaceNameByPlacesId(places_id);

			//create new bit
			String bits_types_id = "2"; //places 
			String name = placeName;
			String description = placeName;
			String bits_id = BitsUtils.createBitGetId(users_id, bits_types_id, name, description, places_id);

			if (bits_id != null) {
				int bits_idInt = Integer.parseInt(bits_id);
				String bits_id_hex = Integer.toHexString(bits_idInt).toUpperCase();		
				
				PosModel pos = PosModel.selectPos(places_id);				
				if (pos!=null) {
					PosReceiptMessageModel message = PosReceiptMessageModel.selectReceiptMessage(pos.getPos_receipt_messages_id());
					
					String encodedMessage = message.getHeader() + 
											message.getWelcome() +
											message.getCheckin() +
											bits_id_hex +
											message.getPhone() +
											message.getUrl();
					
					response.setContentType("text/plain");
					PrintWriter out;
					out = response.getWriter();
					out.println(encodedMessage);
					out.close();
					
				}
				//disable old bits
				BitsUtils.disablePlaceBitsButThis(bits_id);
			}

		}
		
	}
    private static final long serialVersionUID = 1L;
	
}



