/*
 * BoundedStringColor.java
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
public class BoundedStringColor extends BoundedColor {
      String name;
    /** Creates a new instance of BoundedStringColor */
    public BoundedStringColor(String name, Color color) {
        super(color);
        this.name = name;
    }
    public boolean isMatch(String value) {
        if (value.equals(name)) {
            return(true);
        }
        return(false);
    }
    
    public String getName(){
        return(name);
    }
    
    public String toString() {
        return(name+"  "+color.toString());
    }
}
