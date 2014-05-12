package com.platybox.models.analytics;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.platybox.models.places.PlaceModel;
import com.platybox.utils.DateUtils;
import com.platybox.utils.database.DatabaseUtils;

public final class TwoWeekScansAndCheckinsModel {

	/*
	 * Constructor
	 */
	
	/*
	 * This object schema is to fit google charts DataTable objects and according to specification:
	 * http://code.google.com/apis/chart/interactive/docs/reference.html#DataTable
	 * 
	 */
	ArrayList<HashMap<String,String>> cols;
	ArrayList<HashMap<String,ArrayList<HashMap<String,Object>>>> rows;
	
	/*Declare a default error.*/
	private static final TwoWeekScansAndCheckinsModel AVAILABLE_INSTANCE = new TwoWeekScansAndCheckinsModel(
			null, null);


	public TwoWeekScansAndCheckinsModel(ArrayList<HashMap<String,String>> cols, ArrayList<HashMap<String,ArrayList<HashMap<String,Object>>>> rows) {		
		this.cols = cols;
		this.rows = rows;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setCols (ArrayList<HashMap<String,String>> cols) {
		AVAILABLE_INSTANCE.cols = cols;
	}

	public ArrayList<HashMap<String,String>> getCols() {
		return cols;
	}
	
	public void setRows (ArrayList<HashMap<String,ArrayList<HashMap<String,Object>>>> rows) {
		AVAILABLE_INSTANCE.rows =  rows;
	}

	public ArrayList<HashMap<String,ArrayList<HashMap<String,Object>>>> getRows() {
		return rows;
	}
		
	/*
	 * Methods.
	 */
	
	public static TwoWeekScansAndCheckinsModel showTwoWeekScansAndCheckins (String places_id) {
		//Add columns
		ArrayList<HashMap<String,String>> cols = new ArrayList<HashMap<String,String>> ();
		HashMap<String,String>  dayColumn = new HashMap<String,String>();
		HashMap<String,String>  dataScanColumn = new HashMap<String,String>();
		HashMap<String,String>  dataCheckinColumn = new HashMap<String,String>();
		dayColumn.put("label", "Day");
		dayColumn.put("type", "string");
		cols.add(dayColumn);		
		dataScanColumn.put("label", "Scans");
		dataScanColumn.put("type", "number");
		cols.add(dataScanColumn);
		dataCheckinColumn.put("label", "Checkins");
		dataCheckinColumn.put("type", "number");
		cols.add(dataCheckinColumn);
		
		
		
		//Add rows, per day
		ArrayList<HashMap<String,ArrayList<HashMap<String,Object>>>> rows = new ArrayList<HashMap<String,ArrayList<HashMap<String,Object>>>>();
		
		String place_zone = PlaceModel.findPlaceTimeZone(places_id); //Create a date with the timezone of the place to correctly search the db
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(place_zone));		
		//cal.add(Calendar.DAY_OF_MONTH, -14);
		//for (int i=0 ; i < 14 ; i++) {
		//TODO: days to report should be passed as a parameter
		
		//cal.set(Calendar.YEAR, 2011);
		//cal.set(Calendar.MONTH, 2);
		//cal.set(Calendar.DAY_OF_MONTH, 1);
		
		cal.add(Calendar.DAY_OF_MONTH, -68);
		for (int i=0 ; i < 35 ; i++) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			String day = DateUtils.getCalendarToString(cal);
			
			String strQuery = "SELECT COUNT(*) FROM bits_scanned " +
					"INNER JOIN bits ON bits.id=bits_scanned.bits_id " +
					"WHERE bits.places_id='"+places_id+"' " +
					"AND DATE(CONVERT_TZ(bits_scanned.timestamp,'GMT',(SELECT timezone FROM places WHERE id='"+places_id+"')))='"+day+"'";
			
			int scanCount = Integer.parseInt(DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)"));	
			
			
			strQuery = "SELECT COUNT(*) FROM checkins_bits " +
					"INNER JOIN bits ON bits.id=checkins_bits.bits_id " +
					"WHERE bits.places_id='"+places_id+"' " +
					"AND DATE(CONVERT_TZ(checkins_bits.timestamp,'GMT',(SELECT timezone FROM places WHERE id='"+places_id+"')))='"+day+"'";			
					
					
			int checkinCount = Integer.parseInt(DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)"));
			
			
			HashMap<String, ArrayList<HashMap<String,Object>>> row = new HashMap<String, ArrayList<HashMap<String,Object>>>(); 
			ArrayList<HashMap<String,Object>> c = new ArrayList<HashMap<String,Object>>(); 
 			HashMap<String,Object> dayColumnItem = new HashMap<String,Object> ();
 			HashMap<String,Object> dataScanColumnItem = new HashMap<String,Object> ();
 			HashMap<String,Object> dataCheckinColumnItem = new HashMap<String,Object> ();
 			
 			dayColumnItem.put("v", DateUtils.getReadableDay(day));			
			c.add(dayColumnItem);
			dataScanColumnItem.put("v", scanCount);
			c.add(dataScanColumnItem);
			dataCheckinColumnItem.put("v", checkinCount);
			c.add(dataCheckinColumnItem);
			row.put("c", c);			
			rows.add(row);
			
		}
		
		return new TwoWeekScansAndCheckinsModel(cols, rows);
				
	}
	
	public static TwoWeekScansAndCheckinsModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
