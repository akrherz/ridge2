/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import org.geotools.api.geometry.Position;
import org.geotools.api.referencing.operation.MathTransform;

/**
 *
 * @author jason
 */
public class CoordinateHolder {
    private MathTransform transform;
    private Position upperLeft;
    private Position lowerRight;

    public CoordinateHolder(MathTransform transform, Position upperLeft, Position lowerRight) {
        this.transform = transform;
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }

    /**
     * @return the transform
     */
    public MathTransform getTransform() {
        return transform;
    }

    /**
     * @return the upperLeft
     */
    public Position getUpperLeft() {
        return upperLeft;
    }

    /**
     * @return the lowerRight
     */
    public Position getLowerRight() {
        return lowerRight;
    }

}
