package com.platybox.app.ping;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.platybox.models.pingresponse.PingResponseModel;

/**
 * This controller provides a simple response to ping the api.
 */
@Controller
public class PingController {

	private static final Logger logger = LoggerFactory.getLogger(PingController.class);
			
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/ping", method=RequestMethod.GET)	
	public @ResponseBody PingResponseModel home() {
		
		return PingResponseModel.available();
		
		/*
		DataResponse data = new DataResponse();	
		return data;
		*/
				
	}
	
}

