/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author jason
 */
public class CoordinateHolder {
    private MathTransform transform;
    private DirectPosition upperLeft;
    private DirectPosition lowerRight;

    public CoordinateHolder(MathTransform transform, DirectPosition upperLeft, DirectPosition lowerRight) {
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
    public DirectPosition getUpperLeft() {
        return upperLeft;
    }

    /**
     * @return the lowerRight
     */
    public DirectPosition getLowerRight() {
        return lowerRight;
    }

}
