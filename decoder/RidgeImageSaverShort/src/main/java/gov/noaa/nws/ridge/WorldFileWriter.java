/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jason.Burks
 */
public class WorldFileWriter {

    public static void writeWorldFile(String filename, double upperLeftLon, double upperLeftLat, double lowerRightLon, double lowerRightLat, int width, int height) {
         double xScaling = (lowerRightLon-upperLeftLon)/width;
         ///need to use xscaling in y direction in case not square
         double yScaling = -1*(upperLeftLat-lowerRightLat)/height;
         String worldfileOutput = new String(xScaling+"\n0.00000\n0.00000\n"+yScaling+"\n"+upperLeftLon+"\n"+upperLeftLat+"\n");
         outputToFile(filename, worldfileOutput);
    }

    public static void outputToFile(String outputfile, String worldFileInfo) {
             try {
             File file = new File(outputfile);
             BufferedWriter w= new BufferedWriter(new FileWriter(file));
             w.write(worldFileInfo);
             w.close();
             } catch (IOException e) {
                 System.out.println("Problem writing world file "+outputfile);
             }
         }
}
