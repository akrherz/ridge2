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
 *
 * @author jburks
 */
public class FourBitVelocity extends ColorCurve { 
    Color[] colors = new Color[16];
     /** Creates a new instance of FourBitReflectivity */
    public FourBitVelocity() {
        colors[0] = null;
        colors[1] = new Color(2,252,2);
        colors[2] =new Color(1,228,1);
        colors[3] = new Color(1,197,1);
        colors[4] = new Color(7,172,4);
        colors[5] = new Color(6,143,3);
        colors[6] = new Color(4,114,2);
        colors[7] = new Color(124,151,123);
        colors[8] = new Color(152,119,119);
        colors[9] = new Color(137,0,0);
        colors[10] = new Color(162,0,0);
        colors[11] = new Color(185,0,0);
        colors[12] = new Color(216,0,0);
        colors[13] = new Color(239,0,0);
        colors[14] = new Color(254,0,0);
        colors[15] = new Color(144,0,160);
    }

    public Color[] getColors() {
        return(colors);
    }
    
}
