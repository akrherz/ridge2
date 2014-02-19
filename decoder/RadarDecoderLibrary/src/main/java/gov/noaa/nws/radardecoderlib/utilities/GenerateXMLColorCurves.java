/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

/**
 *
 * @author Jason.Burks
 */
public class GenerateXMLColorCurves {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here//104, -32, 94.5, .5, 2, 255, true
         // new ColorCurveToXML("C:/Jason/Document/Ridge Version 2/DR20734/DR20734/colorMaps.nc","C:/temp/test.xml", 3, -32f, 94.5f, .5f, 2, 255, false);
       // new ProcessScaleInfo("C:/Jason/Document/Ridge Version 2/DR20734/DR20734/colorMaps.nc",104,"0:ND,255:RF,16:-32.5,33:-20","c:/temp/output.xml", true);
       //  new ProcessScaleInfo("/Users/jason.burks/Ridge/RadarDecoderLibrary/src/main/resources/awipscolorcurves/colorMaps.nc",29,"0:ND,1:RF,16:-4.0,32:-2.0,48:-0.5,64:0.0,80:0.25,96:0.5,112:1.0,128:1.5,144:2.0,160:2.5,176:3.0,192:4.0,208:5.0,224:6.0,240:8.0","/tmp/ZDR.xml", true);
       //  new ProcessScaleInfo("/Users/jason.burks/Ridge/RadarDecoderLibrary/src/main/resources/awipscolorcurves/colorMaps.nc",34,"0:ND,1:RF,16:-4.0,32:0.20,48:0.45,64:0.65,80:0.75,96:0.85,112:.90,128:0.93,144:0.95,160:0.96,176:0.97,192:0.98,208:0.99,224:1.0,240:1.05","/tmp/CC.xml", true);
        // new ProcessScaleInfo("/Users/jason.burks/Ridge/RadarDecoderLibrary/src/main/resources/awipscolorcurves/colorMaps.nc",35,"0:ND,1:RF,16:-2.0,32:-1.0,48:-0.5,64:0.0,80:0.25,96:0.5,112:1.0,128:1.5,144:2.0,160:2.5,176:3.0,192:4.0,208:5.0,224:7.0,240:10.0","/tmp/kdp.xml", true);
         new ProcessScaleInfo("/Users/jason.burks/Ridge/RadarDecoderLibrary/src/main/resources/awipscolorcurves/colorMaps.nc",35,"0:ND,1:RF,16:-2.0,32:-1.0,48:-0.5,64:0.0,80:0.25,96:0.5,112:1.0,128:1.5,144:2.0,160:2.5,176:3.0,192:4.0,208:5.0,224:7.0,240:10.0","/tmp/kdp.xml", true);
//       new ColorCurveToXML("C:/Jason/Document/Ridge Version 2/DR20734/DR20734/colorMaps.nc","C:/Jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/XMLConfig/ColorCurve/refoutputcolor.xml", 104, new String[0], 94.5f, -32f, -.5f, 1,new String[]{""}, true);
//       new ColorCurveToXML("C:/Jason/Document/Ridge Version 2/DR20734/DR20734/colorMaps.nc","C:/Jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/XMLConfig/ColorCurve/veloutputcolor.xml", 105,new String[]{""}, 63.0f, -63.5f, -.5f, 0, new String[]{"RF",""}, true);
    }

}
