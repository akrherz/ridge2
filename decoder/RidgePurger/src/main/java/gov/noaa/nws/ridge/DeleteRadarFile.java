/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import java.io.File;
import org.apache.log4j.Logger;

/**
 *
 * @author Jason.Burks
 */
public class DeleteRadarFile implements Runnable{
    RidgeTimeDAO dao;
    RadarTimeIndex radar;
    public DeleteRadarFile(RadarTimeIndex radar, RidgeTimeDAO dao ){
        this.radar = radar;
        this.dao = dao;
    }
    public void run() {
      //Delete db entry
        dao.deleteRadarTime(radar);
      //Delete file
        try {
            File file = new File(radar.getRadarPath());
            file.delete();
        } catch( Exception e) {
             Logger.getLogger(DeleteRadarFile.class).info("problem deleting "+radar.getRadarPath());
        }
      //Delete pgw file
        String[] strings = radar.getRadarPath().split("\\.");
        try {
            File filepgw = new File(strings[0]+".pgw");
            filepgw.delete();
        } catch (Exception e) {
            Logger.getLogger(DeleteRadarFile.class).info("problem deleting pgw"+radar.getRadarPath());
        }
        Logger.getLogger(DeleteRadarFile.class).info("Deleted "+radar.getRadarPath());
        radar = null;
        dao = null;
    }

}
