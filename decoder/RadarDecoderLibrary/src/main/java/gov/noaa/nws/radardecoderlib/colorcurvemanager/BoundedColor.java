/*
 * BoundedColor.java
 *
 * Created on July 31, 2006, 9:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurvemanager;

import java.awt.Color;

/**
 *
 * @author jason.burks
 */
public class BoundedColor {
    Color color;
    
  
    /** Creates a new instance of BoundedColor */
    public BoundedColor(Color color) {
        this.color = color;
    }
    
    public Color getColor(){
        return(color);
    }
    
    public String toString() {
        return(color.toString());
    }
    
}
