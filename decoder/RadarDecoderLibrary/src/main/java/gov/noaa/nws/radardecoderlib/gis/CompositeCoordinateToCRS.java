/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import org.geotools.geometry.GeneralPosition;
import org.geotools.api.geometry.Position;
import org.geotools.api.referencing.operation.MathTransform;

/**
 *
 * @author jason
 */
public class CompositeCoordinateToCRS extends RadarCoordinateToCRS {
    private double pixelBinWidth;
    private double productRange;
    private RadarCompositeCoordinate radCoord = new RadarCompositeCoordinate();
    private int previousBinNumber=-99;
    private Position[] previousPoints = new Position[4];
    Position[] output = new Position[4];
    Position[] temp = new Position[2];

   public CompositeCoordinateToCRS(MathTransform geoGraphicToCRS, double radarLon, double radarLat, double pixelWidth, double productRange) {
        super(geoGraphicToCRS,radarLon,radarLat);
        this.pixelBinWidth = pixelWidth;
        this.productRange = productRange;
        
        if (geoGraphicToCRS != null) hasCRS = true;
   }

    @Override
    public void intialize() {
       radCoord.setup(radarLon,radarLat,pixelBinWidth, productRange);
    }
    public void setPixelsPerRow(int pixelsPerRow) {
        radCoord.setNumberPixelsPerRow(pixelsPerRow);
    }

   public void setPixelRow(int rowNumber) {
       radCoord.setRow(rowNumber);
   }


   public Position[] getPoints(int binNumber) throws Exception {
       if (hasCRS) {
    	   output = new Position[4];
    	   if ((binNumber-previousBinNumber) == 1) {
    		   output[0] = new GeneralPosition(previousPoints[1].getOrdinate(0),previousPoints[1].getOrdinate(1));
    		   output[3] = new GeneralPosition(previousPoints[2].getOrdinate(0),previousPoints[2].getOrdinate(1));
    		   temp = this.convertGeoGraphicToCRS(radCoord.getRightEdgePoints(binNumber));
    		   output[1] = temp[0];
    		   output[2] = temp[1];
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
}
