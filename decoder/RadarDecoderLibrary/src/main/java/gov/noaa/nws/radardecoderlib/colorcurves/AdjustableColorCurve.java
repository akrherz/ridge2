/*
 * AdjustableColorCurve.java
 *
 * Created on July 30, 2006, 1:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurves;

import java.awt.Color;

/**
 *
 * @author jason.burks
 */
public interface AdjustableColorCurve {
    public void setColorCurve(Color[] colors);
}
