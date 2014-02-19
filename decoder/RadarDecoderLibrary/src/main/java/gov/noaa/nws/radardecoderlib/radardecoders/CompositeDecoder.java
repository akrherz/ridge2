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
import gov.noaa.nws.radardata.CompositeRowData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import java.io.IOException;
/**
 *
 * @author jburks
 */
public class CompositeDecoder extends RadarDecoder {


    public CompositeDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode, numLevels);

    }

    protected void process() throws IOException {
        long offsetbyte = (offsetToSymbology * 2);
        bindecode.seek(offsetbyte + 34);
        bindecode.seek(offsetbyte + 4);
        int lengthOfBlock = bindecode.getInt();
        
//       //Move to begin of data
        //read all rows and put into the data array
        lengthOfBlock = lengthOfBlock - 16 - 22;
      
        bindecode.seek((int) offsetbyte + 34);
        int numberRows = bindecode.getShort();
        bindecode.seek((int) offsetbyte + 38);
        int i = 0;
        int numberInRows = -1;
        while (lengthOfBlock > 0) {
            long bytesleft = bindecode.getShort();
            int[] rowData = new int[numberRows];
            lengthOfBlock -= 2;
            int start = 0;
            while (bytesleft > 0) {
                int[] myints = bindecode.read4bitInt();
                int endamount = (start + myints[1]);
                for (int j = start; j < endamount; ++j) {
                    rowData[j] = myints[0];
                }
                numberInRows = endamount;
                start = endamount;
                --bytesleft;
                --lengthOfBlock;
            }

            data.add(new CompositeRowData(rowData, i));
            rowData = null;
            ++i;
        }
        

    }
}
