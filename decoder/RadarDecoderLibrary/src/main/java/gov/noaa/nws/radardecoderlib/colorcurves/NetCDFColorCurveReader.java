/*
 * NetCDFColorCurveReader.java
 *
 * Created on October 11, 2005, 9:24 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.colorcurves;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.ma2.Array;
import java.awt.Color;
/**
 *
 * @author jburks
 */
public class NetCDFColorCurveReader extends ColorCurve {
    Color[] colorsOut = new Color[256];
    /** Creates a new instance of NetCDFColorCurveReader */
    public NetCDFColorCurveReader(String filename, int num) {
        try {
            System.out.println("Searching for "+num);
            int numOut =-1;
            NetcdfFile netCDF = NetcdfFile.open(filename);
            Variable tableKeys = netCDF.findVariable("tableKeys");
            Variable tableNames = netCDF.findVariable("tableNames");
            Array tableNamesArray = tableNames.read();
            char[][] names = (char[][])tableNamesArray.copyToNDJavaArray();
            Array tableKeysArray = tableKeys.read();
            int[] colorIDs = (int[])tableKeysArray.copyTo1DJavaArray();
            
            for (int i=0; i<colorIDs.length; ++i) {
                //System.out.println("Checking "+i+"  "+new String(names[i]));
                if (colorIDs[i] == num) {
                    numOut = i;
                    System.out.println("Found "+colorIDs[i]+new String(names[i]));
                }
            }
            Variable tableColors = netCDF.findVariable("tableColors");
            int[] amount = new int[]{1,3,256};
            int[] start = new int[]{numOut,0,0};
            Array colorTable =  tableColors.read(start,amount);
            colorTable = colorTable.reduce();
            short[][] colors = (short[][])colorTable.copyToNDJavaArray();
            for (int j=1; j< colors[0].length+1; ++j) {
               // System.out.println("values ===="+(colors[0][j-1]&255)+" "+(colors[1][j-1]&255)+" "+(colors[2][j-1]&255));
                colorsOut[j-1]  = new Color(colors[0][j-1]&255,colors[1][j-1]&255,colors[2][j-1]&255);
               // System.out.println("Colors =="+colorsOut[j-1]);
            }
        } catch (Exception e) {
         
        }
        
    }
    
    public Color[] getColors() {
        return(colorsOut);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new NetCDFColorCurveReader("C:/Jason/Java/Projects/Ridge/RadarDecoderLibrary/src/gov/noaa/nws/RadarDecoderLib/ColorCurves/colorMaps.nc", 3);
    }
    
}
