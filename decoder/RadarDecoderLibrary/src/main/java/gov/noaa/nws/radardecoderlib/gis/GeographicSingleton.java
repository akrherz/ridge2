/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.gis;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 *
 * @author jason
 */
public class GeographicSingleton {

    private static GeographicSingleton geoSingle;
    private CoordinateReferenceSystem crs;
    private GeographicSingleton() {
        //try {
            //crs = CRS.decode("EPSG:4326");
            crs = DefaultGeographicCRS.WGS84;
//        } catch (NoSuchAuthorityCodeException ex) {
//            Logger.getLogger(GeographicSingleton.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FactoryException ex) {
//            Logger.getLogger(GeographicSingleton.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    public static GeographicSingleton getInstance() {

        if (geoSingle == null)  {
            geoSingle = new GeographicSingleton();
        }
        return geoSingle;
    }

    public CoordinateReferenceSystem getGeographicCoordinate() {
        return crs;
    }

}
