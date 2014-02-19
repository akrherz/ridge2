/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

/**
 *
 * @author Jason.Burks
 */
public class DoubleLabel {
    double value;
    public DoubleLabel(double value) {
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    @Override
    public String toString() {
        return("DoubleLabel: "+value);
    }
}
