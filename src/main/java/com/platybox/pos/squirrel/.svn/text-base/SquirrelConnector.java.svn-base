package com.platybox.pos.squirrel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

/*
 * 
 * TODO: implement properly.
 * 
 * */

public class SquirrelConnector {
	
	String positem;
	String pospromo;
	String posbadge;
	String posdepartment;
	String postable;
	String posseat;
	String posaddress;
	
	public SquirrelConnector (String positem, String pospromo, String posbadge, String posdepartment,
			String postable, String posseat, String posaddress) {
		this.positem = positem;
		this.pospromo = pospromo;
		this.posbadge = posbadge;
		this.posdepartment = posdepartment;
		this.postable = postable;
		this.posseat = posseat;
		this.posaddress = posaddress;
	}

	public Boolean sendOrder () {
		try {
			String queryString = "positem=" +this.positem +
								 "&posaddress=" + this.posaddress +
								 "&pospromo=" + this.pospromo+
								 "&posbadge=" + this.posbadge+
								 "&posdepartment=" + this.posdepartment+								 
								 "&postable=" + this.postable+
								 "&posseat=" + this.posseat;
			
			URL url = new URL("http://localhost:8080/squirrelconnector/order"); //TODO: change this shit dude, wtf!
			URLConnection urlConnection = url.openConnection();
			urlConnection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			
			out.write(queryString);
			out.flush();
			
			//read response
			BufferedReader in
					= new BufferedReader (new InputStreamReader (urlConnection.getInputStream()));
			
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			out.close();
			in.close();
			
			//TODO: check for success.
			return true;
		} catch (Exception e) {
			//
		}
        return false;
	}
	
	
	
	private static boolean isSuccess (String responseBody) {
		return true;
	}
	
}
