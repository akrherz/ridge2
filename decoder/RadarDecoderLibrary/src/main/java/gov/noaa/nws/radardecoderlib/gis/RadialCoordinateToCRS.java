/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import java.util.ArrayList;

import org.geotools.geometry.GeneralDirectPosition;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author jason
 */
public class RadialCoordinateToCRS extends RadarCoordinateToCRS {

    private double radialBinWidth;
    private float elevationAngle;
    private RadialRadarCoordinate radCoord = new RadialRadarCoordinate();
    private int previousBinNumber=-99;
    private DirectPosition[] previousPoints = new DirectPosition[4];
    DirectPosition[] output= new DirectPosition[4];
    DirectPosition[] temp= new DirectPosition[2];
    

    public RadialCoordinateToCRS(MathTransform geoGraphicToCRS, double radarLon, double radarLat, float elevationAngle, double binWidth) {
        super(geoGraphicToCRS,radarLon,radarLat);
        this.elevationAngle = elevationAngle;
        this.radialBinWidth = binWidth;
       if (geoGraphicToCRS != null) hasCRS = true;
    }

    public void intialize() {
        radCoord.setup(radarLon,radarLat,elevationAngle,radialBinWidth);
    }

   public void setRadialAngle(double startAngle, double deltaAngle) {
       radCoord.setRadialAngle(startAngle, deltaAngle);
       previousBinNumber = -99;
   }


   public DirectPosition[] getPoints(int binNumber) throws Exception {
       if (hasCRS) {
    	   output = new DirectPosition[4];
    	   if ((binNumber-previousBinNumber) == 1) {
    		   output[0] = new GeneralDirectPosition(previousPoints[3].getOrdinate(0),previousPoints[3].getOrdinate(1));
    		   output[1] = new GeneralDirectPosition(previousPoints[2].getOrdinate(0),previousPoints[2].getOrdinate(1));
    		   temp = this.convertGeoGraphicToCRS(radCoord.getOuterEdgePoints(binNumber));
    		   output[2] = temp[0];
    		   output[3] = temp[1];
    	   } else {
    		   output = this.convertGeoGraphicToCRS(radCoord.getPoints(binNumber));
    	   }
    	  previousPoints = output;
    	  previousBinNumber = binNumber;

          return  output;
       } else {
           return(radCoord.getPoints(binNumber));
       }
   }

   public RadialRadarCoordinate getRadialRadarCoordinate() {
       return(radCoord);
   }



}
