/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardecoderlib.gis.RadarCoordinateToCRS;
import org.opengis.referencing.operation.MathTransform;


/**
 *
 * @author Jason.Burks
 */
public abstract class SpatialRenderer extends BaseRenderer {
    RadarCoordinateToCRS radarCoord;
    public SpatialRenderer(int width, int height) {
        super(width, height);
    }

    public void setTransform(MathTransform transform, double radarLon, double radarLat) {
        radarCoord = new RadarCoordinateToCRS(transform,radarLon,radarLat);
        radarCoord.intialize();
    }
}
