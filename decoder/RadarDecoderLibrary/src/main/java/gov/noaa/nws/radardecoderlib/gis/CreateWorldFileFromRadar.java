/*
 * CreateWorldFileFromRadar.java
 *
 * Created on October 3, 2005, 9:34 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.gis;
import java.io.*;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
/**
 *w.yahoo
 * @author jburks
 */
public class CreateWorldFileFromRadar {

   public CreateWorldFileFromRadar() {
    }

    public static void createWorldFileFromRadar(String outputFile, DirectPosition upperLeft, DirectPosition lowerRight, int width, int height) {
         double xScaling = (lowerRight.getOrdinate(0)-upperLeft.getOrdinate(0))/width;
         ///need to use xscaling in y direction in case not square
         double yScaling = -1*(upperLeft.getOrdinate(1)-lowerRight.getOrdinate(1))/height;
         String worldfileOutput = new String(xScaling+"\n0.00000\n0.00000\n"+yScaling+"\n"+upperLeft.getOrdinate(0)+"\n"+upperLeft.getOrdinate(1)+"\n");
         outputToFile(outputFile, worldfileOutput);
    }
    
    /** Creates a new instance of CreateWorldFileFromRadar */
    
     public static void createWorldFileFromRadar(String imageoutputfile,DirectPosition centerPoint, double range, int imageWidth, int imageHeight) {
       //Convert to km from nautical miles 315 135
         DirectPosition east = GISDistanceTools.getPointFromRangeAndDistance(centerPoint, 90., range);
         DirectPosition west = GISDistanceTools.getPointFromRangeAndDistance(centerPoint, 270., range);
         //Needed to scale using x to y ratio to accomadate for non-square images
         double yrange = range*((double)imageHeight/(double)imageWidth);
         double xScaling = (east.getOrdinate(0)-west.getOrdinate(0))/imageWidth;
         DirectPosition north = GISDistanceTools.getPointFromRangeAndDistance(centerPoint, 0., yrange);
         DirectPosition south = GISDistanceTools.getPointFromRangeAndDistance(centerPoint, 180., yrange);
         
         
         
         ///need to use xscaling in y direction in case not square
         double yScaling = -1*(north.getOrdinate(1)-south.getOrdinate(1))/imageHeight;
         String worldfileOutput = new String(xScaling+"\n0.00000\n0.00000\n"+yScaling+"\n"+west.getOrdinate(0)+"\n"+north.getOrdinate(1)+"\n");
         String outputfile = createWorldFilename(imageoutputfile);
         outputToFile(outputfile, worldfileOutput);

         }
     
     public static void createWorldFileFromCoordinates(String imageoutputfile, double[] coordinates, int imageWidth, int imageHeight) {
       //Convert to km from nautical miles 315 135
         
         
         double xScaling = (coordinates[2]-coordinates[0])/imageWidth;
        
         
         
         
         ///need to use xscaling in y direction in case not square
         double yScaling = -1*(coordinates[1]-coordinates[3])/imageHeight;
         String worldfileOutput = new String(xScaling+"\n0.00000\n0.00000\n"+yScaling+"\n"+coordinates[0]+"\n"+coordinates[1]+"\n");
         String outputfile = createWorldFilename(imageoutputfile);
         outputToFile(outputfile, worldfileOutput);

         }

    
     
      public static void createWorldFileFromLocalCoordinates(String imageoutputfile, double width, double height, int imageWidth, int imageHeight) {
       //Convert to km from nautical miles 315 135
         double xScaling = width/(imageWidth*2);
         ///need to use xscaling in y direction in case not square
         double yScaling = -1*height/(imageHeight*2);
         String worldfileOutput = new String(xScaling+"\n0.00000\n0.00000\n"+yScaling+"\n"+0.0000000000+"\n"+0.00000000000+"\n");
         String outputfile = createWorldFilename(imageoutputfile);
         outputToFile(outputfile, worldfileOutput);

         }
      
         public static void outputWKT(String imageoutputfile, CoordinateReferenceSystem crs) {
             int locationofDot = imageoutputfile.indexOf(".");
             String firstPart = imageoutputfile.substring(0, locationofDot);
             String outputfile = firstPart+".prj";
             try {
             File file = new File(outputfile);
             BufferedWriter w= new BufferedWriter(new FileWriter(file));
             w.write(crs.toWKT());
             w.close();
             } catch (IOException e) {
                 System.out.println("Problem writing world file "+outputfile);
             }
             
         }
     
     
         public static void outputToFile(String outputfile, String worldFileInfo) {
            System.out.println("OUtputting world file "+outputfile);
             try {
             File file = new File(outputfile);
             BufferedWriter w= new BufferedWriter(new FileWriter(file));
             w.write(worldFileInfo);
             w.close();
             } catch (IOException e) {
                 System.out.println("Problem writing world file "+outputfile);
             }
         }
         
         public static String createWorldFilename(String filename) {
             int locationofDot = filename.lastIndexOf(".");
             String firstPart = filename.substring(0, locationofDot);
             String fileSuffix = filename.substring(locationofDot+1);
             return(firstPart+"."+getWorldFileSuffix(fileSuffix));
             
         }
         
         public static String getWorldFileSuffix(String suffix) {
             if (suffix.equalsIgnoreCase("GIF")) {
                 return("gfw");
             } else if (suffix.equalsIgnoreCase("PNG")) {
                 return("pgw");
             } else if (suffix.equalsIgnoreCase("SVG")) {
                 return("sgw");
             }
             
             return("");
             
         }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //GeoPoint startPoint = new GeoPoint(34.931,-86.084);
        //createWorldFileFromRadar("C:/Jason/radardata/test.gfw",startPoint,156.,600,550);
        //haversineDistance(34.45, -87.86,0.,0.);
        //CreateWorldFileFromRadar.createWorldFilename("C:/Jason/radar/test.gif");
        //CreateWorldFileFromRadar.createWorldFileFromRadar("C:/Jason/radardata/compareWithVersion1/verison2/FWS_20030406_0156_N0R.gif",new GeoPoint(32.573,-97.303),200.,600,550);
        CreateWorldFileFromRadar.createWorldFileFromLocalCoordinates("/home/jason/temp/FWS_20030406_0156_N0R.gif",10000,12000,600,550);
    }
    
}
