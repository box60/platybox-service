package com.platybox.utils;

import java.util.Calendar;

public class DateUtils {
	
	public static String getTodayString() {		
		Calendar cal = Calendar.getInstance();
		String yearToday = String.valueOf(cal.get(Calendar.YEAR));
		String monthToday = String.valueOf(cal.get(Calendar.MONTH)+1);
		String dayToday = String.valueOf(cal.get(Calendar.DATE));
		return yearToday + "-" + monthToday + "-" + dayToday;		
	}
	
	public static String getCalendarToString (Calendar cal) {
		String yearToday = String.valueOf(cal.get(Calendar.YEAR));
		String monthToday = String.valueOf(cal.get(Calendar.MONTH)+1);
		String dayToday = String.valueOf(cal.get(Calendar.DATE));
		return yearToday + "-" + monthToday + "-" + dayToday;
	}

	/**
	 * Transforms a date string from "2011-08-01" or "2008-08-01 12:00:00"  to "08/01"
	 * @param date
	 * @return
	 */
	public static String getReadableDay (String date) {
		if (date.length() > 10) {
			date.substring(0, 10); //remove the hours
		}
		String[] d = date.split("-");
		return d[1] + "/" + d[2];
	}
	
}
