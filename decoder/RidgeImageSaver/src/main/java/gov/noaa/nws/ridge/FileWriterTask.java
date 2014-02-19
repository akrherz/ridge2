/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;
import gov.noaa.nws.ridge.gis.WorldFileWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.opengis.geometry.DirectPosition;

/**
 *
 * @author Jason.Burks
 */
public class FileWriterTask implements Runnable {
    ProcessedRadarFile radarFile;
    String startPath = "/www/html/ridge2/RadarImg2/";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    
    public FileWriterTask(ProcessedRadarFile radarFile,String startPath) {
        this.radarFile = radarFile;
        this.startPath = startPath;
    }

    public void run() {
        {
            FileOutputStream fos2 = null;
            try {
                String XXX = radarFile.getXXX();
                String ZZZ = radarFile.getZZZ();
                Date date = radarFile.getValidTime();
                fos2 = new FileOutputStream(startPath + ZZZ+"/"+XXX + "_" + ZZZ +"_"+dateFormat.format(date)+".png");
                byte[] barray = radarFile.getByteImage();
                fos2.write(barray);
                DirectPosition upperLeft = radarFile.getUpperLeft();
                DirectPosition lowerRight = radarFile.getLowerRight();
                
                 int width = radarFile.getImageWidth();
                 int height = radarFile.getImageHeight();
                 WorldFileWriter.writeWorldFile(startPath + ZZZ+"/"+XXX + "_" + ZZZ +"_"+dateFormat.format(date)+".pgw", upperLeft.getOrdinate(0),upperLeft.getOrdinate(1),lowerRight.getOrdinate(0),lowerRight.getOrdinate(1), width,height);
                 Logger.getLogger(FileWriterTask.class).info("Writing "+startPath+ ZZZ+"/"+XXX + "_" + ZZZ +"_"+dateFormat.format(date)+".png");
            } catch (IOException ex) {
            	//ex.printStackTrace();
                Logger.getLogger(FileWriterTask.class).warn(ex);
            } catch (Exception e) {
            	//e.printStackTrace();
            	Logger.getLogger(FileWriterTask.class).warn(e);
            } finally {
                try {
                	if (fos2 != null) {
                		fos2.close();
                	} else {
                		Logger.getLogger(FileWriterTask.class).warn("File is null "+radarFile);
                	}
                } catch (IOException ex) {
                    Logger.getLogger(FileWriterTask.class).warn(ex);
                }
            }

        }
    }



}
