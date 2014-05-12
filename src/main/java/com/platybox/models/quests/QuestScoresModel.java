package com.platybox.models.quests;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public final class QuestScoresModel {

	/*
	 * Constructor
	 */
	//private HashMap<String,String> score;
	String points;
	String quests;
	
	/*Declare a default error.*/
	private static final QuestScoresModel AVAILABLE_INSTANCE = new QuestScoresModel(
			null, null);
			


	public QuestScoresModel(String checkins, String quests) {		
		this.points = checkins;
		this.quests= quests;

	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setPoints (String points) {
		AVAILABLE_INSTANCE.points = points;
	}

	public String getPoints() {
		return points;
	}
	
	public void setQuests (String experience) {
		AVAILABLE_INSTANCE.quests = quests;
	}

	public String getQuests() {
		return quests;
	}
				
	/*
	 * Methods.
	 */
		
	public static QuestScoresModel selectScore (String users_id, String quests_id) {
					
		ArrayList<HashMap<String,String>> scoresQuery
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM users_scores_quests WHERE " +
				"users_id='"+users_id+"' AND " +
				"quests_id='"+quests_id+"'";				
		scoresQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (scoresQuery.isEmpty() == false) {
			String points = scoresQuery.get(0).get("points").toString();
			String quests = scoresQuery.get(0).get("quests").toString();
			return new QuestScoresModel(points, quests);	
		}
		
		return error();				
	}
	
	public static QuestScoresModel error() {
		return AVAILABLE_INSTANCE;
	}
	
	
}
