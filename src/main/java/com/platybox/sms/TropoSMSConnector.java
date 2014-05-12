package com.platybox.sms;

import java.io.StringReader;
import java.net.URLEncoder;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

/*
 * 
 * This module depends on our implementation of Tropo.com. Specifically for the platybox-sms app running on it.
 * 
 * */

public class TropoSMSConnector {

	String numberToDial = null;
	String msg = null;
	
	String tropoToken = "04f9b5429dd03143a38ad4eaadaeed2629cf50b3854d263990b7f77c0066591691fa00afa0490c52f156dcff";
	String tropoApp = "http://api.tropo.com/1.0/sessions?action=create&token=" + tropoToken;
	
	public TropoSMSConnector (String numberToDial, String msg) {
		this.numberToDial = numberToDial;
		this.msg = msg;
	}

	public Boolean sendSMS () {
		
		String url = tropoApp + "&numberToDial="+this.numberToDial+"&msg="+URLEncoder.encode(this.msg);
		
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));        
        
        try {
            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {                
                System.err.println("Method failed: " + 
                        method.getStatusLine());
            }
            
            byte[] responseBody = method.getResponseBody();
            return isSuccess(new String(responseBody));
                                 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return false;
	}
	
	private static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}
	
	private static boolean isSuccess (String responseBody) {
		String stringToParse = "<data>" + responseBody + "</data>";

		try {
			DocumentBuilderFactory dbf =
				DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(stringToParse));
			Document doc = db.parse(is);
			
			NodeList nodes = doc.getElementsByTagName("session");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				NodeList name = element.getElementsByTagName("success");
				Element line = (Element) name.item(0);
				String isSuccess = getCharacterDataFromElement(line);
				
				if (isSuccess.equalsIgnoreCase("true"))
					return true;
				else 
					return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
