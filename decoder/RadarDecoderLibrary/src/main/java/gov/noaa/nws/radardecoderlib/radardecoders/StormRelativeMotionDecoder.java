package gov.noaa.nws.radardecoderlib.radardecoders;

import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;

import java.io.IOException;

public class StormRelativeMotionDecoder extends FourBitRadialDecoder {

	public StormRelativeMotionDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode, numLevels);
    }
	
	public float getStormRelativeDirection() throws Exception {
		bindecode.seek(102);
		return bindecode.getShort()*.1f;
	}
	
	public float getStormRelativeSpeed() throws Exception{
		bindecode.seek(100);
		return bindecode.getShort()*.1f;
	}
	
	public float getStormRelativeElevation() throws Exception{
		bindecode.seek(58);
		return bindecode.getShort()*.1f;
	}
	
	public int getStormRelativeSourceFlag() throws Exception{
		bindecode.seek(96);
		return bindecode.getShort();
	}
	

}
