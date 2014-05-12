package com.platybox.app.settings;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.NestedServletException;

import com.platybox.models.users.UserModel;
import com.platybox.models.users.UserProfileModel;
import com.platybox.session.SessionHandler;
import com.platybox.utils.ImageUtils;
import com.platybox.utils.RegisterUtils;

import org.springframework.ui.Model;

import com.platybox.session.SessionHandler;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AccountSettingsController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/settings/account", method=RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request, HttpServletResponse response) {
		String users_id = SessionHandler.sessionExists(request, response);
		if (users_id == null)
			return "login";
		
		UserModel user = UserModel.selectUser(users_id);		
		model.addAllAttributes(user.getUser());		
		return "settingsaccount";
		
	}

	@RequestMapping(value = "/settings/account", method = RequestMethod.POST)
	public String handleFormUpload( HttpServletRequest request, HttpServletResponse response,
			@RequestParam("username") String username,
			@RequestParam("email") String email,			
			Model model) throws IOException, MaxUploadSizeExceededException {

		String users_id = SessionHandler.sessionExists(request, response);
		if (users_id == null)
			return "login";
		
		HashMap<String,String> values = new HashMap<String,String>();
		UserModel user = UserModel.selectUser(users_id);
		
		if (!user.getUser().get("username").equalsIgnoreCase(username)){
			if (RegisterUtils.checkUsername(username))
				values.put("username", username);
			else
				model.addAttribute("flashusername", "That username is already taken.");
		}
		
		if (!user.getUser().get("email").equalsIgnoreCase(email)) {
			if (RegisterUtils.checkEmail(email)){
				values.put("email", email);
				UserModel.setUnverified(users_id);
			}
			else
				model.addAttribute("flashemail", "That email is already taken");
		}
		
		if (!values.isEmpty()) {
			user = UserModel.updateUser(users_id, values);
			model.addAttribute("flashsuccess", "Your changes have been saved.");
		}
		
		model.addAllAttributes(user.getUser());
		
		return "settingsaccount";
	}
}