/*
 * ColorCurveDriver.java
 *
 * Created on October 11, 2005, 9:11 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurves;


/**
 * Instaniates the proper color curve given the name
 * @author jburks
 */
public class ColorCurveFactory {
    
    /** Creates a new instance of ColorCurveDriver */
    public ColorCurveFactory() {
        
    }
    
    /**
     * Returns the color curve from the given name
     * @param name Name of the Color Curve
     * @return Color curve for given name
     */
    public static ColorCurve getColorCurve(String name) {
        if (name.equals("FourBitReflectivity")) {
            return(new FourBitReflectivity());
        } else if (name.equals("FourBitVelocity")) {
            return(new FourBitVelocity());
        } else if (name.equals("EightBitReflectivity")) {
            return(new NetCDFColorCurveReader("/home/jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/ColorCurves/colorMaps.nc", 45));
        } else if (name.equals("EightBitVelocity")) {
            return(new NetCDFColorCurveReader("/home/jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/ColorCurves/customColorMaps.nc", 1101));
        } else if (name.equals("EchoTops")) {
            return(new NetCDFColorCurveReader("/home/jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/ColorCurves/customColorMaps.nc", 1001));
        } else if (name.equals("ThreeHourlyPrecip")) {
            return(new NetCDFColorCurveReader("/home/jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/ColorCurves/colorMaps.nc", 47));
        } else if (name.equals("VADWindProfile")) {
            return(new VADWindProfile());
        }else if (name.equals("OneHourlyPrecip")) {
            return(new OneHourPrecip());
        }
        return(null);
    }
    
}
