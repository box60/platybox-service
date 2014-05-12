package com.platybox.models.coupons;

import java.util.ArrayList;
import java.util.HashMap;

import com.platybox.utils.database.DatabaseUtils;

public class CouponsModel {
	
	/*
	 * Constructor
	 */
	
	ArrayList<HashMap<String,String>> coupons;	
	
	
	/*Declare a default error.*/
	private static final CouponsModel AVAILABLE_INSTANCE = new CouponsModel(
			null);
	
	public CouponsModel(ArrayList<HashMap<String,String>> coupons) {		
		this.coupons = coupons;		
	}
	
	/*
	 * Getters and setters.
	 */
	
	public void setCoupons (ArrayList<HashMap<String,String>>coupons) {
		AVAILABLE_INSTANCE.coupons=coupons;
	}
	public ArrayList<HashMap<String,String>> getCoupons() {
		return coupons;
	}
	
	/*
	 * Methods.
	 */	
	
	public static ArrayList<HashMap<String,String>> findCoupons () {		
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>> ();

		//TODO: create a coupon model and add a place info here.
		String strQuery = "SELECT coupons_types.type, coupons.id,coupons.places_id,coupons.quests_id,coupons.bits_id,coupons.price,coupons.name," +
		"coupons.description,coupons.expires,coupons.left FROM coupons " +
		"LEFT JOIN coupons_types ON coupons_types.id=coupons.coupons_types_id WHERE available=1 AND coupons.left>0";
		
		data = DatabaseUtils.executeQuery(strQuery);	
		if (data.isEmpty() || Integer.parseInt(data.get(0).get("left")) <  1)
			return null;
		else
			return data;
	}
	/*
	 * We will add some of the place data here, although it's not proper to do this.
	 * */
	public static CouponsModel findCouponsAsModel () {		
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>> ();		

		String strQuery = "SELECT coupons_types.type, coupons.id,coupons.places_id,coupons.quests_id,coupons.bits_id,coupons.price,coupons.name," +
		"coupons.description,coupons.expires,coupons.left FROM coupons " +
		"LEFT JOIN coupons_types ON coupons_types.id=coupons.coupons_types_id WHERE available=1 AND coupons.left>0";
		
		data = DatabaseUtils.executeQuery(strQuery);
		if (data.isEmpty())
			return error();	
		else 			
			return new CouponsModel(data);
		
	}

	/**
	 * Searches for previously purchased coupons. (for givaway coupons)
	 * @param users_id
	 * @param coupons_id
	 * @return true if coupon has never before been purchased
	 */
	public static boolean couponHasNotBeenPurchased (String users_id, String coupons_id) {
		String strQuery = "SELECT id FROM coupons_purchased " +
				"WHERE users_id='"+users_id+"' AND coupons_id='"+coupons_id+"'";
		return DatabaseUtils.executeQuery(strQuery).isEmpty();
	}
	
	public static void logCouponPurchased (String users_id, String coupons_id) {
		String strUpdate = "INSERT INTO coupons_purchased (users_id, coupons_id) " +
				"VALUES ('"+users_id+"','"+coupons_id+"')";
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static void consumeCoupon (String id) {
		String strUpdate = "UPDATE coupons SET coupons.left=coupons.left-1 WHERE id="+id;
		DatabaseUtils.executeUpdate(strUpdate);
	}
	
	public static CouponsModel error() {
		return AVAILABLE_INSTANCE;
	}		
	
}
