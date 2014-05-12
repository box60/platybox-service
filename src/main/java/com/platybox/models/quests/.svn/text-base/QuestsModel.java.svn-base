package com.platybox.models.quests;

import java.util.ArrayList;
import java.util.HashMap;
import com.platybox.utils.IcalScheduler;
import com.platybox.utils.database.DatabaseUtils;

public class QuestsModel {
	
	/*
	 * Constructor
	 */
		
	private ArrayList<QuestModel> quests;
	private ArrayList<QuestsBadgeModel> badges; //badges owned by this person.

	
	/*Declare a default error.*/
	private static final QuestsModel AVAILABLE_INSTANCE = new QuestsModel (
			null, null
	);
	
	public QuestsModel(ArrayList<QuestModel> quests,  ArrayList<QuestsBadgeModel> badges) {		
		this.quests = quests;
		this.badges = badges;
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setPromos (ArrayList<QuestModel> quests) {
		AVAILABLE_INSTANCE.quests= quests;
	}

	public ArrayList<QuestModel> getQuests() {
		return quests;
	}
	

	public void setBadges (ArrayList<QuestsBadgeModel> badges) {
		AVAILABLE_INSTANCE.badges = badges;
	}
	public ArrayList<QuestsBadgeModel> getBadges () {
		 return badges;
	}
	
	
	/*
	 * Methods.
	 */
	
	public static QuestsModel searchAvailableQuests (String users_id) {
		
		ArrayList<QuestModel> quests = new ArrayList<QuestModel> ();	
		ArrayList<HashMap<String,String>> queryResult
			= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM quests " +
					"WHERE available=true";
		
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {
				QuestModel quest = QuestModel.selectQuest(id.get("id"), users_id);
				quests.add( quest );				
			}						
		}
		ArrayList<QuestsBadgeModel> badges = QuestsBadgesModel.selectUserBadges(users_id).getBadges(); 
		return new QuestsModel(quests, badges);		
	}
	
	public static QuestsModel error() {
		return AVAILABLE_INSTANCE;
	}

}
