/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

/**
 *
 * @author jason
 */
public class StringRelation implements Relation{
    double value;
    String name;
    public StringRelation(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public boolean isContained(double value) {
        if (this.value == value) return true;
        return false;

    }

    public String getName() {
        return name;
    }

    public String toString() {
        return("Relation: "+name);
    }
}
