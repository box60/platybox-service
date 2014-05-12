package com.platybox.models.quests;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.models.bits.BitModel;
import com.platybox.models.places.PlaceModel;
import com.platybox.utils.CharEscaper;
import com.platybox.utils.database.DatabaseUtils;


public class QuestGoalModel {
	
	/*
	 * Constructorsn't 
	 */
		
	private BitModel bit;
	private PlaceModel place;
	private String completed;
		
	/*Declare a default error.*/
	private static final QuestGoalModel AVAILABLE_INSTANCE = new QuestGoalModel(
			null, null, null
			/*
			BitModel.error(), PlaceModel.error(), new String("error")
			*/
			);
	
	public QuestGoalModel(BitModel bit, PlaceModel place, String completed) {		
		this.bit = bit;
		this.place = place;		
		this.completed = completed;
	}
	
	/*
	 * Getters and setters.
	 */

	public void setBit(BitModel bit) {
		AVAILABLE_INSTANCE.bit= bit;
	}
	
	public BitModel getBit() {
		return bit;
	}
	
	public void setPlace (PlaceModel place) {
		AVAILABLE_INSTANCE.place = place;
	}
	
	public PlaceModel getPlace () {
		return place;
	}

	public void setCompleted (String completed){
		AVAILABLE_INSTANCE.completed = completed;
	}
	
	public String getCompleted () {
		return completed;
	}
	
	/*
	 * Methods.
	 */
	
	public static QuestGoalModel selectQuestBit (String users_id, String quests_bits_id) {
		
		ArrayList<HashMap<String,String>> questsBitsQuery
			= new ArrayList<HashMap<String,String>>();		
		
		String strQuery = "SELECT quests_bits.bits_id, bits.places_id FROM quests_bits " +
				"LEFT JOIN bits ON bits.id=quests_bits.bits_id" +
				"WHERE quests_bits.id="+quests_bits_id;
		questsBitsQuery = DatabaseUtils.executeQuery(strQuery);
		
		if (questsBitsQuery.isEmpty() == false ) {
			String bits_id = questsBitsQuery.get(0).get("bits_id");
			String places_id = questsBitsQuery.get(0).get("places_id");
			String completed = isCompleted(quests_bits_id, users_id);
			return new QuestGoalModel(BitModel.selectBitInfo(bits_id, users_id),
										PlaceModel.selectPlace(places_id, users_id), completed);	
		} else {
			return error();
		}		
	}
	
	public static QuestGoalModel selectQuestBit (String users_id, String bits_id,
								String places_id, String quests_bits_id) {
		
			String completed = isCompleted(quests_bits_id, users_id); 
			return new QuestGoalModel(BitModel.selectBitInfo(bits_id, users_id),
										PlaceModel.selectPlace(places_id, users_id), completed);			
	}
			
	public static QuestGoalModel error() {
		return AVAILABLE_INSTANCE;
	}
	
	private static String isCompleted (String quests_bits_id, String users_id) {
		
		ArrayList<HashMap<String,String>> query
			= new ArrayList<HashMap<String,String>>();
				
		String strQuery = "SELECT id FROM checkins_quests_bits " +
				"WHERE quests_bits_id="+quests_bits_id+" AND users_id="+users_id;
		query = DatabaseUtils.executeQuery(strQuery);
		
		if (query.isEmpty() == false)
			return "1";
		else 
			return "0";
		
	}
	

}