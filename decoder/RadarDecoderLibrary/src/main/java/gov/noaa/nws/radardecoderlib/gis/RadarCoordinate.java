/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.referencing.GeodeticCalculator;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.TransformException;

/**
 *
 * @author jason
 */
public class RadarCoordinate {
    GeodeticCalculator calculator = new GeodeticCalculator();
    

    public void setup(double longitude,double latitude) {
        calculator.setStartingGeographicPoint(longitude,latitude);
       
    }

    

    public DirectPosition getPoint(DirectPosition point) {
        try {
            double range = calculateDistance(point.getOrdinate(0), point.getOrdinate(1));
            double azimuth = calculateAzimuth(point.getOrdinate(0), point.getOrdinate(1));
            calculator.setDirection(azimuth,range);
            return(calculator.getDestinationPosition());
                    
           
        } catch (TransformException ex) {
            Logger.getLogger(RadarCoordinate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    private double calculateDistance(double distanceX, double distanceY) {
        return(Math.sqrt(Math.pow(distanceX, 2)+Math.pow(distanceY, 2)));
    }
    private double calculateAzimuth(double distanceX, double distanceY) {
        return(Math.toDegrees(Math.atan2(distanceX, distanceY)));
    }
//    public static double convertAzimuth(double value) {
//         if ((value >180.f) && (value <=360.f)) {
//             value = value-360.f;
//         }
//         return(value);
//     }


//    public static void main(String[] strings) {
//        System.out.println("x=0,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., 0.))));
//         System.out.println("x=10,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., 10.))));
//         System.out.println("x=10,y=0"+"   "+(90-Math.toDegrees(Math.atan2(0., 10.))));
//        System.out.println("x=10,y=-10"+"   "+(90-Math.toDegrees(Math.atan2(10., 10.))));
//        System.out.println("x=0,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., 0.))));
//        System.out.println("x=-10,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., -10.))));
//        System.out.println("x=-10,y=0"+"   "+(90-Math.toDegrees(Math.atan2(0., -10.))));
//        System.out.println("x=-10,y=-10"+"   "+(90-Math.toDegrees(Math.atan2(-10., -10.))));
//
//    }
}
