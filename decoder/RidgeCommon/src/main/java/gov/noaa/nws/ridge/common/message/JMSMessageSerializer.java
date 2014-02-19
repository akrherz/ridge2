/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge.common.message;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;

import java.util.Date;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.geotools.geometry.GeneralDirectPosition;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 *
 * @author Jason.Burks
 */
public class JMSMessageSerializer{
   

    

	public static ProcessedRadarFile unmarshall(Message messageIn) throws Exception{
		BytesMessage message = (BytesMessage)messageIn;
		ProcessedRadarFile radarFile = new ProcessedRadarFile();

		radarFile.setXXX(message.getStringProperty("siteID"));
		radarFile.setZZZ(message.getStringProperty("productID"));
		radarFile.setValidTime(new Date(message.getLongProperty("validTime")));
		long length = message.getBodyLength();
		byte[] barray = new byte[(int)length];
		message.readBytes(barray);
		radarFile.setByteImage(barray);
		radarFile.setUpperLeft(new GeneralDirectPosition(message.getDoubleProperty("upperLeftLon"),message.getDoubleProperty("upperLeftLat")));
		radarFile.setLowerRight(new GeneralDirectPosition(message.getDoubleProperty("lowerRightLon"),message.getDoubleProperty("lowerRightLat")));
		radarFile.setImageWidth(message.getIntProperty("width"));
		radarFile.setImageHeight(message.getIntProperty("height"));
		radarFile.setElevationAngle(message.getFloatProperty("elevationAngle"));
		radarFile.setVcp(message.getIntProperty("vcp"));

		if (message.propertyExists("stormTotalPrecipBegin")) radarFile.setStormTotalPrecipBegin(new Date(message.getLongProperty("stormTotalPrecipBegin")));
		if (message.propertyExists("stormTotalPrecipEnd")) radarFile.setStormTotalPrecipEnd(new Date(message.getLongProperty("stormTotalPrecipEnd")));
		if (message.propertyExists("stormRelativeSpeedKts")) radarFile.setStormRelativeSpeed(message.getFloatProperty("stormRelativeSpeedKts"));

		if (message.propertyExists("stormRelativeDirection")) radarFile.setStormRelativeDirection(message.getFloatProperty("stormRelativeDirection"));

		return radarFile;	  
	}
	
	public static Message marshall(ProcessedRadarFile radarFile,Session session) throws Exception {
		BytesMessage message = session.createBytesMessage();
        message.writeBytes(radarFile.getByteImage());
        message.setStringProperty("siteID",radarFile.getXXX());
        message.setStringProperty("productID", radarFile.getZZZ());
        message.setLongProperty("validTime", radarFile.getValidTime().getTime());
        message.setDoubleProperty("upperLeftLon", radarFile.getUpperLeft().getOrdinate(0));
        message.setDoubleProperty("upperLeftLat", radarFile.getUpperLeft().getOrdinate(1));
        message.setDoubleProperty("lowerRightLon", radarFile.getLowerRight().getOrdinate(0));
        message.setDoubleProperty("lowerRightLat", radarFile.getLowerRight().getOrdinate(1));
        message.setFloatProperty("elevationAngle", radarFile.getElevationAngle());
        message.setIntProperty("width", radarFile.getImage().getWidth());
        message.setIntProperty("height", radarFile.getImage().getHeight());
        if (radarFile.getStormTotalPrecipBegin() != null) {
          message.setLongProperty("stormTotalPrecipBegin", radarFile.getStormTotalPrecipBegin().getTime());
        }
        if (radarFile.getStormTotalPrecipEnd() != null) {
          message.setLongProperty("stormTotalPrecipEnd", radarFile.getStormTotalPrecipEnd().getTime());
        }
        
        //Add vcp
        message.setIntProperty("vcp", radarFile.getVcp());
        
        //Add Storm relative speed and direction
        if (radarFile.getStormRelativeSpeed() != Float.MIN_VALUE) {
        	message.setFloatProperty("stormRelativeSpeedKts", radarFile.getStormRelativeSpeed());
        	message.setFloatProperty("stormRelativeDirection", radarFile.getStormRelativeDirection());
        }
		return message;
	}

   

}
