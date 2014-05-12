package com.platybox.email;

// Based on Krams's tutorial:   
// http://krams915.blogspot.com/2011/01/spring-3-task-scheduling-via.html

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.platybox.models.emails.EmailsQueueModel;
import com.platybox.models.users.UserModel;

/**
 * Scheduler for handling jobs
 */
@Service
public class SchedulerService {

	protected static Logger logger = Logger.getLogger("service");

	@Autowired
	@Qualifier("asyncWorker")
	private Worker worker;
	
	//TODO: change rate for production 
	//@Scheduled(fixedDelay=5000)
	@Scheduled(fixedRate=60000)
	//@Scheduled(cron="*/5 * * * * ?")
	public void doSchedule() {
		
		ArrayList<HashMap<String,String>> emails = 
							new ArrayList<HashMap<String,String>> (); 
		
		emails = EmailsQueueModel.showQueue().getEmails();  		
		Iterator<HashMap<String,String>> itr = emails.iterator();		
		while (itr.hasNext()){
			HashMap<String,String> element = itr.next();
			String id = element.get("id");
			String email = element.get("email");
			String type = element.get("type");
			String users_id = element.get("users_id");
			String promos_id = element.get("promos_id");
			String processing = element.get("processing");
			if (processing.equalsIgnoreCase("0")) {
				worker.work(id, email, type, users_id, promos_id);
				EmailsQueueModel.processEmailFromQueue(id);
			}
		}
		
	}
	

}
