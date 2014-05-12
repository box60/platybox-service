package com.platybox.app.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*This controller just renders the generic view*/

@Controller
public class ErrorController{ 
	@RequestMapping(value="/error", method=RequestMethod.GET)
	protected String renderGenericError () {
		return "GenericExceptionPage";
	}
}