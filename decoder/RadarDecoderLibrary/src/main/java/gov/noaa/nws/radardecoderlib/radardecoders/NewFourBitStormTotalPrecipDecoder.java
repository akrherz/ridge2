package gov.noaa.nws.radardecoderlib.radardecoders;

import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;

import java.io.IOException;
import java.util.Date;

public class NewFourBitStormTotalPrecipDecoder extends FourBitRadialDecoder implements StormTotalPrecipDecoder{
	private boolean isNull = false;
	public NewFourBitStormTotalPrecipDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode, numLevels);
        checkForNull();
    }
	
	private void checkForNull() {
		try {
			bindecode.seek(58);
		
			int isNullValue = bindecode.getShort();
			if (isNullValue != 0) {
				isNull = true;
			} else {
				isNull = false;
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void process() throws IOException{
		if (!isNull) {
			super.process();
		} 
	}

	public Date getStormTotalPrecipBegin() throws Exception {
		bindecode.seek(52);
        int oneScan = bindecode.getShort();
        int twoScan = bindecode.getShort();
        return(new Date((long)((twoScan*60.+(oneScan-1)*86400.)*1000.)));
	}
	
	public Date getStormTotalPrecipEnd() throws Exception {
		bindecode.seek(94);
        int oneScan = bindecode.getShort();
        int twoScan = bindecode.getShort();
        return(new Date((long)((twoScan*60.+(oneScan-1)*86400.)*1000.)));
	}
}
