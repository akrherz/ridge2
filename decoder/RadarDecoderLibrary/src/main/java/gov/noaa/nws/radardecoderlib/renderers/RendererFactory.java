/*
 * RendererDriver.java
 *
 * Created on October 11, 2005, 10:09 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.renderers;

import gov.noaa.nws.radardata.RadarProductData;

/**
 *
 * @author jburks
 */
public class RendererFactory {
    
    /** Creates a new instance of RendererDriver */
    public RendererFactory() {
    }
    
    public static RadarSpatialRenderer getRadarRenderer(RadarProductData data, int width, int height) {
        String name = data.getRendererType();
         if (name.equals("MesocycloneRenderer")) {
            return(new MesocycloneRenderer(width, height));
        } else if (name.equals("TVSRenderer")) {
            return(new TVSRenderer(width, height));
        } else if (name.equals("StormTrackRenderer")) {
            return(new StormTrackRenderer(width, height));
        }else if (name.equals("RadialRenderer")) {
            return (new RadialSpatialRenderer(width, height, data.getResolutionInMeters(), data.getRangeOfProductInMeters()));
        } else if (name.equals("CompositeRenderer")) {
            return (new CompositeSpatialRenderer(width, height, data.getResolutionInMeters(), data.getRangeOfProductInMeters()));
        } else if (name.equals("VADWindProfileRenderer")) {
            return (new VADWindRenderer(width, height));
        }
        return(null);
    }
    

}
