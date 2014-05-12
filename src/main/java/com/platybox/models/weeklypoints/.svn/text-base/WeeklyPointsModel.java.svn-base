package com.platybox.models.weeklypoints;

import com.platybox.utils.database.DatabaseUtils;


/*
 * This is a temporary model without persistence that computes the weekly entries
 * to chow vancouver's weekly prize.
 */

public final class WeeklyPointsModel {

	/*
	 * Constructor
	 */
	
	//private HashMap<String,String> mastery;
	String checkins;
	String featured;
	String badges;
	String points;
	
	/*Declare a default error.*/
	private static final WeeklyPointsModel AVAILABLE_INSTANCE = new WeeklyPointsModel(
			null, null, null, null );


	public WeeklyPointsModel(String checkins, String featured, String badges, String points) {		
		this.checkins = checkins;
		this.featured = featured;
		this.badges = badges;
		this.points = points;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setCheckins (String checkins) {
		AVAILABLE_INSTANCE.checkins = checkins;
	}

	public String getCheckins() {
		return checkins;
	}

	public void setFeatured (String featured) {
		AVAILABLE_INSTANCE.featured = featured;
	}

	public String getFeatured() {
		return featured;
	}
	
	public void setBadges (String badges) {
		AVAILABLE_INSTANCE.badges = badges;
	}

	public String getBadges() {
		return badges;
	}
	
	public void setPoints (String Points) {
		AVAILABLE_INSTANCE.points = Points;		
	}
	public String getPoints () {
		return points;
	}
		
	/*
	 * Methods.
	 */
	
	/*
	 * Computes weekly points by a user.
	 */
	public static WeeklyPointsModel showPoints (String users_id) {
		//count checkins
		String strQuery = "SELECT COUNT(*) FROM checkins_bits " +
				"WHERE timestamp > ( SELECT DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)) " +
				"AND users_id="+users_id;		
		String checkins = DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)");
		
		//count featured
		strQuery = "SELECT COUNT(*) FROM checkins_bits " +
				"LEFT JOIN quests_bits ON quests_bits.bits_id=checkins_bits.bits_id " +
				"LEFT JOIN quests ON quests.id=quests_bits.quests_id " +
				"WHERE timestamp > ( SELECT DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)) " +
				"AND quests.available=1 AND users_id="+users_id;		
		String featured = DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)");		
		
		//count badges
		strQuery = "SELECT COUNT(*) FROM users_quests " +
				"WHERE timestamp > ( SELECT DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)) " +
				"AND users_id="+users_id;		
		String badges = DatabaseUtils.executeQuery(strQuery).get(0).get("COUNT(*)");
		
		//compute total points.
		int checkins_i = Integer.parseInt(checkins);
		int featured_i = Integer.parseInt(featured);
		int badges_i = Integer.parseInt(badges);		
		String points = String.valueOf(checkins_i+featured_i+badges_i);
		
		return new WeeklyPointsModel(checkins, featured, badges, points);
		
		}

	public static WeeklyPointsModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
