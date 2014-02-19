package renderertest;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import gov.noaa.nws.radardecoderlib.binaryutils.MappedByteBufferReader;
import gov.noaa.nws.radardecoderlib.builders.ProductConfigReader;
import gov.noaa.nws.radardecoderlib.colorcurvemanager.ColorCurveManager;
import gov.noaa.nws.radardecoderlib.gis.CoordinateHolder;
import gov.noaa.nws.radardecoderlib.gis.CreateWorldFileFromRadar;
import gov.noaa.nws.radardecoderlib.gis.GeographicsCoordinateFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.DecoderFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.RadarDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.FourBitStormTotalPrecipDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import gov.noaa.nws.radardecoderlib.renderers.RadarSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RendererFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import junit.framework.TestCase;

public class ProductTests extends TestCase {
	int width  = 800;
    int height = 800;
	public void testSTP() {
		try {
			output("/products/tv0/MEM_20100922_1836_TV0","/tmp/stp.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testNET() {
		try {
			output("/products/net/HTX_20060808_1350_NET","/tmp/net.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testNET2() {
		try {
			output("/products/net/AEC_20120118_1821_NET","/tmp/net2.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testN0Q() {
		try {
			output("/products/n0q/KFWD_SDUS54_N0QFWS_201010180348","/tmp/n0q.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testN0U() {
		try {
			output("/products/n0u/KFWD_SDUS54_N0UFWS_201010180222","/tmp/n0u.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testNCR() {
		try {
			output("/products/ncr/KFWD_SDUS54_NCRFWS_201010180604","/tmp/ncr.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void test4bitReflect() {
		try {
			output("/products/4bitreflec/FWS_20030406_0156_N0R","/tmp/4bitreflec.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testComp8bitReflect() {
		try {
			output("/products/n0q/N0Q_20110106_1819","/tmp/n0q.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testTZL() {
		try {
			output("/products/tzl/TZL_20110106_1850","/tmp/tzl.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testoldNOAAPortN0S() {
		try {
			output("/products/oldnoaaport/n0s/N0S_20110106_1839","/tmp/oldnoaaport_n0s.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	public void testPTA() {
		try {
			output("/products/pta/LGX_20111115_1829_PTA","/tmp/pta.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testPTA2() {
		try {
			output("/products/pta/PBZ_20120119_1312_PTA","/tmp/pta2.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testNTP() {
		try {
			output("/products/ntp/ABC_20080723_1800_NTP","/tmp/ntp.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	public void testNTP2() {
		try {
			output("/products/ntp/HTX_20060808_1235_NTP","/tmp/ntp2.png");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception "+e.getMessage());
			
		}
	}
	
	private void output(String input, String output) throws Exception{
            BinaryReader reader = new MappedByteBufferReader(ProductTests.class.getResource(input).getFile());
            if (BinaryUtilities.isZlibCompressed(reader) ){
				BinaryUtilities.zlibUncompress(reader);
			}
                BinaryUtilities.setupHeaderOffset(reader);
       
                if (BinaryUtilities.isBZip2Compressed(reader)) {
                    reader = BinaryUtilities.unCompress(reader);
                }
                reader.seek(0);
                System.out.println("Message Code = "+RadarDecoder.getMessageCode(reader));
                reader.seek(0);
                RadarProductData radarData = ProductConfigReader.getInstance().getProductData(RadarDecoder.getMessageCode(reader));
                RadarDecoder decoder = DecoderFactory.getDecoder(radarData, reader);
                System.out.println("Decoder "+decoder);
                System.out.println("Message Code " + decoder.getMessageCode());
                System.out.println("Scan Time ==" + decoder.getRadarScanTime());
                System.out.println("Generation Time ==" + decoder.getRadarGenerationTime());
                if (decoder instanceof FourBitStormTotalPrecipDecoder) {
                	System.out.println("Precip begin Time ==" + ((FourBitStormTotalPrecipDecoder)decoder).getStormTotalPrecipBegin());
                	System.out.println("Precip end Time ==" + ((FourBitStormTotalPrecipDecoder)decoder).getStormTotalPrecipEnd());
                }
                ArrayList<RadarData> data = decoder.decode();
                RadarSpatialRenderer renderer = RendererFactory.getRadarRenderer(radarData, width, height);
                ColorCurveManager colmanager = new ColorCurveManager("/colorcurves/" + radarData.getDefaultColorCurveName() + ".xml");
                Threshold[] thresholds = decoder.getThresholds();
                renderer.setColor(colmanager.getColors(thresholds));
                renderer.setRadarData(data);
                CoordinateHolder holder = GeographicsCoordinateFactory.getTransformForGeo(width, height, decoder.getRadarLocation(), 130 * 1852.);
                renderer.setTransform(holder.getTransform(), decoder.getRadarLocation(), decoder.getElevationAngle());
                CreateWorldFileFromRadar.createWorldFileFromRadar(CreateWorldFileFromRadar.createWorldFilename(output), holder.getUpperLeft(), holder.getLowerRight(), width, height);
                BufferedImage image = renderer.render();
                ImageIO.write(image, "png", new File(output));
            
        
	}

}
