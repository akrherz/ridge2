/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardecoderlib.gis.RadarCoordinateToCRS;
import java.awt.Color;
import org.geotools.geometry.GeneralDirectPosition;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Jason.Burks
 */
public abstract class SymbologyRenderer extends RadarSpatialRenderer {
    RadarCoordinateToCRS radarCoord;
    public SymbologyRenderer(int width, int height) {
        super(width, height,0,0);
    }

    @Override
     public  void setTransform(MathTransform transform, DirectPosition radarLocation,  double elevationAngle) {
        radarCoord = new RadarCoordinateToCRS(transform,radarLocation.getOrdinate(0),radarLocation.getOrdinate(1));
        radarCoord.intialize();
     }

     public void drawText(double x, double y, String text, Color color) {
        try {
            //Write the text by the item but offest by 2 scaled pixels
            DirectPosition pointone = radarCoord.transformXandYToCRS(new GeneralDirectPosition(x, y));
            g.setPaint(color);
            g.drawString(text, (float) pointone.getOrdinate(0), (float) pointone.getOrdinate(1));
        } catch (Exception ex) {
        }

     }


}
