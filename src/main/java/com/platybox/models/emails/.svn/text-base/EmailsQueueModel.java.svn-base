package com.platybox.models.emails;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class EmailsQueueModel {
	
	/*
	 * Constructor
	 */
	
	ArrayList<HashMap<String,String>> emails; 
	
	/*Declare a default error.*/
	private static final EmailsQueueModel AVAILABLE_INSTANCE = new EmailsQueueModel(
			null);

	public EmailsQueueModel(ArrayList<HashMap<String,String>> emails) {		
		this.emails = emails;		
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setEmails (ArrayList<HashMap<String,String>>emails) {
		AVAILABLE_INSTANCE.emails= emails;
	}
	public ArrayList<HashMap<String,String>> getEmails() {
		return emails;
	}
		
	/*
	 * Methods.
	 */
	
	public static EmailsQueueModel showQueue () {		
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>> ();		
		String strQuery = "SELECT emails_queue.id, emails_queue.users_id, emails_queue.promos_id, " +
				"users.email, emails_queue_types.type, emails_queue.processing " +
				"FROM emails_queue " +
				"LEFT JOIN emails_queue_types ON emails_queue_types_id=emails_queue_types.id " +
				"LEFT JOIN users ON emails_queue.users_id=users.id";
		data = DatabaseUtils.executeQuery(strQuery);		
		return new EmailsQueueModel(data);				
	}

	public static void addEmailToQueue (String users_id, String type) {		
		String strUpdate = "INSERT INTO emails_queue (users_id,emails_queue_types_id) " +
				"VALUES ("+users_id+",(SELECT id FROM emails_queue_types WHERE type='"+type+"'))";
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void addPromoEmailToQueue (String users_id, String type, String promos_id) {		
		String strUpdate = "INSERT INTO emails_queue (users_id,emails_queue_types_id, promos_id) " +
			"VALUES ("+users_id+",(SELECT id FROM emails_queue_types WHERE type='"+type+"'), '"+promos_id+"')";
		DatabaseUtils.executeUpdate(strUpdate);
	}
		
	public static void processEmailFromQueue (String id) {		
		String strUpdate = "UPDATE emails_queue SET processing=true WHERE id="+id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
		
	public static void deleteEmailFromQueue (String id) {		
		String strUpdate = "DELETE FROM emails_queue WHERE id="+id;
		DatabaseUtils.executeUpdate(strUpdate);
	}	
	
	public static EmailsQueueModel error() {
		return AVAILABLE_INSTANCE;
	}		
	
}
