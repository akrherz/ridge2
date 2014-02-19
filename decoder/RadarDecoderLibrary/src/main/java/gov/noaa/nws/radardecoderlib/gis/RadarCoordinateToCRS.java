/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.geometry.GeneralDirectPosition;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.NoninvertibleTransformException;
import org.opengis.referencing.operation.TransformException;

/**
 *
 * @author jason
 */
public class RadarCoordinateToCRS {
    MathTransform geographicToCRS;
    MathTransform CRSToGeographic;
    RadarCoordinate radarCoord = new RadarCoordinate();
    boolean hasCRS = false;
    double radarLon, radarLat;

    public RadarCoordinateToCRS(MathTransform geoGraphicToCRS, double radarLon, double radarLat) {
           this.radarLat = radarLat;
           this.radarLon = radarLon;
            try {
            this.geographicToCRS = geoGraphicToCRS;
            if (geoGraphicToCRS != null) {
                hasCRS = true;
                CRSToGeographic = geoGraphicToCRS.inverse();
            }
        } catch (NoninvertibleTransformException ex) {
            Logger.getLogger(RadarCoordinateToCRS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void intialize() {
        radarCoord.setup(radarLon, radarLat);
    }

    

    public double[] convertGeoGraphicToCRS(double[] postIn) throws Exception{
        try {
            double[] output = new double[postIn.length];
            geographicToCRS.transform(postIn, 0, output, 0, postIn.length);
            return(output);
        } catch (TransformException ex) {
            Logger.getLogger(RadarCoordinateToCRS.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Problem");

        }

    }

    public DirectPosition[] convertGeoGraphicToCRS(DirectPosition[] postIn) throws Exception{
        try {
            int num = postIn.length;
            DirectPosition[] output = new DirectPosition[num];
            for (int i=0; i< num; ++i) {
            	
            		output[i] = new GeneralDirectPosition(0,0);
            		geographicToCRS.transform(postIn[i],output[i]);
            
                
            }
            return(output);
        } catch (TransformException ex) {
            Logger.getLogger(RadarCoordinateToCRS.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Problem");

        }

    }
    public DirectPosition convertGeoGraphicToCRS(DirectPosition postIn) throws Exception{
        try {
            DirectPosition output = new GeneralDirectPosition(0,0);
                geographicToCRS.transform(postIn,output);

           // System.out.println("Output ===="+output[0]);
            return(output);
        } catch (TransformException ex) {
            Logger.getLogger(RadarCoordinateToCRS.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Problem");

        }

    }

    public DirectPosition transformXandYToCRS(DirectPosition point) throws Exception {
        if (hasCRS) {
            try {
                return this.convertGeoGraphicToCRS(radarCoord.getPoint(point));
            } catch (Exception ex) {
                Logger.getLogger(RadarCoordinateToCRS.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("Problem");
            }
       } else {
           return(radarCoord.getPoint(point));
       }

    }

    public boolean isTransformAvailable() {
        if (geographicToCRS != null) {
            return true;
        }
        return false;
    }
}
