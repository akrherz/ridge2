/*
 * DecoderInterface.java
 *
 * Created on October 7, 2005, 10:53 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.radardecoders;

import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.opengis.geometry.DirectPosition;

/**
 *
 * @author jburks
 */
public interface DecoderInterface {
    public ArrayList decode() throws IOException;
    public Threshold[] getThresholds() throws IOException;
    public DirectPosition getRadarLocation();
    public  double getRadarHeight();
    public int getRadarID();
    public Date getRadarScanTime();
    public Date getRadarGenerationTime();
    public int getVCP();
    public double getElevationAngle();
    public int getMessageCode();
}
