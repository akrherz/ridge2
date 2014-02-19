package renderertest;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import gov.noaa.nws.radardecoderlib.binaryutils.MappedByteBufferReader;
import gov.noaa.nws.radardecoderlib.builders.ProductConfigReader;
import gov.noaa.nws.radardecoderlib.radardecoders.DecoderFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.RadarDecoder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Test;

public class ZlibTest {
	
	public ZlibTest() {
	
	}
	
	@Test
    public void testProduct() {
 
    	
    }

	public void runFile(String file) {
		System.out.println("Starting ");
		try {
			BinaryReader binread = new MappedByteBufferReader(file);

			if (BinaryUtilities.isZlibCompressed(binread)) {
				System.out.println("It is compressed now do something");
				binread = BinaryUtilities.zlibUncompress(binread);
				binread.seek(0);
				BinaryUtilities.setupHeaderOffset(binread);
				System.out.println("Size ====" + binread.getSize());
				System.out.println("Output =" + binread.getShort());
				RadarProductData radarData = ProductConfigReader.getInstance()
						.getProductData(RadarDecoder.getMessageCode(binread));
				RadarDecoder decoder = DecoderFactory.getDecoder(radarData,
						binread);
				System.out.println("Message Code " + decoder.getMessageCode());
				System.out.println("Scan Time ==" + decoder.getRadarScanTime());
				System.out.println("Generation Time =="
						+ decoder.getRadarGenerationTime());
				ArrayList<RadarData> data = decoder.decode();
				System.out.println("data ===" + data.size());
			} else {
				System.out.println("Not Compressed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	

	public static void main(String[] args) {
		new ZlibTest().runFile("/Users/jason.burks/Data/Ridge/NewNoaaPort/HTX/NCR/NCR_20110106_1829");
	}
}
