package com.platybox.models.quests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import javax.servlet.ServletException;

import com.platybox.utils.database.DatabaseUtils;

public class QuestsBadgesModel {
	/*
	 * Constructor
	 */
	
	private ArrayList<QuestsBadgeModel> badges;

	/*Declare a default error.*/
	private static final QuestsBadgesModel AVAILABLE_INSTANCE = new QuestsBadgesModel (
			null
	);
	
	public QuestsBadgesModel(ArrayList<QuestsBadgeModel> badges) {		
		this.badges= badges;
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setBadges (ArrayList<QuestsBadgeModel> badges) {
		AVAILABLE_INSTANCE.badges = badges;
	}

	public ArrayList<QuestsBadgeModel> getBadges() {
		return badges;
	}
	
	public static QuestsBadgesModel selectUserBadges (String users_id) {
		ArrayList<QuestsBadgeModel> badges
				= new ArrayList<QuestsBadgeModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT quests_badges_id FROM quests " +
				"LEFT JOIN users_quests ON users_quests.quests_id=quests.id " +
				"WHERE users_quests.users_id="+users_id;				
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> quest : queryResult ) {
				String id = quest.get("quests_badges_id");
				QuestsBadgeModel badge = QuestsBadgeModel.selectBadge(id);				
				badges.add(badge);
			}
			return new QuestsBadgesModel(badges);			
		} else
			return error();
	}
		
		
	public static QuestsBadgesModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
