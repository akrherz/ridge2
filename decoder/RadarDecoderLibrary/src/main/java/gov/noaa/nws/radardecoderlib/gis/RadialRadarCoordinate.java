/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.geometry.GeneralDirectPosition;
import org.geotools.referencing.GeodeticCalculator;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.TransformException;

/**
 *
 * @author jason
 */
public class RadialRadarCoordinate {
    GeodeticCalculator calculator = new GeodeticCalculator();
    DistanceCalculator distCalc;
    double binWidth;
    double startAngle, endAngle;
    DirectPosition[] output = new DirectPosition[4];


    public void setup( double longitude,double latitude, float elevationAngle, double binWidth) {
        calculator.setStartingGeographicPoint(longitude,latitude);
        distCalc = new DistanceCalculator(elevationAngle);
        this.binWidth = binWidth;
    }

    public void setRadialAngle(double startAngle, double deltaAngle) {
        this.startAngle = convertToAzimuth(startAngle);
        this.endAngle = convertToAzimuth(startAngle+deltaAngle);
    }

    public DirectPosition[] getPoints(int binNumber) {
        try {
            output = new DirectPosition[4];
            double distanceInside = distCalc.findGreatCircleDistance((binNumber)* binWidth);
            double distanceOutside = distCalc.findGreatCircleDistance((binNumber + 1) * binWidth);
            calculator.setDirection(startAngle, distanceInside);
            output[1] = calculator.getDestinationPosition();

            calculator.setDirection(startAngle, distanceOutside);
            output[2] = calculator.getDestinationPosition();

            calculator.setDirection(endAngle, distanceOutside);
            output[3] = calculator.getDestinationPosition();

            calculator.setDirection(endAngle, distanceInside);
            output[0] = calculator.getDestinationPosition();
            return output;
        } catch (TransformException ex) {
            Logger.getLogger(RadialRadarCoordinate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    public DirectPosition[] getOuterEdgePoints(int binNumber) {
        try {
            output = new DirectPosition[2];
           
            double distanceOutside = distCalc.findGreatCircleDistance((binNumber + 1) * binWidth);
           

            calculator.setDirection(startAngle, distanceOutside);
            output[0] = calculator.getDestinationPosition();

            calculator.setDirection(endAngle, distanceOutside);
            output[1] = calculator.getDestinationPosition();

            return output;
        } catch (TransformException ex) {
            Logger.getLogger(RadialRadarCoordinate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    public DirectPosition[] getUpperPoint(int binNumber) {
        try {
            output = new DirectPosition[1];
           
            double distanceOutside = distCalc.findGreatCircleDistance((binNumber + 1) * binWidth);
           
            calculator.setDirection(endAngle, distanceOutside);
            output[0] = calculator.getDestinationPosition();

            return output;
        } catch (TransformException ex) {
            Logger.getLogger(RadialRadarCoordinate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public DirectPosition[] getDistancePoints(int binNumber) {
            DirectPosition[] output = new DirectPosition[4];
            double distanceInside = distCalc.findGreatCircleDistance((binNumber)* binWidth);
            double distanceOutside = distCalc.findGreatCircleDistance((binNumber + 1) * binWidth);
            output[0] = new GeneralDirectPosition(distanceInside*Math.sin(Math.toRadians(startAngle)),distanceInside*Math.cos(Math.toRadians(startAngle)));
            output[1] = new GeneralDirectPosition(distanceOutside*Math.sin(Math.toRadians(startAngle)),distanceOutside*Math.cos(Math.toRadians(startAngle)));
            output[2] = new GeneralDirectPosition(distanceOutside*Math.sin(Math.toRadians(endAngle)),distanceOutside*Math.cos(Math.toRadians(endAngle)));
            output[3] = new GeneralDirectPosition(distanceInside*Math.sin(Math.toRadians(endAngle)),distanceInside*Math.cos(Math.toRadians(endAngle)));
            return output;
    }

    public static double convertToAzimuth(double value) {
        if (value >=360.)  value = value-360f;
         if ((value >180.f) && (value <=360.f)) {
             value = value-360.f;
         }
         return(value);
     }
}
