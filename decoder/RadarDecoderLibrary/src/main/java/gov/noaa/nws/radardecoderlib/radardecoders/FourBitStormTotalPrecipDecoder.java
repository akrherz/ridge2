package gov.noaa.nws.radardecoderlib.radardecoders;

import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;

import java.io.IOException;
import java.util.Date;

public class FourBitStormTotalPrecipDecoder extends FourBitRadialDecoder implements StormTotalPrecipDecoder{

	public FourBitStormTotalPrecipDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode, numLevels);
    }
	
	
	
	

	public Date getStormTotalPrecipBegin() throws Exception {
		bindecode.seek(94);
        int oneScan = bindecode.getShort();
        int twoScan = bindecode.getShort();
        return(new Date((long)((twoScan*60.+(oneScan-1)*86400.)*1000.)));
	}
	
	public Date getStormTotalPrecipEnd() throws Exception {
		bindecode.seek(98);
        int oneScan = bindecode.getShort();
        int twoScan = bindecode.getShort();
        return(new Date((long)((twoScan*60.+(oneScan-1)*86400.)*1000.)));
	}
}
