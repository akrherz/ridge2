/*
 * FourBitRadialDecoder.java
 *
 * Created on October 7, 2005, 10:44 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.radardecoders;
import gov.noaa.nws.radardata.RadialData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import java.io.IOException;
/**
 *
 * @author jburks
 */
public class FourBitRadialDecoder extends RadialDecoder {

    public FourBitRadialDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode, numLevels);
    }


    protected void process() throws IOException {
        //Move to begin of data
        //read all rows and put into the data array
        lengthOfBlock = lengthOfBlock - 16 - 14;
        bindecode.seek((int) (offsetToSymbology * 2) + 30);
        //System.out.println("Processing ");
        int i = 0;
        int[] myints;
        while (lengthOfBlock > 0) {
            long halfwordsleft = bindecode.getShort();
            radialstartAngle = bindecode.getShort() * .1;
            radialangledelta = bindecode.getShort() * .1;
            lengthOfBlock = lengthOfBlock - 6;
            int start = 0;
            int rangebins = 0;
            int[] bin = new int[numberRangeBins];
            while (halfwordsleft > 0) {
                myints = bindecode.read4bitInt();
                lengthOfBlock = lengthOfBlock - 1;
                int endamount = (start + myints[1]);
                for (int j = start; j < endamount; ++j) {
                    bin[rangebins] = myints[0];
                    ++rangebins;
                }
                start = endamount;
                myints = bindecode.read4bitInt();
                lengthOfBlock = lengthOfBlock - 1;
                endamount = (start + myints[1]);
                for (int j = start; j < endamount; ++j) {
                    bin[rangebins] = myints[0];
                    ++rangebins;
                }
                start = endamount;
                --halfwordsleft;
            }
            data.add(new RadialData(bin, radialstartAngle, radialangledelta));
            bin=null;
            ++i;
            rangebins = 0;

        }

    }
}
