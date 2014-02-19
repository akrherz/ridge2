/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

/**
 *
 * @author Jason.Burks
 */
public class StringLabel {
    String name;
    public StringLabel(String name) {
        this.name = name;
    }

    public String getString() {
        return(name);
    }

     @Override
    public String toString() {
        return("StringLabel: "+name);
    }
}
