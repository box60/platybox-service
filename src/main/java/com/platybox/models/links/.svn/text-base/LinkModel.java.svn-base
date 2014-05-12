package com.platybox.models.links;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.models.bits.BitModel;
import com.platybox.utils.database.DatabaseUtils;

public class LinkModel {

	/*
	 * Constructor
	 */
	
	private HashMap<String,String> link;
	private BitModel bit;
	
	/*Declare a default error.*/
	private static final LinkModel AVAILABLE_INSTANCE = new LinkModel(
			null, null
			/*
			new HashMap<String,String>(){private static final long serialVersionUID = 1L;{
				put("error", "empty response");
			}}, BitModel.error()
			*/
			);

	public LinkModel (HashMap<String,String> link, BitModel bit) {		
		this.link = link;
		this.bit = bit;
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setLink (HashMap<String,String> link) {
		AVAILABLE_INSTANCE.link = link;
	}
	public HashMap<String,String> getLink() {
		return link;
	}
	
	public void setBit (BitModel bit) {
		AVAILABLE_INSTANCE.bit = bit;
	}
	
	public BitModel getBit () {
		return bit;
	}
	
	/*
	 * Methods.
	 */
	
	public static LinkModel selectLink (String links_id) {
		
		ArrayList<HashMap<String,String>> linksQuery
				= new ArrayList<HashMap<String,String>>();
		
		String strQuery = "SELECT * FROM links WHERE " +
						"id=" + links_id;
		
		linksQuery = DatabaseUtils.executeQuery(strQuery);
		if (linksQuery.isEmpty() == false ){
			String bits_id = linksQuery.get(0).get("link_bits_id");
			return new LinkModel (linksQuery.get(0), BitModel.selectBit(bits_id));
		}
		else
			return error();		
	}	
	
	public static LinkModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
