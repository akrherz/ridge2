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
public class RadarCompositeCoordinate {
    GeodeticCalculator calculator = new GeodeticCalculator();
    double binWidth;
    int row;
    int pixelsPerRow;
    int halfPixel;
    double ydistance,ydistancePlus1;
    double startY,startX;
    double rangeOfProduct;

    DirectPosition[] points = new DirectPosition[4];
    
    public void setup(double longitude,double latitude, double binWidth, double rangeOfProduct) {
        calculator.setStartingGeographicPoint(longitude,latitude);
        this.binWidth = binWidth;
        this.rangeOfProduct = rangeOfProduct;
    }

    public void setNumberPixelsPerRow(int pixelsPerRow) {
         this.pixelsPerRow = pixelsPerRow;
         halfPixel = -1*(pixelsPerRow/2);
         startY = 1.5*1000.;
         startX = 1.5*1000.;
    }

    public void setRow(int rowNumber) {
        row = rowNumber;
          ydistance = -1*(rowNumber-.5)*binWidth+rangeOfProduct+startY;
        ydistancePlus1 = -1*(rowNumber+.5)*binWidth+rangeOfProduct+startY ;
        
    }

    public DirectPosition[] getPoints(int pixelInRow) {
        try {
        	points = new DirectPosition[4];

            double xdistance =  (pixelInRow-.5) * binWidth-rangeOfProduct-startX;
            double xdistancePlus1 = (pixelInRow+.5) * binWidth-rangeOfProduct-startX;
            double range = calculateDistance(xdistance, ydistance);
            double azimuth = calculateAzimuth(xdistance, ydistance);
            calculator.setDirection(azimuth,range);
            points[0] = calculator.getDestinationPosition();
                    
            range = calculateDistance(xdistancePlus1, ydistance);
            azimuth = calculateAzimuth(xdistancePlus1, ydistance);
            calculator.setDirection(azimuth, range);
            points[1] = calculator.getDestinationPosition();
            
            range = calculateDistance(xdistancePlus1, ydistancePlus1);
            azimuth = calculateAzimuth(xdistancePlus1, ydistancePlus1);
            calculator.setDirection(azimuth, range);
            points[2] = calculator.getDestinationPosition();
            
            range = calculateDistance(xdistance, ydistancePlus1);
            azimuth = calculateAzimuth(xdistance, ydistancePlus1);
            calculator.setDirection(azimuth, range);
            points[3] = calculator.getDestinationPosition();
          //  System.out.println("Points 0 ===="+points[0]);
            return points;
        } catch (TransformException ex) {
            Logger.getLogger(RadarCompositeCoordinate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    public DirectPosition[] getRightEdgePoints(int pixelInRow) {
        try {
            points = new DirectPosition[2];
      
            double xdistancePlus1 = (pixelInRow+.5) * binWidth-rangeOfProduct-startX;

            double range = calculateDistance(xdistancePlus1, ydistance);
            double azimuth = calculateAzimuth(xdistancePlus1, ydistance);
            calculator.setDirection(azimuth, range);
            points[0] = calculator.getDestinationPosition();
            
            range = calculateDistance(xdistancePlus1, ydistancePlus1);
            azimuth = calculateAzimuth(xdistancePlus1, ydistancePlus1);
            calculator.setDirection(azimuth, range);
            points[1] = calculator.getDestinationPosition();
           
            return points;
        } catch (TransformException ex) {
            Logger.getLogger(RadarCompositeCoordinate.class.getName()).log(Level.SEVERE, null, ex);
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


    public static void main(String[] strings) {
        System.out.println("x=0,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., 0.))));
         System.out.println("x=10,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., 10.))));
         System.out.println("x=10,y=0"+"   "+(90-Math.toDegrees(Math.atan2(0., 10.))));
        System.out.println("x=10,y=-10"+"   "+(90-Math.toDegrees(Math.atan2(10., 10.))));
        System.out.println("x=0,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., 0.))));
        System.out.println("x=-10,y=10"+"   "+(90-Math.toDegrees(Math.atan2(10., -10.))));
        System.out.println("x=-10,y=0"+"   "+(90-Math.toDegrees(Math.atan2(0., -10.))));
        System.out.println("x=-10,y=-10"+"   "+(90-Math.toDegrees(Math.atan2(-10., -10.))));

    }
}
