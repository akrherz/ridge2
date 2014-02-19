/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jms.BytesMessage;
import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.opengis.geometry.DirectPosition;

/**
 *
 * @author Jason.Burks
 */
public class RadarTimeWriterTask implements Runnable {

	ProcessedRadarFile radarFile;
    //String rootDir = "C:/Jason/temp/ridgeoutput/RadarImg/";
	 String startPath = "/www/html/ridge2/RadarImg2/";
    RidgeTimeDAO dao;
    final static GeometryFactory factory = new GeometryFactory(new PrecisionModel(),4326);
    DateFormat dateForm = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    public RadarTimeWriterTask(ProcessedRadarFile radarFile, RidgeTimeDAO dao, String startPath) {
        this.radarFile = radarFile;
        this.dao = dao;
        this.startPath = startPath;
    }

    public void run() {
        try {
            RadarTimeIndex index = new RadarTimeIndex(); 
            String XXX = radarFile.getXXX();
            String ZZZ = radarFile.getZZZ();
            Date date = radarFile.getValidTime();
            index.setRadar(XXX);
            index.setLayer(ZZZ);
            index.setDatetime(date);
            index.setRadarPath(startPath + ZZZ+"/"+XXX + "_" + ZZZ +"_"+dateFormat.format(date)+".png");
            DirectPosition upperLeft = radarFile.getUpperLeft();
            DirectPosition lowerRight = radarFile.getLowerRight();
  
            Coordinate[] coords = new Coordinate[5];
            coords[0] = new Coordinate(upperLeft.getOrdinate(0),upperLeft.getOrdinate(1));
            coords[1] = new Coordinate(lowerRight.getOrdinate(0),upperLeft.getOrdinate(1));
            coords[2] = new Coordinate(lowerRight.getOrdinate(0),lowerRight.getOrdinate(1));
            coords[3] = new Coordinate(upperLeft.getOrdinate(0),lowerRight.getOrdinate(1));
            coords[4] = new Coordinate(upperLeft.getOrdinate(0),upperLeft.getOrdinate(1));
            index.setTheGeom(factory.createPolygon(factory.createLinearRing(coords),null));
            
            //Get optional paramters.
            
            //vcp int
            try {
            	index.setVcp(radarFile.getVcp());
            } catch (Exception e) {
            	
            }
            
            //storm relative direction float
           //stormRelativeDirection
            try {
            	index.setSrmDirection(radarFile.getStormRelativeDirection());
            } catch (Exception e) {
            	
            }
            
            //storm relative speed float
            //stormRelativeSpeedKts
            try {
            	index.setSrmSpeedKts(radarFile.getStormRelativeSpeed());
            } catch (Exception e) {
            	
            }
            
            //Storm total precip begin time date
            //stormTotalPrecipBegin
            try {
            	index.setStpStartDateTime(radarFile.getStormTotalPrecipBegin());
            } catch (Exception e) {
            	
            }
            
            //Storm total precip end time date
            //stormTotalPrecipEnd
            try {
            	index.setStpEndDateTime(radarFile.getStormTotalPrecipEnd());
            } catch (Exception e) {
            	
            }
            
            dao.saveRadarTime(index);
            Logger.getLogger(RadarTimeWriterTask.class).info("Wrote hibernate entry for xxx="+XXX + " zzz=" + ZZZ +" data date="+dateFormat.format(date));
        } catch (Exception ex) {
            Logger.getLogger(RadarTimeWriterTask.class.getName()).fatal(ex);
        }
    }

}
