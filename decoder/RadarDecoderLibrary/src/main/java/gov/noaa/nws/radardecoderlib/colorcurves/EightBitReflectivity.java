/*
 * FourBitReflectivity.java
 *
 * Created on October 9, 2005, 5:27 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurves;
import java.awt.Color;
/**
 * Color Curve for Eight Bit Reflectivity
 * @author jburks
 */
public class EightBitReflectivity extends ColorCurve {
    Color[] colors = new Color[256];
     /**
     * Creates a new instance of Eight Bit Reflectivity Color Curve
     */
    public EightBitReflectivity() {
         int blue = 0;
         int red = 0;
         int green = 255;
         colors[0] = null;
         for (int i=1; i<256; ++i) {
             if (i <200) {
                 green = green -1;
                 blue = blue+1;
             } else if (i>200) {
                 green =0;
                 blue = blue -2;
                 red = red+2;
             }
             colors[i] = new Color(red,green,blue);
         }
    }

    /**
     * Returns array of Colors
     * @return get array of colors from this color curve
     */
    public Color[] getColors() {
        return(colors);
    }
    
}
