/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge.common.message;


import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.geotools.geometry.GeneralDirectPosition;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;


/**
 *
 * @author Jason.Burks
 */
public class AMQPMessageSerializer {
    

	public static ProcessedRadarFile unmarshall(Message message) throws Exception {
    
    	  Map<String, Object> map = message.getMessageProperties().getHeaders();
    	  ProcessedRadarFile radarFile = new ProcessedRadarFile();
    	  
          radarFile.setXXX((String)map.get("siteID"));
          radarFile.setZZZ((String)map.get("productID"));
          radarFile.setValidTime(new Date((Long)map.get("validTime")));
          radarFile.setByteImage(message.getBody());
          radarFile.setUpperLeft(new GeneralDirectPosition(Double.valueOf((String)map.get("upperLeftLon")),Double.valueOf((String)map.get("upperLeftLat"))));
          radarFile.setLowerRight(new GeneralDirectPosition(Double.valueOf((String)map.get("lowerRightLon")),Double.valueOf((String)map.get("lowerRightLat"))));
           radarFile.setImageWidth((Integer)map.get("width"));
           radarFile.setImageHeight((Integer)map.get("height"));
    	  radarFile.setElevationAngle(Float.valueOf((String)map.get("elevationAngle")));
    	  radarFile.setVcp((Integer)map.get("vcp"));
			
    	  if (map.containsKey("stormTotalPrecipBegin")) radarFile.setStormTotalPrecipBegin(new Date((Long)map.get("stormTotalPrecipBegin")));
    	  if (map.containsKey("stormTotalPrecipEnd")) radarFile.setStormTotalPrecipEnd(new Date((Long)map.get("stormTotalPrecipEnd")));
    	  if (map.containsKey("stormRelativeSpeedKts")) radarFile.setStormRelativeSpeed(Float.valueOf((String)map.get("stormRelativeSpeedKts")));
    	  if (map.containsKey("stormRelativeDirection")) radarFile.setStormRelativeDirection(Float.valueOf((String)map.get("stormRelativeDirection")));

    	 return radarFile;
	}
	
	
	public static Message marshall(ProcessedRadarFile radarFile) throws Exception {
		
		MessageProperties properties = new MessageProperties();
		properties.setHeader("siteID", radarFile.getXXX());
		properties.setHeader("productID", radarFile.getZZZ());
		properties.setHeader("validTime", radarFile.getValidTime().getTime());
		properties.setHeader("upperLeftLon",String.valueOf(radarFile.getUpperLeft().getOrdinate(0)));
		properties.setHeader("upperLeftLat", String.valueOf(radarFile.getUpperLeft().getOrdinate(1)));
		properties.setHeader("lowerRightLon", String.valueOf(radarFile.getLowerRight().getOrdinate(0)));
		properties.setHeader("lowerRightLat", String.valueOf(radarFile.getLowerRight().getOrdinate(1)));
		properties.setHeader("elevationAngle", String.valueOf(radarFile.getElevationAngle()));
		properties.setHeader("width", radarFile.getImage().getWidth());
		properties.setHeader("height", radarFile.getImage().getHeight());
		
		if (radarFile.getStormTotalPrecipBegin() != null) {
			properties.setHeader("stormTotalPrecipBegin", radarFile.getStormTotalPrecipBegin().getTime());
	        }
	        if (radarFile.getStormTotalPrecipEnd() != null) {
	        	properties.setHeader("stormTotalPrecipEnd", radarFile.getStormTotalPrecipEnd().getTime());
	        }
	        
	        //Add vcp
	        properties.setHeader("vcp", radarFile.getVcp());
	        
	        //Add Storm relative speed and direction
	        if (radarFile.getStormRelativeSpeed() != Float.MIN_VALUE) {
	        	properties.setHeader("stormRelativeSpeedKts", String.valueOf(radarFile.getStormRelativeSpeed()));
	        	properties.setHeader("stormRelativeDirection", String.valueOf(radarFile.getStormRelativeDirection()));
	        }
		
		
		
		
		properties.setContentType("image/png");
		//properties.setContentType("text/plain");
		return new Message(radarFile.getByteImage(), properties);
		
	}

}
