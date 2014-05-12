package com.platybox.utils;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class CreateBitQR {
	
	int id;
	int width;
	int height;
		
	public CreateBitQR (int id) {
		this.id = id;
		this.width = 300;  
    	this.height = 300;    	
	}

	public String getFileName (int id) {
		String fileName = null;
		return fileName;
	}
		
	/**
	 * Creates a QRBit containig 
	 * 
	 * @return url where this image can be found 
	 */

	public String create () {
		
		
		//String serverName = request.getServerName();
		//String contextName = context.getContextPath();
	    
	    // servlet that resolves a visit with external client
		//String visitServlet = serverName + contextName + "/qr/";
				
		String imageFormat = "gif"; // could be "gif", "tiff", "jpeg"
		String qrFilename = String.valueOf(this.id) + "." + imageFormat;
		
		/*We now host in Apache and save accordingly*/
		String outPath = "/var/www/img/qr/";		
		String qrImageUrl = "http://img.platybox.com/qr/" + qrFilename;	
		
		String qrContent  = "http://api.platybox.com/1/qr/" + String.valueOf(this.id);
		//String qrContent  = "http://" + visitServlet + String.valueOf(this.id);

    	try {    		
	    	BitMatrix bitMatrix = new QRCodeWriter().encode(qrContent, BarcodeFormat.QR_CODE, width, height);  
	    	MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File (outPath + qrFilename)));	    	
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return qrImageUrl;
		
	}
		
	
}


