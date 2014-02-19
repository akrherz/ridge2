/*
 * StringThreshold.java
 *
 * Created on August 3, 2006, 7:47 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.radardecoders.thresholds;

/**
 *
 * @author Jason.Burks
 */
public class StringThreshold extends Threshold{
    String string;
    /** Creates a new instance of StringThreshold */
    public StringThreshold(String string) {
        this.string = string;
    }
    
    public String getString() {
        return(string);
    }
    
    public String toString() {
        return(string);
    }
}
