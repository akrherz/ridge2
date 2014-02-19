/*
 * This class contains methods to modify the map template file and to save
 * it as a temporary mapfile file for Mapserver
 */

package gov.noaa.nws.ridge.liteimagecreator.task;
import gov.noaa.nws.ridge.liteimagecreator.config.Config;
import java.util.regex.*;
import java.util.UUID;
import java.io.*;

/**
 *
 * @author brian.walawender
 */
public class MapFileMaker {
  

    public static String createMapFile(RadarInput input) throws Exception {
        // Create the local radar section of the mapfile
        Config myConfig = Config.getInstanceConfig();

        String mapLayer = " LAYER\n";
        mapLayer = mapLayer + "  NAME '"  + input.getRadarId() + "_"  + input.getLayerId()+ "'\n";
        mapLayer = mapLayer + "  TYPE RASTER\n";
        mapLayer = mapLayer + "  STATUS OFF\n";
        mapLayer = mapLayer + "  METADATA\n";
        mapLayer = mapLayer + "   \"wms_tile\"       " + input.getRadarId() + "_"  + input.getLayerId() + "\n";
        mapLayer = mapLayer + "  END\n";
        mapLayer = mapLayer + "  DATA " + input.getImagePath() + "\n";
        mapLayer = mapLayer + " END\n";
//
        Pattern pattern = Pattern.compile("#RADAR_LAYER");
//
//        // Replace all occurrences of pattern in input

       Matcher matcher = pattern.matcher(myConfig.getTemplate());
       String fullMapFile = matcher.replaceAll(mapLayer);
//
//        // Create temporary Mapfile name using UUID
        String id = UUID.randomUUID().toString();
        String outputFile = myConfig.getTemporaryPath() + id + "_" + input.getRadarId() + "_" + input.getLayerId() + ".map";
        System.out.println("Going to be writing "+outputFile);
         System.out.println("With  "+fullMapFile);
//        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
//        out.write(fullMapFile);
//        out.close();


        return(outputFile);

    }

}
