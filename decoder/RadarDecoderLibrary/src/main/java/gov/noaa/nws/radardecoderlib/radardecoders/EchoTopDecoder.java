package gov.noaa.nws.radardecoderlib.radardecoders;

import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;

import java.io.IOException;

public class EchoTopDecoder extends CompositeDecoder{
	
	private boolean isNull = false;
	
	public EchoTopDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode, numLevels);
        checkForNull();
    }
	
	private void checkForNull() {
		try {
			bindecode.seek(92);
		
			int isNullValue = bindecode.getShort();
			if (isNullValue == 0) {
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
}
