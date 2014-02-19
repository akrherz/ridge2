/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge.liteimagecreator.task;

import gov.noaa.nws.ridge.liteimagecreator.config.Config;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason.Burks
 */
public class ProcessImageTask implements Runnable {
        RadarInput input;
    public ProcessImageTask(RadarInput input) {
       this.input = input;
    }

    public void run() {
        System.out.println("Doing the work");
        Config config = Config.getInstanceConfig();
        String imageLocation = config.getWwwImagePath() + input.getRadarId().toLowerCase() + "_" + input.getLayerId().toLowerCase() + ".png";
        String size = "600 550";


        try {
            //Generate tmp mapfile
            System.out.println("Generate tmp mapfile");
            String mapPath = MapFileMaker.createMapFile(input);

            String cmd = "shp2img -m " + mapPath + " -o " + imageLocation + " -s " + size;
            cmd = cmd + " -l \"" + input.getRadarId() + "_" + input.getLayerId();
            cmd = cmd + " state county roads cities warnings\" -e " + input.getBbox() + " -i PNG";
            //Execute shp2img
            System.out.println("run shp2img");
            //Now run the command
            System.out.println(cmd);
            //Process child = Runtime.getRuntime().exec(cmd);

            //Remove tmp mapfile
            System.out.println("Remove tmp mapfile");
            //Now delete the tempfile
            boolean success = (new File(mapPath)).delete();
            if (!success) {
                // Deletion failed
                System.out.println("Cannot delete temporary mapfile");
            }

        } catch (Exception ex) {
            Logger.getLogger(ProcessImageTask.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
