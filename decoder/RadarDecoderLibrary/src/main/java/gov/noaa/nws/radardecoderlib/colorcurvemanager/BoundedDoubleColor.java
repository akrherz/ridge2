/*
 * BoundedNumericalColor.java
 *
 * Created on August 3, 2006, 9:17 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurvemanager;

import java.awt.Color;

/**
 *
 * @author Jason.Burks
 */
public class BoundedDoubleColor extends BoundedColor{
    private double lowerValue=Double.MAX_VALUE;
    private double upperValue=Double.MIN_VALUE;
    final public static int LOWER =0;
    final public static int UPPER =1;
    /** Creates a new instance of BoundedNumericalColor */

    public BoundedDoubleColor(double lowerValue, double upperValue, Color color) {
        super(color);
        this.lowerValue = lowerValue;
        this.upperValue = upperValue;
        this.color = color;
    }
    
    public BoundedDoubleColor(double value, Color color, int bound ) {
        super(color);
        if (bound == LOWER) {
            this.lowerValue = value;
        } else {
            this.upperValue = value;
        }
        this.color = color;
    }
    public boolean isMatch(double value) {
        if (value >= lowerValue && value < upperValue) {
            return(true);
        }
        return(false);
    }
    
    public String toString() {
        return(lowerValue+" "+upperValue+"  "+color.toString());
    }

    public double getLowerValue() {
        return(lowerValue);
    }
    
}
