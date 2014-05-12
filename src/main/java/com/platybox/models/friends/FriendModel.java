package com.platybox.models.friends;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.models.users.UserModel;
import com.platybox.utils.database.DatabaseUtils;

public class FriendModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> friend;
	private UserModel user;
	
	/*Declare a default error.*/
	private static final FriendModel AVAILABLE_INSTANCE = new FriendModel(
			null, null
			/*
			new HashMap<String,String>(){private static final long serialVersionUID = 1L;{
				put("error", "empty response");
			}}, UserModel.error()
			*/
			);

	public FriendModel (HashMap<String,String> friend, UserModel user) {		
		this.friend = friend;
		this.user = user;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setFriend (HashMap<String,String> friend) {
		AVAILABLE_INSTANCE.friend = friend;
	}
	public HashMap<String,String> getFriend() {
		return friend;
	}
	
	public void setUser (UserModel bit) {
		AVAILABLE_INSTANCE.user = user;
	}
	
	public UserModel getUser() {
		return user;
	}
	
	/*
	 * Methods.
	 */
	
	public static FriendModel selectFriend (String friends_id) {
		
		ArrayList<HashMap<String,String>> linksQuery
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM friends WHERE " +
						"id=" + friends_id;
		
		linksQuery = DatabaseUtils.executeQuery(strQuery);
		if (linksQuery.isEmpty() == false ){
			String users_id = linksQuery.get(0).get("friend_users_id");
			return new FriendModel (linksQuery.get(0), UserModel.selectUser(users_id));
		}
		else
			return error();		
	}	
	
	public static FriendModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
