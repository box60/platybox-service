package com.platybox.models.quests;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class QuestsBadgeModel {

	/*
	 * Constructor
	 */
	
	private String name;
	private String image_url;
	
	/*Declare a default error.*/
	private static final QuestsBadgeModel AVAILABLE_INSTANCE = new QuestsBadgeModel(
			null, null
	);

	public QuestsBadgeModel (String name, String image_url) {		
		this.name = name;
		this.image_url = image_url;

	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setName(String name) {
		AVAILABLE_INSTANCE.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setImage_url(String image_url) {
		AVAILABLE_INSTANCE.image_url = image_url;
	}
	public String getImage_url() {
		return image_url;
	}

		
	/*
	 * Methods.
	 */
	
	public static QuestsBadgeModel selectBadge (String id) {
		
		ArrayList<HashMap<String,String>> badgeQuery
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM quests_badges WHERE " +
						"id=" + id;
		
		badgeQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (badgeQuery.isEmpty() == false ){
			String name = badgeQuery.get(0).get("name");
			String image_url = badgeQuery.get(0).get("image_url");
			return new QuestsBadgeModel(name, image_url);
		}
		else
			return error();		
	}	
	
	public static QuestsBadgeModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
