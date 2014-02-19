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
public class OneHourPrecip extends ColorCurve { 
    Color[] colors = new Color[16];
     /** Creates a new instance of FourBitReflectivity */
    public OneHourPrecip() {
        colors[0] = null;
        colors[1] = new Color(4,233,231);
        colors[2] =new Color(1,159,244);
        colors[3] = new Color(3,0,244);
        colors[4] = new Color(2,253,2);
        colors[5] = new Color(1,197,1);
        colors[6] = new Color(0,142,0);
        colors[7] = new Color(253,248,2);
        colors[8] = new Color(229,188,0);
        colors[9] = new Color(253,149,0);
        colors[10] = new Color(253,0,0);
        colors[11] = new Color(212,0,0);
        colors[12] = new Color(188,0,0);
        colors[13] = new Color(248,0,253);
        colors[14] = new Color(152,84,198);
        colors[15] = new Color(253,253,253);
    }

    public Color[] getColors() {
        return(colors);
    }
    
}
