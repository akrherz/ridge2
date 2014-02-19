/*
 * ColorCurvesInteface.java
 *
 * Created on October 9, 2005, 7:50 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurves;
import java.awt.Color;
/**
 * Provides basic interface to get Colors from a given color curve
 * @author jburks
 */
public interface ColorCurvesInterface {
    /**
     * Get an array of colors from the color curve
     * @return Array of colors from the color curve
     */
    public Color[] getColors();
}
