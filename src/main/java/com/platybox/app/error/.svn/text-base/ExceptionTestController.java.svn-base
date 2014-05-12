package com.platybox.app.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import com.platybox.exception.GenericException;

/*This controller presents an error, to test the exception response from the server */

@Controller
public class ExceptionTestController{
 
	@RequestMapping(value="/oops", method=RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		throw new GenericException("Oppss...System error, please visit it later");
	}
	
}