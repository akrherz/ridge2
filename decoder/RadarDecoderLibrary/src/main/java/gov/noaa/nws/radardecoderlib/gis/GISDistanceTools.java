/*
 * GISDistanceTools.java
 *
 * Created on October 4, 2005, 9:23 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import org.geotools.geometry.GeneralPosition;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.api.geometry.Position;



/**
 *
 * @author jburks
 */
public class GISDistanceTools {
    static double RadiusEarth = 6371;
    /** Creates a new instance of GISDistanceTools */
    public GISDistanceTools() {
    }
    
     
     public static Position getPointFromRangeAndDistance(Position startPoint, double head, double distance) {
         
         double azimuth = convertAzimuth(head);
//         double rangem = ((distance)*1852.)*Math.cos(Math.toRadians(0.5));
         double rangem = (distance)*1852.;
        GeodeticCalculator calc = new GeodeticCalculator();
         //GeodeticCalculator calc = new GeodeticCalculator();
       
         //System.out.println("Calc ="+calc.getEllipsoid());
      
         calc.setStartingGeographicPoint(startPoint.getOrdinate(0),startPoint.getOrdinate(1));
         
         calc.setDirection(azimuth,rangem); 
         
         return(new GeneralPosition(calc.getDestinationGeographicPoint().getY(),calc.getDestinationGeographicPoint().getX()));
     }
    
     public static Position getPointFromRangeXAndY(Position startPoint, double x, double y) {
         //need to calculate Azimuth
        double  head = Math.atan2(x,y);
        double distance = Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
         double rangem = (distance)*1852.00;
         GeodeticCalculator calc = new GeodeticCalculator();
         
         calc.setStartingGeographicPoint(startPoint.getOrdinate(0),startPoint.getOrdinate(1));
         calc.setDirection(Math.toDegrees(head),rangem); 
         
         return(new GeneralPosition(calc.getDestinationGeographicPoint().getY(),calc.getDestinationGeographicPoint().getX()));
      //   return(null);
     }
//     
     public static double convertAzimuth(double value) {
         if ((value >180.) && (value <=360.)) {
             value = value-360.;
         }
         return(value);
     }
     public static void main(String[] args) {
         //System.out.println(getPointFromRangeAndDistance(new GeoPoint(33.0,-88.0),270.0,120.0));
       //  System.out.println(getPointFromRangeXAndY(new GeoPoint(33.0,-88.0),100.0,100.0));
        // System.out.println(getPointFromRangeAndDistanceNew(new GeoPoint(33.0,-88.0),270.0,120.0));
//         GeodeticCalculator calc = new GeodeticCalculator();
//         calc.setAnchorPoint(-88.0,33.0);
//         calc.setDirection(-90.0,22224.00);
//         System.out.println(calc.getDestinationPoint());
     }
//     public GeoPoint
             
             
}
