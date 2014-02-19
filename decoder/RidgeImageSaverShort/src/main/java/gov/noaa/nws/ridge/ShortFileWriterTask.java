/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.opengis.geometry.DirectPosition;

/**
 *
 * @author Jason.Burks
 */
public class ShortFileWriterTask implements Runnable {
    ProcessedRadarFile radarFile;
    String startPath = "/www/html/ridge2/RadarImg2/";
    public ShortFileWriterTask(ProcessedRadarFile radarFile, String startPath) {
        this.radarFile = radarFile;
        this.startPath = startPath;
    }

    public void run() {
        {
            FileOutputStream fos = null;
            try {
            	String XXX = radarFile.getXXX();
                String ZZZ = radarFile.getZZZ();
                fos = new FileOutputStream(startPath + ZZZ+"/"+XXX + "_" + ZZZ +".png");
                byte[] barray = radarFile.getByteImage();
                fos.write(barray);
                DirectPosition upperLeft = radarFile.getUpperLeft();
                DirectPosition lowerRight = radarFile.getLowerRight();
                
                 int width = radarFile.getImageWidth();
                 int height = radarFile.getImageHeight();
                 WorldFileWriter.writeWorldFile(startPath + ZZZ+"/"+XXX + "_" + ZZZ +".pgw", upperLeft.getOrdinate(0),upperLeft.getOrdinate(1),lowerRight.getOrdinate(0),lowerRight.getOrdinate(1), width,height);
                 Logger.getLogger(ShortFileWriterTask.class).info("Writing "+startPath + ZZZ+"/"+XXX + "_" + ZZZ +".png");
            } catch (IOException ex) {
                Logger.getLogger(ShortFileWriterTask.class.getName()).warn(ex);
            } catch (Exception ex) {
                Logger.getLogger(ShortFileWriterTask.class.getName()).warn(ex);
            } finally {
                try {
                	if (fos != null) {
                		fos.close();
                	} else {
                		Logger.getLogger(ShortFileWriterTask.class).warn("File is null "+radarFile);
                	}
                } catch (IOException ex) {
                    Logger.getLogger(ShortFileWriterTask.class).warn(ex);
                }
            }

        }
    }



}
