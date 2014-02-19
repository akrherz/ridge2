/*
 * RadialDecoder.java
 *
 * Created on October 7, 2005, 11:19 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.radardecoders;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import java.io.IOException;
/**
 *
 * @author jburks
 */
public abstract class RadialDecoder extends RadarDecoder{
    int numberOfRows;
    int numberRadials;
    int lengthOfBlock;
    double scaleFactor;
    int numberRangeBins;
    double radialstartAngle;
    double radialangledelta;
    double cosOfElevationAngle,sinOfElevationAngle;
   
    /** Creates a new instance of RadialDecoder */
    public RadialDecoder(BinaryReader bindecode, int numLevels) throws IOException{
        super(bindecode,numLevels);
            bindecode.seek(58);
            elevationAngle = bindecode.getShort()*.1;
            cosOfElevationAngle = Math.cos(Math.toRadians(elevationAngle));
            sinOfElevationAngle = Math.sin(Math.toRadians(elevationAngle));
            long offsetbyte = (offsetToSymbology*2);
            bindecode.seek(offsetbyte+4);
            lengthOfBlock = bindecode.getInt();
            bindecode.seek(offsetbyte+12);
            dataLayerBytes = bindecode.getInt();
            bindecode.seek(offsetbyte);
            int blockdivider = bindecode.getShort();
            if ( blockdivider != -1) {
                throw new IOException("No Symbology Block");
            }
            bindecode.seek(offsetbyte+20);
            int numBins = bindecode.getShort();
            bindecode.seek(offsetbyte+26);
            scaleFactor = bindecode.getShort()*numBins/230.;
            numberRadials = bindecode.getShort();
            bindecode.seek(offsetbyte+18);
            int startBin = bindecode.getShort();
            
            bindecode.seek(offsetbyte+20);
            numberRangeBins = bindecode.getShort();
    }
   
  
     public int getNumberRadials() {
        return(numberRadials);
    }



    public double getHeightForRadius(double radius) {
        //Using h=((r*cos(alpha))^2/9168.66 +r*sin(alpha))*6076.115
        //returns height in feet above ground. Takes into account the height of the radar.
        double height = (((Math.pow(radius*cosOfElevationAngle,2))/9168.66)+radius*sinOfElevationAngle)*6076.115;
        return((height+radarHeight)*.3048);
        
    }


}
