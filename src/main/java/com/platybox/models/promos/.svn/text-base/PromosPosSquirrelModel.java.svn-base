package com.platybox.models.promos;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;


/*
 * This model was written to access the db by private methods, it's contents DO NOT COMPLY with
 * our specifications, e.g. places_id has been changed to placeid
 * */

public final class PromosPosSquirrelModel {

	/*
	 * Constructor
	 */	
	String positem;
	String pospromo;
	String posbadge;
	String posdepartment;
	String postable;
	String posseat;
	String posaddress;
		
	/*Declare a default error.*/
	private static final PromosPosSquirrelModel AVAILABLE_INSTANCE = new PromosPosSquirrelModel(
			null, null, null, null, null, null, null
			/*
			new String("error"),new String("error"),new String("error"),
			new String("error"),new String("error"),new String("error"), new String("error")
			*/
			);


	public PromosPosSquirrelModel(String positem, String pospromo, String posbadge,
							String posdepartment, String postable, String posseat, String posaddress) {
		this.positem = positem;
		this.pospromo = pospromo;
		this.posbadge = posbadge;
		this.posdepartment = posdepartment;
		this.postable = postable;
		this.posseat = posseat;
		this.posaddress = posaddress;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setPositem (String positem) {
		AVAILABLE_INSTANCE.positem = positem;
	}
	public void setPospromo (String pospromo) {
		AVAILABLE_INSTANCE.pospromo = pospromo;
	}
	public void setPosbadge (String posbadge) {
		AVAILABLE_INSTANCE.posbadge = posbadge;
	}
	public void setPosdepartment (String posdepartment) {
		AVAILABLE_INSTANCE.posdepartment = posdepartment;
	}
	public void setPostable (String postable){
		AVAILABLE_INSTANCE.postable = postable;
	}
	public void setPosseat (String posseat) {
		AVAILABLE_INSTANCE.posseat = posseat;
	}
	public void setPosaddress (String posaddress) {
		AVAILABLE_INSTANCE.posaddress = posaddress;
	}
	public String getPositem (){
		return positem;
	}
	public String getPospromo () {
		return pospromo;
	}
	public String getPosbadge () {
		return posbadge;
	}
	public String getPosdepartment () {
		return posdepartment;
	}
	public String getPostable () {
		return postable;
	}
	public String getPosseat () {
		return posseat;
	}
	public String getPosaddress () {
		return posaddress;
	}
			
	/*
	 * Methods.
	 */
		
	public static PromosPosSquirrelModel selectPos (String promos_id) {
					
		ArrayList<HashMap<String,String>> posQuery
				= new ArrayList<HashMap<String,String>>();
		
		//TODO: right now we are assuming that there will only be one system for
		//each place, we should iterate and create accordingly
		String strQuery = "SELECT * FROM promos_pos_squirrel "+
				"LEFT JOIN pos ON pos.id=promos_pos_squirrel.pos_id "+
				"WHERE promos_id='"+promos_id+"'";				
		posQuery = DatabaseUtils.executeQuery(strQuery);		
		if (posQuery.isEmpty() == false) {
			
			String positem = posQuery.get(0).get("positem").toString();
			String pospromo =posQuery.get(0).get("pospromo").toString();
			String posbadge = posQuery.get(0).get("posbadge").toString();
			String posdepartment = posQuery.get(0).get("posdepartment").toString();
			String postable = posQuery.get(0).get("postable").toString();
			String posseat = posQuery.get(0).get("posseat").toString();
			String posaddress = posQuery.get(0).get("posaddress").toString();
			
			return new PromosPosSquirrelModel(positem, pospromo, posbadge, posdepartment, postable, posseat, posaddress);	
		}
		
		return error();				
	}
	
	public static PromosPosSquirrelModel error() {
		return AVAILABLE_INSTANCE;
	}	
}
