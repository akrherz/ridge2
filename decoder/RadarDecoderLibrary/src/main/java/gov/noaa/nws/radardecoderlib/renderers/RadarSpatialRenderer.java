/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Jason.Burks
 */
public abstract class RadarSpatialRenderer extends BaseRenderer {
    double binWidth, productRange;

    public RadarSpatialRenderer(int width, int height, double binWidth, double productRange) {
        super(width,height);
        this.binWidth = binWidth;
        this.productRange = productRange;
    }

    public abstract void setTransform(MathTransform transform, DirectPosition radarLocation,  double elevationAngle);
    
}
