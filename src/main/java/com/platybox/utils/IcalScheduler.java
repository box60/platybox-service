package com.platybox.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.google.ical.compat.javautil.DateIteratorFactory;
import com.google.ical.iter.RecurrenceIterator;
import com.google.ical.iter.RecurrenceIteratorFactory;
import com.google.ical.util.DTBuilder;
import com.google.ical.values.DateValue;
import com.google.ical.values.DateValueImpl;
import com.google.ical.values.Frequency;
import com.google.ical.values.IcalParseUtil;
import com.google.ical.values.PeriodValueImpl;
import com.google.ical.values.RDateList;
import com.google.ical.values.RRule;
import com.google.ical.values.Weekday;
import com.google.ical.values.WeekdayNum;

public class IcalScheduler {

	TimeZone tzid = TimeZone.getDefault(); 
	DateValue dtstart = null;
	DateValue dtend = null;
	RRule rrule = null;
	RDateList rdatelist = null;
	int yearToday;
	int monthToday;
	int dayToday;
	int hourToday; //hour will be enough today.
	int yearTomorrow;
	int monthTomorrow;
	int dayTomorrow;
	int hourTomorrow; //hour will be enough today.
	int yearYesterday;
	int monthYesterday;
	int dayYesterday;
	int hourYesterday; //hour will be enough today.
	
	
	public IcalScheduler (String dtstart, String dtend, String rrule, String rdatelist) {
		try {
			if (dtstart!=null)
				this.dtstart = IcalParseUtil.parseDateValue(dtstart);
			if (dtend!=null)
				this.dtend = IcalParseUtil.parseDateValue(dtend);
			if (rrule!=null)
				this.rrule = new RRule(rrule);
			if (rdatelist!=null)
				this.rdatelist = parseDateList (rdatelist);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
		
	public String getDtstart () {
		return dtstart.toString();
	}
	public String getDtend () {
		return dtend.toString();		
	}
	public String getRrule () {
		return rrule.toString();
	}
	public String getRdatelist () {
		return rdatelist.toString();
	}	
	
	public Boolean isAvailable () {
			
		try {			
			setToday();
			setTomorrow();
			setYesterday();
			DateValue today
					= new DTBuilder (this.yearToday, this.monthToday, this.dayToday).toDate();
			DateValue tomorrow
					= new DTBuilder (this.yearTomorrow, this.monthTomorrow, this.dayTomorrow).toDate();
			DateValue yesterday
				= new DTBuilder (this.yearYesterday, this.monthYesterday, this.dayYesterday).toDate();
				
			//if no start nor rule specified it's an "ever and ever" promo 
			if (dtstart==null && dtend==null && rrule==null)
				return true;
						
			if (this.dtstart!=null) { //check if there start
				//if start specified but no end, and within range, it's an "ever and ever" promo
				if (this.dtstart.compareTo(today)<=0 && dtend==null)
					return true;
				
				//if within range but no rule, it's an ever and ever promo
				if (this.dtstart.compareTo(today)<=0 && this.dtend.compareTo(today)>=0 && this.rrule==null)
					return true;
			} else 
				this.dtstart = yesterday; //else just fake today to avoid a null starting day
			
			if (this.dtend==null) //if it's still null, just fake tomorrrow
				this.dtend = tomorrow;
			
			//if within range, iterate over occurrences specified by rrule.
			if (this.dtstart.compareTo(today)<=0 && this.dtend.compareTo(today)>=0 && this.rrule!=null){			
				RecurrenceIterator ri
					= RecurrenceIteratorFactory.createRecurrenceIterator(this.rrule, this.dtstart, this.tzid);			
				ri.advanceTo(today); //start from today eh?			
				while (ri.hasNext()) {
					DateValue recurrence = ri.next();
					int exists = recurrence.compareTo(today); 
					if (exists==0) { //compare returns an int representing the days to(+)/from(-) matching date
						//check for hours if rrule has hours
						int activeHours [] = this.rrule.getByHour();
						if (activeHours.length > 0) {
							for (int i=0; i<activeHours.length; i++) {
								if (activeHours[i] == this.hourToday)
									return true;
							}
							return false;
						} else 
							return true;
					}
					else if (exists > 0) //gone past recurrence and found nothing.
						break;
				}
				
			}			
			
		} catch (Exception e) { //might be removed
			e.printStackTrace();
		}		
		return false;		
	}
	
	
	private void setToday() {
		Calendar cal = Calendar.getInstance();		
		this.yearToday = cal.get(Calendar.YEAR);
		this.monthToday = cal.get(Calendar.MONTH)+1;//January is 0
		this.dayToday = cal.get(Calendar.DATE);
		this.hourToday = cal.get(Calendar.HOUR_OF_DAY);
	}

	private void setTomorrow () {
		Calendar cal = Calendar.getInstance();		
		this.yearTomorrow = cal.get(Calendar.YEAR);
		this.monthTomorrow = cal.get(Calendar.MONTH)+1;//January is 0
		this.dayTomorrow = cal.get(Calendar.DATE)+1;
		this.hourTomorrow = cal.get(Calendar.HOUR_OF_DAY);
	}
	
	private void setYesterday() {
		Calendar cal = Calendar.getInstance();		
		this.yearYesterday = cal.get(Calendar.YEAR);
		this.monthYesterday = cal.get(Calendar.MONTH)+1;//January is 0
		this.dayYesterday = cal.get(Calendar.DATE)-1;
		this.hourYesterday= cal.get(Calendar.HOUR_OF_DAY);
	}
	
	
	private RDateList parseDateList (String list) {
		
		           	 
		RDateList rdateList = new RDateList(this.tzid);
		String []tokens = list.split(",");
		DateValue [] datesUtc = new DateValue[tokens.length];

		try {
			for(int i=0; i < tokens.length; i++){
				datesUtc[i] = new DTBuilder(IcalParseUtil.parseDateValue(tokens[i])).toDate();
			}
			rdateList.setDatesUtc(datesUtc);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return rdateList;
	}

}
