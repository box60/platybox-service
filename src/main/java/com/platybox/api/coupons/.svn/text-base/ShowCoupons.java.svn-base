package com.platybox.api.coupons;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platybox.models.coupons.CouponsModel;


/*
 * Will search for sponsored promos and return them if they're valid. Will return promos even when they're not scheduled for. Clients must present promo availability accordingly. 
 * 
 * Example response:
 * {"promos":[{"user":{"user":{"id":"2","username":"roberto","coins":"99999971","description":"With our powers combined, we are platypus.","name":"A platypus.","experience":"6","points":"596","type":"user","photo":"http://api.platybox.com/1/resources/images/pr/platypus.jpg"}},"bit":{"bit":{"id":"114","qr_image_url":"http://img.platybox.com/qr/114.gif","places_id":"2","description":"Get a pint of Rocky's % \"Mountain Lager with the purchase of any meal.","name":"Lager-ific Pint","bits_types_id":"9","type":"promos"}},"promo":{"valid":"1","dtend":null,"places_id":"2","rrule":null,"users_id":"2","available":"1","rdatelist":null,"id":"25","dtstart":"20110310","price":"5","description":"Get a pint of Rocky's % \"Mountain Lager with the purchase of any meal.","name":"Lager-ific Pint","sponsored":"1"},"place":{"place":{"id":"2","geolong":"-123.184674","name":"Box 60 Inc.","geolat":"49.263495"},"mastery":{"promos":"0","checkins":"0"}}}]}
 */
@Controller
public class ShowCoupons extends HttpServlet {
	
	@RequestMapping(value="/coupons/search", method=RequestMethod.GET)	
	public @ResponseBody CouponsModel  findCouponsAvailable (	HttpServletRequest request,
											HttpServletResponse response)	
											throws IOException, ServletException {	 	
	    return CouponsModel.findCouponsAsModel();
    }
    private static final long serialVersionUID = 1L;
}