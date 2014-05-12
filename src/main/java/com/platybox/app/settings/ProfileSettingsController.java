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
public class ProfileSettingsController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/settings/profile", method=RequestMethod.GET)
	public String showForm(Model model, HttpServletRequest request, HttpServletResponse response) {
		String users_id = SessionHandler.sessionExists(request, response);
		if (users_id == null)
			return "login";
		
		UserProfileModel profile = UserProfileModel.selectProfile(users_id);		
		model.addAllAttributes(profile.getProfile());		
		return "settingsprofile";
		
	}

	@RequestMapping(value = "/settings/profile", method = RequestMethod.POST)
	public String handleFormUpload( HttpServletRequest request, HttpServletResponse response,
			@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file,
			@RequestParam("realname") String realname,
			@RequestParam("bio") String bio,
			Model model) throws IOException, MaxUploadSizeExceededException {

		String users_id = SessionHandler.sessionExists(request, response);
		if (users_id == null)
			return "login";
		
		HashMap<String,String> values = new HashMap<String,String>();
		UserProfileModel profile = UserProfileModel.selectProfile(users_id);

		
		//TODO: this is a hack to catch files too big.
		//we couldn't catch the MaxUploadExceededException caught. :( 
		if (!file.isEmpty()) {
			if (ImageUtils.isImageable(file)) {
				byte[] bytes = file.getBytes();
				String imageURL = ImageUtils.updateAvatarImages(bytes, users_id);
				values.put("photo", imageURL);
			} else 
				model.addAttribute("flashimage", "File is too big, or not an image.");			
		}

		if (!profile.getProfile().get("name").equalsIgnoreCase(realname)) {	
			if (RegisterUtils.checkName(realname))
				values.put("name", realname);
			else 
				model.addAttribute("flashname", "Your name must be two words, without punctuation.");
		}
		
		if (!profile.getProfile().get("description").equalsIgnoreCase(bio)) {
			if (RegisterUtils.checkDescription(bio))
				values.put("description", bio);
			else
				model.addAttribute("flashdescription", "Please tell the world who you are.");
		}
		
		if (!values.isEmpty()) {
			profile = UserProfileModel.updateProfile(users_id, values);
			model.addAttribute("flashsuccess", "Your changes have been saved.");
		}  
		
		model.addAllAttributes(profile.getProfile());
		
		
		return "settingsprofile";
	}
}