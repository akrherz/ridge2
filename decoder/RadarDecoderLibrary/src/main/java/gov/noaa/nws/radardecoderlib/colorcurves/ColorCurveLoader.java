/*
 * ColorCurveLoader.java
 *
 * Created on October 7, 2005, 9:46 PM
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
public class ColorCurveLoader {
    
    /** Creates a new instance of ColorCurveLoader */
    public ColorCurveLoader() {
    }
    
     public static Color[] getColorCurve(int messageCode) {
         Color[] colors;
         switch (messageCode) {
             case 16:  return(FourBitReflectivityColors());
             case 17:  return(FourBitReflectivityColors());
             case 18:  return(FourBitReflectivityColors());
             case 19:  return(FourBitReflectivityColors());
             case 20:  return(FourBitReflectivityColors());
             case 22:  return(FourBitVelocityColors());
             case 23:  return(FourBitVelocityColors());
             case 24:  return(FourBitVelocityColors());
             case 25:  return(FourBitVelocityColors());
             case 26:  return(FourBitVelocityColors());
             case 27:  return(FourBitVelocityColors());
             
             case 94: return(EightBitColorArray());
             case 99: return(EightBitColorArray());
             
             default: return(null);
         }
     } 
     private static Color[] FourBitReflectivityColors() {
        Color[] colors = new Color[16];
        colors[0] = new Color(0,0,0,0);
        colors[1] = new Color(4,233,231);
        colors[2] =new Color(1,159,244);
        colors[3] = new Color(3,0,244);
        colors[4] = new Color(2,253,2);
        colors[5] = new Color(1,197,1);
        colors[6] = new Color(0,142,0);
        colors[7] = new Color(253,248,2);
        colors[8] = new Color(229,188,0);
        colors[9] = new Color(253,139,0);
        colors[10] = new Color(254,0,0);
        colors[11] = new Color(212,0,0);
        colors[12] = new Color(188,0,0);
        colors[13] = new Color(248,0,253);
        colors[14] = new Color(152,84,198);
        colors[15] = new Color(253,253,253);
        return(colors);
    }
    private static Color[] FourBitVelocityColors() {
        Color[] colors = new Color[16];
        colors[0] = new Color(0,0,0,0);
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
        return(colors);
    }
    
     private static Color[]  EightBitColorArray() {
         Color[] colors = new Color[256];
         int blue = 0;
         int red = 0;
         int green = 255;
         colors[0] = new Color(0,0,0,0);
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
          return(colors);
     }
    
     
}
