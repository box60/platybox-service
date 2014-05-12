package com.platybox.models.friends;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


import javax.servlet.ServletException;

import com.platybox.utils.database.DatabaseUtils;

public class FriendsModel {
	/*
	 * Constructor
	 */
	
	private ArrayList<FriendModel> friends;

	/*Declare a default error.*/
	private static final FriendsModel AVAILABLE_INSTANCE = new FriendsModel (
			null
			/*
			new ArrayList<FriendModel> () {private static final long serialVersionUID = 1L;{
				add (FriendModel.error()); //(PlaceModel.error();
			}}
			*/
	);
	
	public FriendsModel(ArrayList<FriendModel> friends) {		
		this.friends= friends;
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setFriends (ArrayList<FriendModel> friend) {
		AVAILABLE_INSTANCE.friends = friends;
	}

	public ArrayList<FriendModel> getFriends() {
		return friends;
	}
	
	
	
	public static FriendsModel selectFriends (String users_id) {
		ArrayList<FriendModel> friends
				= new ArrayList<FriendModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT id " + 
					"FROM friends WHERE users_id="+users_id;
		
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				friends.add(FriendModel.selectFriend(id.get("id")));				
			}
			return new FriendsModel(friends);			
		} else
			return error();
	}
		
	public static FriendsModel insertFriend (String users_id,
									String friend_users_id) {
		String strQuery = "SELECT * FROM friends WHERE " +
		"users_id="+users_id+" AND " +
		"friend_users_id="+friend_users_id;
		
		if (DatabaseUtils.executeQuery(strQuery).isEmpty()) { 		
			String strUpdate = "INSERT INTO friends (users_id, friend_users_id) " +
				"VALUES ('"+users_id+"','"+friend_users_id+"')";
			DatabaseUtils.executeUpdate(strUpdate); 					
		}
		return selectFriends(users_id);
	}
	
	public static FriendsModel deleteFriend (String users_id,
									String friend_users_id) {
		String strUpdate="DELETE FROM friends WHERE " +
						"users_id="+users_id+" AND friend_users_id="+friend_users_id;
		DatabaseUtils.executeUpdate(strUpdate);
		return selectFriends(users_id);		
	}
		
	public static FriendsModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
