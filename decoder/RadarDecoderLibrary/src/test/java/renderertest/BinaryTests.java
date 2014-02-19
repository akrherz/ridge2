package renderertest;

import java.math.BigInteger;

import junit.framework.TestCase;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;


public class BinaryTests extends TestCase {
	
	public BinaryTests() {
		
	}

	
	public void testBits() {
		byte[] bytes = new byte[]{(byte)1,(byte)1};
		boolean[] values = BinaryUtilities.bitsFromBytes(bytes);
		for (int i=0; i< values.length; ++i) {
			System.out.println("Value = "+i+"   "+values[i]);
		}
	}
}
