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

import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.StringThreshold;
import gov.noaa.nws.radardata.RadialData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.DoubleThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import java.io.IOException;

/**
 * 
 * @author jburks
 */
public class EightBitRadialDecoder extends RadialDecoder {

	public EightBitRadialDecoder(BinaryReader bindecode, int numLevels)
			throws IOException {
		super(bindecode, numLevels);
	}

	protected void process() throws IOException {
		// Move to begin of data
		// read all rows and put into the data array
		lengthOfBlock = lengthOfBlock - 16 - 14;
		bindecode.seek((int) (offsetToSymbology * 2) + 30);
		boolean setup = false;
		boolean notified = false;

		for (int i = 0; i < numberRadials; ++i) {
			long bytesleft = bindecode.getShort();
			radialstartAngle = bindecode.getShort() * .1;
			radialangledelta = bindecode.getShort() * .1;
			lengthOfBlock = lengthOfBlock - 6;
			int[] bin = new int[numberRangeBins];
			int start = 0;
			int rangebins = 0;
			while (bytesleft > 0) {
				lengthOfBlock = lengthOfBlock - 1;
				bin[rangebins] = bindecode.getByteAsInt();
				++rangebins;
				--bytesleft;
			}
			data.add(new RadialData(bin, radialstartAngle, radialangledelta));
			bin = null;
			rangebins = 0;
		}

	}

	public Threshold[] getThresholds() throws IOException {

		Threshold[] thresholds = new Threshold[256];
		bindecode.seek(60);
		int startNumber = 0;
		int endNumber = 255;
		if (messageCode == 32 || messageCode == 180 || messageCode == 186) {
			startNumber = 2;
			thresholds[0] = new StringThreshold("ND");
			thresholds[1] = new StringThreshold("MIS");
			double min = bindecode.getShort() / 10.;
			double increment = bindecode.getShort() / 10.;
			double maxNum = bindecode.getShort() + startNumber;
			// This is a bug fix until the TDWR TZL product is fixed in the next
			// build. Right now it outputs ND,5,10, which translates to
			// -3276.6,.5, and 1, which is wrong.
			if (min == -3276.6) {
				min = -32.0;
				increment = .5;
				maxNum = 256;
			}
			for (int i = startNumber; i < maxNum; ++i) {

				thresholds[i] = new DoubleThreshold(min + (i - startNumber)
						* increment);
			}
		} else if (messageCode == 81) {
			startNumber = 2;
			thresholds[0] = new StringThreshold("ND");
			thresholds[1] = new StringThreshold("MIS");
			double min = bindecode.getShort() / 10.;
			double increment = bindecode.getShort() / 10.;
			double maxNum = bindecode.getShort() + startNumber;

			for (int i = startNumber; i < maxNum; ++i) {
				thresholds[i] = new DoubleThreshold(min + (i - startNumber)
						* increment);
			}
		} else if (messageCode == 182 || messageCode == 99 | messageCode == 93
				|| messageCode == 154) {
			// products come out as m/s*10 need to convert to knots
			startNumber = 2;
			thresholds[0] = new StringThreshold("ND");
			thresholds[1] = new StringThreshold("RF");
			double min = (bindecode.getShort() / 10.) * 1.9438444924406;
			double increment = (bindecode.getShort() / 10.) * 1.9438444924406;
			double maxNum = bindecode.getShort() + startNumber;

			for (int i = startNumber; i < maxNum; ++i) {
				thresholds[i] = new DoubleThreshold(min + (i - startNumber)
						* increment);
			}
		} else if (messageCode == 159 || messageCode == 161 || messageCode == 163 ) {
			
			startNumber = 2;
			thresholds[0] = new StringThreshold("ND");
			thresholds[1] = new StringThreshold("RF");

			double scale = bindecode.getFloat();
			double offset = bindecode.getFloat();
			
			for (int i = startNumber; i < 255; ++i) {
				thresholds[i] = new DoubleThreshold((i - offset) / scale);
			}
		}else if (messageCode == 165  || messageCode == 177) {
				thresholds[0] = new StringThreshold("ND");
				thresholds[10] = new StringThreshold("BI");
				thresholds[20] = new StringThreshold("GC");
				thresholds[30] = new StringThreshold("IC");
				thresholds[40] = new StringThreshold("DS");
				thresholds[50] = new StringThreshold("WS");
				thresholds[60] = new StringThreshold("RA");
				thresholds[70] = new StringThreshold("HR");
				thresholds[80] = new StringThreshold("BD");
				thresholds[90] = new StringThreshold("GR");
				thresholds[100] = new StringThreshold("HA");
				thresholds[140] = new StringThreshold("UK");
				thresholds[150] = new StringThreshold("RF");
				
		} else if (messageCode == 170 || messageCode == 172 || messageCode == 173 || messageCode == 174 || messageCode == 175 ) {
			
			startNumber = 1;
			thresholds[0] = new StringThreshold("ND");

			double scale = bindecode.getFloat();
			double offset = bindecode.getFloat();
			
			for (int i = startNumber; i < 255; ++i) {
				if (messageCode == 170 || messageCode == 173 || messageCode == 174 || messageCode == 175) {
					thresholds[i] = new DoubleThreshold(0.01*(i - offset) / scale);
				} else if (messageCode == 172) {
					thresholds[i] = new DoubleThreshold(0.01*(i - offset));
				} else {
					thresholds[i] = new DoubleThreshold((i - offset) / scale);
				}
				
			}
			
		} else if (messageCode == 176) {
		//TODO need to fix this
//		
//			startNumber = 0;
//
//			double scale = bindecode.getFloat();
//			double offset = bindecode.getFloat();
//			System.out.println("Scale=" + scale);
//			System.out.println("offset=" + offset);
//			for (int i = startNumber; i < 655; ++i) {
//				thresholds[i] = new DoubleThreshold((i - offset) / scale);
//				System.out.println("Thresholds " + thresholds[i]);
//			}
		} else {
			startNumber = 2;
			thresholds[0] = new StringThreshold("ND");
			thresholds[1] = new StringThreshold("MIS");
			double min = bindecode.getShort() / 10.;
			double increment = bindecode.getShort() / 10.;
			double maxNum = bindecode.getShort() + startNumber;
			
			for (int i = startNumber; i < maxNum; ++i) {
				thresholds[i] = new DoubleThreshold(min + (i - startNumber)
						* increment);
			}
		}
		return (thresholds);

	}
}
