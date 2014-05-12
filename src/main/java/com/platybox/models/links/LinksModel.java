package com.platybox.models.links;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class LinksModel {
	/*
	 * Constructor
	 */
	
	private ArrayList<LinkModel> links;

	/*Declare a default error.*/
	private static final LinksModel AVAILABLE_INSTANCE = new LinksModel (
			null
			/*
			new ArrayList<LinkModel> () {private static final long serialVersionUID = 1L;{
				add (LinkModel.error()); //(PlaceModel.error();
			}}
			*/
	);
	
	public LinksModel(ArrayList<LinkModel> links) {		
		this.links= links;
	}
		
	/*
	 * Getters and setters.
	 */
	
	public void setLinks (ArrayList<LinkModel> link) {
		AVAILABLE_INSTANCE.links = links;
	}

	public ArrayList<LinkModel> getLinks() {
		return links;
	}
	
	public static LinksModel selectLinks (String users_id) {
		ArrayList<LinkModel> links
				= new ArrayList<LinkModel>();		
		ArrayList<HashMap<String,String>> queryResult
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT id " + 
					"FROM links WHERE users_id="+users_id;
		
		queryResult = DatabaseUtils.executeQuery(strQuery);
		
		if (queryResult.isEmpty() == false ){
			for (HashMap<String,String> id : queryResult ) {						
				links.add(LinkModel.selectLink(id.get("id")));				
			}
			return new LinksModel(links);			
		} else
			return error();
	}
		
	public static LinksModel insertLink (String users_id,
									String link_bits_id) {
		
		String strQuery = "SELECT * FROM links WHERE " +
								"users_id="+users_id+" AND " +
								"link_bits_id="+link_bits_id;
			
		if (DatabaseUtils.executeQuery(strQuery).isEmpty()) { 			
			String strUpdate = "INSERT INTO links (users_id, link_bits_id) " +
								"VALUES ('"+users_id+"','"+link_bits_id+"')";
			DatabaseUtils.executeUpdate(strUpdate); 
			
		}
		return selectLinks(users_id);		
	}
	
	public static LinksModel deleteLink (String users_id,
										String link_bits_id) {
		
		String strUpdate="DELETE FROM links WHERE " +
						"users_id="+users_id+" AND link_bits_id="+link_bits_id;
		DatabaseUtils.executeUpdate(strUpdate);
		return selectLinks(users_id);
		
	}
			
	public static LinksModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
