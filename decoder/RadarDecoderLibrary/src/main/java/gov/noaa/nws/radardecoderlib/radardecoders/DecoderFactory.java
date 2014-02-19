/*
 * DecoderDriver.java
 *
 * Created on October 11, 2005, 9:25 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.radardecoders;
import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import java.io.IOException;
/**
 *
 * @author jburks
 */
public class DecoderFactory {
    
    /** Creates a new instance of DecoderDriver */
    public DecoderFactory() {
       
    }
    
    public static RadarDecoder getDecoder(RadarProductData data, BinaryReader bindecode) throws IOException {
        String decoderName = data.getDecoderType();
        if (decoderName.equals("FourBitRadialDecoder")) {
            return(new FourBitRadialDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("EightBitRadialDecoder")) {
            return(new EightBitRadialDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("StormTrackDecoder")) {
            return(new StormTrackDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("CompositeDecoder")) {
            return(new CompositeDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("MesocycloneDecoder")) {
            return(new MesocycloneDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("VADWindProfileDecoder")) {
            return(new VADWindProfileDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("DSPRadialDecoder")) {
            return(new DSPRadialDecoder(bindecode,data.getNumberOfLevels()));
        }  else if (decoderName.equals("TVSDecoder")) {
            return(new TVSDecoder(bindecode,data.getNumberOfLevels()));
        }  else if (decoderName.equals("FourBitStormTotalPrecipDecoder")) {
        	return(new FourBitStormTotalPrecipDecoder(bindecode,data.getNumberOfLevels()));
        }  else if (decoderName.equals("NewFourBitStormTotalPrecipDecoder")) {
        	return(new NewFourBitStormTotalPrecipDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("StormRelativeMotionDecoder")) {
        	return(new StormRelativeMotionDecoder(bindecode,data.getNumberOfLevels()));
        } else if (decoderName.equals("EchoTopDecoder")) {
        	return(new EchoTopDecoder(bindecode,data.getNumberOfLevels()));
        }
        return(null);
    }
}
