package com.platybox.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.Kernel;

import java.awt.RenderingHints;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import java.awt.image.ConvolveOp;

import org.springframework.web.multipart.MultipartFile;

import sun.awt.image.BufferedImageGraphicsConfig;

    public class ImageUtils {

	

    private static BufferedImage resizeImage(BufferedImage image, int type, int width, int height){
    	BufferedImage resizedImage = new BufferedImage(width, height, type);
    	Graphics2D g = resizedImage.createGraphics();
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    	g.drawImage(image, 0, 0, width, height, null);
    	g.dispose();
    	blurImage(resizedImage); //a trick to make it an apparently better quality.
    	return resizedImage;
    }
    
    private static BufferedImage cropImageSquare (BufferedImage originalImage, int type)
    {
    	int w = originalImage.getWidth();
    	int h = originalImage.getHeight();
    	
    	int squareSize;
    	if (w<=h) squareSize = w; else squareSize = h;
    	    	
        BufferedImage croppedImage = new BufferedImage(squareSize, squareSize, type);
        Graphics2D g = croppedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage (originalImage, 0,0, squareSize,squareSize, (w-squareSize)/2, (h-squareSize)/2, squareSize+((w-squareSize)/2), squareSize+((h-squareSize)/2), null);
        g.dispose();
        return croppedImage;
    }
    
    public static BufferedImage blurImage(BufferedImage image) {
    	float ninth = 1.0f/9.0f;
    	float[] blurKernel = {
    	ninth, ninth, ninth,
    	ninth, ninth, ninth,
    	ninth, ninth, ninth
    	};
    	image = createCompatibleImage(image);
    	Map map = new HashMap();
    	map.put(RenderingHints.KEY_INTERPOLATION,
    	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    	map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	RenderingHints hints = new RenderingHints(map);
    	BufferedImageOp op = new ConvolveOp(new Kernel(3, 3, blurKernel), ConvolveOp.EDGE_NO_OP, hints);
    	return op.filter(image, null);
    }
    
    private static BufferedImage createCompatibleImage(BufferedImage image) {
    	GraphicsConfiguration gc = BufferedImageGraphicsConfig.getConfig(image);
    	int w = image.getWidth();
    	int h = image.getHeight();
    	BufferedImage result = gc.createCompatibleImage(w, h, Transparency.TRANSLUCENT);
    	Graphics2D g2 = result.createGraphics();
    	g2.drawRenderedImage(image, null);
    	g2.dispose();
    	return result;
    }
    
    
    private static BufferedImage createAvatar (BufferedImage image, int type) {   	
    	if (image.getWidth()!=image.getHeight()) 
    		 image = cropImageSquare(image, type);    	    	    	    
    	return image;
    }
    
    private static void saveImage (BufferedImage image, String users_id, String filename) throws IOException {
    	String directory = "/var/www/img/pr/" + users_id;    	
    	if (!new File(directory).exists())
    		new File(directory).mkdirs();
    	ImageIO.write(image, "jpg", new File("/var/www/img/pr/" + users_id + "/"+filename+".jpg"));    	
    }
    
    private static boolean isImageFile (String filename) {    	
    	if (filename.endsWith(".png") || filename.endsWith(".gif") ||
    			filename.endsWith(".jpg") || filename.endsWith(".jpeg") )
    			return true;
    		else
    			return false;    	
    }
    
    /**
     * Updates all the avatar related images (i.e. original, normal, small and smaller) and returns the path
     * where the normal avatar might be found.  
     * @param bytes The bites read from a form
     * @param users_id The user id.
     * @return A string represeting the URL where the image with the normal avatar can be found 
     * @throws IOException
     */
    public static String updateAvatarImages (byte[] bytes, String users_id) throws IOException {
    	
    	BufferedImage imageToSave;
    	InputStream in = new ByteArrayInputStream(bytes);    	
    	BufferedImage image = ImageIO.read(in);
    	int type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
    	
    	//We will overwrite the previous images by naming it profile.jpg
    	//String filename = TokenGenerator.generateToken(Calendar.getInstance().toString());
    	String filename = "profile_";    
    	
    	//Save a larger file
    	float imageRatio = (float)image.getWidth() / (float)image.getHeight();
    	imageToSave = image;
    	if (image.getHeight() > 480 )
    		imageToSave = resizeImage(imageToSave, type, (int)(480*imageRatio), 480 );
    	saveImage(imageToSave, users_id, filename+"large");
    	
    	BufferedImage avatar = createAvatar(image, type); //This will create a square avatar    	
    	    	
    	//Save normal size
    	imageToSave = resizeImage(avatar, type, 180, 180);
    	saveImage(imageToSave, users_id, filename);
    	
    	//Save smaller size
    	imageToSave = resizeImage(avatar, type, 50, 50);
    	saveImage(imageToSave, users_id, filename+"small");
    	
    	//Save smaller size
    	imageToSave = resizeImage(avatar, type, 25, 25);
    	saveImage(imageToSave, users_id, filename+"smaller");
    	
    	return new String("http://img.platybox.com/pr/" + users_id + "/"+filename+".jpg");    	
    }
    
    public static boolean isImageable (MultipartFile file) {
    	long fileSize = file.getSize();
		String fileName = file.getOriginalFilename().toLowerCase();		
		if ( isImageFile(fileName) && fileSize < 1200000 )
			return true;
		else 
			return false;    	
    }
    
    
}
