/*
 * DoubleThreshold.java
 *
 * Created on August 3, 2006, 7:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.radardecoders.thresholds;

/**
 *
 * @author Jason.Burks
 */
public class DoubleThreshold extends Threshold{
    double value;
    int optionalIndicator;
    public final static int GREATER_THAN = 0;
    public final static int LESS_THAN = 1;
    boolean indicatorSet = false;
    /** Creates a new instance of DoubleThreshold */
    public DoubleThreshold(double value) {
        this.value = value;
    }
    public DoubleThreshold(double value, int optionalIndicator) {
        this.value = value;
        this.optionalIndicator = optionalIndicator;
        indicatorSet = true;
    }
    
    public double getValue(){
        return(value);
    }
    
    public int getIndicator() {
        return(optionalIndicator);
    }
    
    public boolean isIndicatorSet(){
        return(indicatorSet);
    }
    
    public String toString() {
        if (indicatorSet) {
            return(value+" "+optionalIndicator);
        } else {
            return(String.valueOf(value));
        }
    }
}
