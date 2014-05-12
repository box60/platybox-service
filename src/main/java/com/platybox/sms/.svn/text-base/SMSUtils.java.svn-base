package com.platybox.sms;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.platybox.models.phones.PhoneStatusModel;

public class SMSUtils {
	public static String sendSMS(String phone, String msg) {
		TropoSMSConnector sms = new TropoSMSConnector(phone, msg);
		return sms.sendSMS().toString();
	}		

	/**
	 * Checks if this code is in the form 0F... (i.e. 0x0F...)
	 * @param msg
	 * @return True if it matches
	 */
	public static boolean isHexOnly (String msg) {
		Pattern p = Pattern.compile("(?i)^[0-9]{2}+[A-F0-9]{1}+[A-F0-9]*$");
	    Matcher m = p.matcher(msg);
	    
	    if (m.matches())
	    	return true;
	    else 
	    	return false;		
	}
	
	/**
	 * A temporary solution to the fact that we cannot change codes. Non-connected POS systems
	 * we have created a pattern, [DayOfTheMonthDay+21][Single Random Character][Bit_id in hex: 0F... (i.e. 0x0F...)]
	 * It additionally checks if it was produced today. 
	 * @param msg
	 * @return True if it matches
	 */
	public static boolean isMaskedHexAndToday (String msg) {
		Pattern p = Pattern.compile("(?i)^[0-9]{2}[A-F0-9]{1}[A-F0-9]{1,}$");
	    Matcher m = p.matcher(msg);
	    
	    if (m.matches() && (maskedHexIsToday(msg) || maskedHexIsAdvertisement(msg) )){
	    	return true;
	    }
	    else 
	    	return false;		
	}

	public static boolean isEmail (String msg) {
		Pattern p = Pattern.compile("(?i)^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
	    Matcher m = p.matcher(msg);
	    
	    if (m.matches()){
	    	return true;
	    }
	    else 
	    	return false;
	}
	
	
	/**
	 * Check if this hex was produced today. Use UTC as the standard as all clients
	 * must provide data in UTC time. But allow people to check in late, i.e. next day.
	 * @param msg
	 * @return
	 */
	public static boolean maskedHexIsToday (String msg) {
		String dateString = msg.substring(0,2);
		int dateInt = Integer.parseInt(dateString) - 21; //TODO: remove this magic number!!!
		
		Calendar calendar = Calendar.getInstance();
		int dom = calendar.get(Calendar.DAY_OF_MONTH);
		
		//Allow people to check in for a whole day. i.e. if they have a
		//bit that was produced the day before, allow them to check in.
		//TODO: this needs to be better done.
		if ((dateInt <= dom && dateInt >= dom-1) || (dateInt>=28 && dom==1) ) { 
			return true;
		} else
			return false;
	}
	/**
	 * Advertisements have the pattern 000+hex 
	 * @param msg
	 * @return true if the first three characters match '000'
	 */
	public static boolean maskedHexIsAdvertisement (String msg) {
		String adString = msg.substring(0,3);
		if (adString.equalsIgnoreCase("000"))
			return true;
		else
			return false;		
	}
	
	public static String maskedHexGetBitsId (String msg) {
		String hex = msg.substring(3);		
		return String.valueOf(Integer.parseInt(hex, 16));	
	}
		
}
