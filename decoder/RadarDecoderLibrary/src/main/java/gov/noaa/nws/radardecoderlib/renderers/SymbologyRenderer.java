/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardecoderlib.gis.RadarCoordinateToCRS;
import java.awt.Color;
import org.geotools.geometry.GeneralPosition;
import org.geotools.api.geometry.Position;
import org.geotools.api.referencing.operation.MathTransform;

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
     public  void setTransform(MathTransform transform, Position radarLocation,  double elevationAngle) {
        radarCoord = new RadarCoordinateToCRS(transform,radarLocation.getOrdinate(0),radarLocation.getOrdinate(1));
        radarCoord.intialize();
     }

     public void drawText(double x, double y, String text, Color color) {
        try {
            //Write the text by the item but offest by 2 scaled pixels
            Position pointone = radarCoord.transformXandYToCRS(new GeneralPosition(x, y));
            g.setPaint(color);
            g.drawString(text, (float) pointone.getOrdinate(0), (float) pointone.getOrdinate(1));
        } catch (Exception ex) {
        }

     }


}
