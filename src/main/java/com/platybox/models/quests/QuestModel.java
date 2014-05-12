package com.platybox.models.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.platybox.utils.CouponsUtils;
import com.platybox.utils.IcalScheduler;
import com.platybox.utils.database.DatabaseUtils;

public class QuestModel {
	
	/*
	 * Constructor
	 */
	
	private HashMap<String,String> quest;
	private ArrayList<QuestGoalModel> goals;
	private QuestScoresModel scores;
	private String completed;

	/*Declare a default error.*/
	private static final QuestModel AVAILABLE_INSTANCE = new QuestModel (
			null, null, null, null
			/*
			new HashMap<String,String>(){private static final long serialVersionUID = 1L;{
				put("error", "empty response");
			}} ,
			new ArrayList<QuestGoalModel> () {private static final long serialVersionUID = 1L;{
				add (QuestGoalModel.error()); //(PlaceModel.error();
			}},
			QuestScoresModel.error(),
			new String("Error")
			*/
	);
	
	public QuestModel(HashMap<String,String> quest ,ArrayList<QuestGoalModel> goals,
						  	QuestScoresModel scores, String completed) {
		this.quest = quest;
		this.goals = goals;
		this.scores = scores;
		this.completed = completed;
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setQuest (HashMap<String,String> quest) {
		AVAILABLE_INSTANCE.quest = quest;
	}
	public HashMap<String,String> getQuest() {
		return quest;
	}
	
	public void setPromos (ArrayList<QuestGoalModel> goals) {
		AVAILABLE_INSTANCE.goals = goals;
	}

	public ArrayList<QuestGoalModel> getGoals() {
		return goals;
	}
	
	public void setScores (QuestScoresModel scores){
		AVAILABLE_INSTANCE.scores = scores;
	}
	
	public QuestScoresModel getScores () {
		return scores;
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
	
	public static QuestModel selectQuest (String quests_id, String users_id) {
		ArrayList<QuestGoalModel> goals
				= new ArrayList<QuestGoalModel>();
		
		ArrayList<HashMap<String,String>> queryResult
		= new ArrayList<HashMap<String,String>>();
				
		String strQuery = "SELECT quests_bits.bits_id, quests_bits.id , bits.places_id " +
				"FROM quests_bits " +
				"LEFT JOIN bits ON bits.id=quests_bits.bits_id " +
				"WHERE quests_bits.quests_id="+quests_id;
		queryResult = DatabaseUtils.executeQuery(strQuery);
				
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {
				String quests_bits_id = id.get("id");
				String bits_id = id.get("bits_id");
				String places_id = id.get("places_id");				
				goals.add( QuestGoalModel.selectQuestBit(users_id, bits_id, places_id, quests_bits_id));				
			}
			
			strQuery = "SELECT id, name, description FROM quests WHERE id="+quests_id;
			queryResult = DatabaseUtils.executeQuery(strQuery);

			String completed = isCompleted(users_id,quests_id);
			
			return new QuestModel(queryResult.get(0), goals, 
						QuestScoresModel.selectScore(users_id, quests_id), completed);			
		} else
			return error();
	}
	
	
	
	//TODO: THIS SHOULD BE PUT INTO A UTILS STATIC CLASS AS IT CALLS DIFFERENT MODELS: users_scores, quests_scores, etc
	/**
	 * A very special case that registers a checkin, marks a quest as completed if finished and logs quest
	 * and user scores.
	 * 
	 * @param bits_id
	 * @param users_id
	 */
	public static void checkinQuest (String bits_id, String users_id) {
		
		//log quest checkin and quest score for each bit goals from available quests.   
		String strQuery = "SELECT quests_bits.*, quests.available FROM quests_bits " +
				"LEFT JOIN quests ON quests.id=quests_bits.quests_id " +
				"WHERE quests_bits.bits_id="+bits_id+" AND quests.available=true";
		ArrayList<HashMap<String,String>> questsPlaces = DatabaseUtils.executeQuery(strQuery);  
		Iterator<HashMap<String,String>> itr = questsPlaces.iterator();
		while (itr.hasNext()) {
			HashMap<String,String> row = itr.next(); 
			String quests_id = row.get("quests_id");
			String quests_bits_id = row.get("id");
			String value =  row.get("value");
			strQuery = "SELECT * FROM checkins_quests_bits " +
					"WHERE users_id="+users_id+" AND quests_bits_id="+quests_bits_id;
			if (DatabaseUtils.executeQuery(strQuery).isEmpty()) {//if no checking before has happened
				//log place quest checkin
				String strUpdate = "INSERT INTO checkins_quests_bits (users_id, quests_bits_id) " +
						"VALUES ("+users_id+", '"+quests_bits_id+"')";
				DatabaseUtils.executeUpdate(strUpdate);


				//log place quest score
				strQuery = "SELECT id FROM users_scores_quests WHERE " +
						"users_id="+users_id+" AND " +
						"quests_id="+quests_id;
				if (DatabaseUtils.executeQuery(strQuery).size() >= 1) {
					strUpdate = "UPDATE users_scores_quests " +
							"SET points=points+"+value+" WHERE " +
							"users_id="+users_id+" AND " +
							"quests_id ="+quests_id;
				} else {
					strUpdate = "INSERT INTO users_scores_quests (points, users_id, quests_id) " +
							"VALUES ("+value+", '"+users_id+"', '"+quests_id+"')";							
				}
				DatabaseUtils.executeUpdate(strUpdate);

				//log if this quest is finished, add one point of experience.
				//TODO: this is a hack, this should be cleaner.
				//TODO: converting a string from the db to integer is just wrong. please change this.
				ArrayList<HashMap<String,String>> query = new ArrayList<HashMap<String,String>>();
				strQuery = "SELECT quests_bits_value_needed FROM quests WHERE id="+quests_id;						
				query = DatabaseUtils.executeQuery(strQuery);		
				Integer valueBitsNeeded = Integer.parseInt(query.get(0).get("quests_bits_value_needed"));

				strQuery = "SELECT SUM(value) FROM checkins_quests_bits " +
						"LEFT JOIN quests_bits ON checkins_quests_bits.quests_bits_id=quests_bits.id " +
						"WHERE users_id="+users_id+" AND quests_id="+quests_id;
				query = DatabaseUtils.executeQuery(strQuery);
				String collected = query.get(0).get("SUM(value)");
				Integer valueBitsCollected;						
				if (collected != null)
					valueBitsCollected = Integer.parseInt(collected);			
				else valueBitsCollected = 0;


				strQuery = "SELECT * FROM users_quests WHERE users_id='"+users_id+"' AND quests_id='"+quests_id+"'";
				query = DatabaseUtils.executeQuery(strQuery);
				boolean questNotFinished = query.isEmpty();

				//add users_scores, quests_scores and quests finished.
				if (valueBitsCollected >= valueBitsNeeded && questNotFinished) {
					strUpdate = "UPDATE users_scores SET quests=quests+1 " +
							"WHERE users_id="+users_id;
					DatabaseUtils.executeUpdate(strUpdate);

					strUpdate = "UPDATE users_scores_quests SET quests=quests+1 " +
							"WHERE users_id="+users_id;
					DatabaseUtils.executeUpdate(strUpdate);

					strUpdate = "INSERT INTO users_quests (users_id, quests_id) " +
							"VALUES ('"+users_id+"', '"+quests_id+"')";		
					DatabaseUtils.executeUpdate(strUpdate);

					//give a quest coupon as advertised
					/*Search for and give a register coupon if available*/
					CouponsUtils.giveQuestCoupon(users_id, quests_id);

				}

			}

		}
	}
	
	
	
	public static QuestModel error() {
		return AVAILABLE_INSTANCE;
	}

	private static String isCompleted (String users_id, String quests_id) {
		
		ArrayList<HashMap<String,String>> query
			= new ArrayList<HashMap<String,String>>();


		String strQuery = "SELECT quests_bits_value_needed FROM quests WHERE id="+quests_id;						
		query = DatabaseUtils.executeQuery(strQuery);		
		Integer valueBitsNeeded = Integer.parseInt(query.get(0).get("quests_bits_value_needed"));
								
		strQuery = "SELECT SUM(value) FROM checkins_quests_bits " +
				"LEFT JOIN quests_bits ON checkins_quests_bits.quests_bits_id=quests_bits.id " +
				"WHERE users_id="+users_id+" AND quests_id="+quests_id;
		query = DatabaseUtils.executeQuery(strQuery);
		String collected = query.get(0).get("SUM(value)");
		if (collected != null)  {
			Integer valueBitsCollected = Integer.parseInt(collected);			
			if (valueBitsCollected >= valueBitsNeeded )
				return "1";
		}
		return "0";
		
	}
	
	
}
