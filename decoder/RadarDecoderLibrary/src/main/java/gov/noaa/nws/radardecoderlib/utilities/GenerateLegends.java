/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

/**
 *
 * @author Jason.Burks
 */
public class GenerateLegends {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//         new LegendFromColorCurveXML("C:/temp/refoutputcolor.xml", "-20 -10 0 10 20 30 40 50 60 70 80", "C:/temp/legendReflec.png", "DBZ");
//          new LegendFromColorCurveXML("C:/temp/stpoutputcolor.xml", "0 .1 .3 .6 1 1.5 2 2.5 3 4 5 6 8 10 12 15", "C:/temp/legendSTP.png", "IN");
//          new LegendFromColorCurveXML("C:/temp/veloutputcolor.xml","RF -100 -90 -80 -70 -60 -50 -40 -30 -20 -10 0 10 20 30 40 50 60 70 80 90 100", "C:/temp/legendVel.png", "KTS");
//     new LegendFromColorCurveXML("C:/Jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/XMLConfig/ColorCurve/refoutputcolor.xml", "C:/temp/legendReflec.png", "DBZ");
//       new LegendFromColorCurveXML("C:/Jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/XMLConfig/ColorCurve/veloutputcolor.xml", "C:/temp/legendVelocity.png", "KTS");
    	new LegendFromColorCurveXML("/Users/jason.burks/Ridge/RadarDecoderLibrary/src/main/resources/colorcurves/ZDRColorCurveManager.xml", "RF -4.0 -3.0 -2.0 -1.0 0.0 1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0", "/tmp/zdr.png", "DB");
    	new LegendFromColorCurveXML("/Users/jason.burks/Ridge/RadarDecoderLibrary/src/main/resources/colorcurves/HIDColorCurveManager.xml", "RF UK HA GR BD HR RA WS DS IC GC BI", "/tmp/hid.png", " ");
    
    
    }

}
