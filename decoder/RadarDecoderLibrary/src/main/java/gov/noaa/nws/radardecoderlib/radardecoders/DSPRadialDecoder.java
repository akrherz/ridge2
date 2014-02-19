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
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.DoubleThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import java.io.IOException;
/**
 *
 * @author jburks
 */
public class DSPRadialDecoder extends RadialDecoder {
    
    int blockid;
    public DSPRadialDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode,numLevels);
    } 
    
   
    
    protected void process() throws IOException {
        //Move to begin of data 
        //read all rows and put into the data array
        lengthOfBlock = lengthOfBlock - 16 - 14;
        int numRadialsMul = numberRadials;
        int[][] dataRows = new int[numRadialsMul][];
        double[] startAngle =  new double[numRadialsMul];
        double[] endAngle =  new double[numRadialsMul];
        bindecode.seek((int)(offsetToSymbology*2)+30);
        boolean setup = false;
        boolean notified = false;
        for (int i=0; i<numRadialsMul; ++i) {
            long bytesleft = bindecode.getShort();
            radialstartAngle = bindecode.getShort()*.1;
            radialangledelta = bindecode.getShort()*.1;
            lengthOfBlock = lengthOfBlock - 6;
            startAngle[i] = radialstartAngle;
            endAngle[i] = radialangledelta;
            int[] bin = new int[1000];
            int start =0;
            int rangebins =0;
            while (bytesleft > 0) {
               lengthOfBlock = lengthOfBlock - 1;
               bin[rangebins]=bindecode.getByteAsInt();
                ++rangebins;
                --bytesleft;
            }
            dataRows[i] = new int[rangebins];
            System.arraycopy(bin, 0, dataRows[i], 0, rangebins);
            data.add(new RadialData(dataRows[i],radialstartAngle,radialangledelta));
            rangebins=0;
        }
    }

    public Threshold[] getThresholds() throws IOException {
        
       Threshold[] thresholds = new Threshold[256];
            bindecode.seek(92);
            double maxPrecip = bindecode.getShort()*.01;
            boolean foundMul = false;
            int count = 1;
            double scalingMul = .01;
            while (foundMul == false) {
                if ((maxPrecip > ((float)count-1)*2.55) && (maxPrecip <= ((float)count)*2.55)) {
                    foundMul = true;
                    scalingMul = ((float)count)*.01;
                } else {
                    ++count;
                }
            }
            
            bindecode.seek(60);
            thresholds[0]= new DoubleThreshold(0);
            double min = bindecode.getShort()/10.;
            double increment = bindecode.getShort()/10.;
            double max = bindecode.getShort()/10.;
            for (int i=0; i< 255; ++i){
                thresholds[i] = new DoubleThreshold(i*scalingMul);
            }
        return(thresholds);
        
    }
}
