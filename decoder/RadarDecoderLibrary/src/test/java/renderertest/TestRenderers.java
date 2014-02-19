/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package renderertest;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import gov.noaa.nws.radardecoderlib.binaryutils.MappedByteBufferReader;
import gov.noaa.nws.radardecoderlib.builders.ProductConfigReader;
import gov.noaa.nws.radardecoderlib.colorcurvemanager.ColorCurveManager;
import gov.noaa.nws.radardecoderlib.colorcurves.FourBitReflectivity;
import gov.noaa.nws.radardecoderlib.gis.CoordinateHolder;
import gov.noaa.nws.radardecoderlib.gis.CreateWorldFileFromRadar;
import gov.noaa.nws.radardecoderlib.gis.GeographicsCoordinateFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.CompositeDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.DecoderFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.FourBitRadialDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.RadarDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import gov.noaa.nws.radardecoderlib.renderers.BaseRenderer;
import gov.noaa.nws.radardecoderlib.renderers.CompositeSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RadarSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RadarSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RadialSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RendererFactory;
import java.io.FilenameFilter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import org.junit.Test;

/**
 *
 * @author Jason.Burks
 */
public class TestRenderers {
    int width = 1000;
    int height = 1000;
    String fileToProcess;
    String outputname;
    
    
    public TestRenderers() {
    	
    }
    
    @Test
    public void testProduct() {
 
    	
    }
    
    public void render(String fileToProcess, String outputname) {
    	this.fileToProcess = fileToProcess;
    	this.outputname = outputname;
            
            try {
            	BinaryReader reader = new MappedByteBufferReader(fileToProcess);
            	if (BinaryUtilities.isZlibCompressed(reader)) {
            		reader = BinaryUtilities.zlibUncompress(reader);
            	}
                BinaryUtilities.setupHeaderOffset(reader);
            try {
                if (BinaryUtilities.isBZip2Compressed(reader)) {
                    reader = BinaryUtilities.unCompress(reader);
                }
                reader.seek(0);
                reader.seek(0);
                RadarProductData radarData = ProductConfigReader.getInstance().getProductData(RadarDecoder.getMessageCode(reader));
                RadarDecoder decoder = DecoderFactory.getDecoder(radarData, reader);
                System.out.println("Message Code " + decoder.getMessageCode());
                System.out.println("Scan Time ==" + decoder.getRadarScanTime());
                System.out.println("Generation Time ==" + decoder.getRadarGenerationTime());
                ArrayList<RadarData> data = decoder.decode();
                RadarSpatialRenderer renderer = RendererFactory.getRadarRenderer(radarData, width, height);
                ColorCurveManager colmanager = new ColorCurveManager("/colorcurves/" + radarData.getDefaultColorCurveName() + ".xml");
                System.out.println("Color Curve ="+"/colorcurves/" + radarData.getDefaultColorCurveName() + ".xml");
                Threshold[] thresholds = decoder.getThresholds();
                for (Threshold threshold:thresholds) {
               	System.out.println("Threshold "+threshold);
                }
                Color[] colors = colmanager.getColors(thresholds);
                for (Color color:colors) {
                	if (color != null) {
                	//System.out.println("Color "+color.getAlpha());
                	} else {
                		//System.out.println("Null");
                	}
                }
                renderer.setColor(colmanager.getColors(thresholds));
                
                renderer.setRadarData(data);
                CoordinateHolder holder = GeographicsCoordinateFactory.getTransformForGeo(width, height, decoder.getRadarLocation(), 124 * 1852.);
                renderer.setTransform(holder.getTransform(), decoder.getRadarLocation(), decoder.getElevationAngle());
                CreateWorldFileFromRadar.createWorldFileFromRadar(CreateWorldFileFromRadar.createWorldFilename(outputname), holder.getUpperLeft(), holder.getLowerRight(), width, height);
                BufferedImage image = renderer.render();
                ImageIO.write(image, "png", new File(outputname));
            } catch (Exception ex) {
                Logger.getLogger(TestRenderers.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TestRenderers.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(TestRenderers.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    	TestRenderers renderer = new TestRenderers();
    	//renderer.render("/Users/jason.burks/Data/Radar/DualPolSamples/MHX_20111115_1832_PTA","/tmp/mhx_pta.png");
    //	renderer.render("/Users/jason.burks/Data/Radar/DualPolSamples/OTX_20111115_1831_N0H","/tmp/otx_n0h.png");
    	//renderer.render("/Users/jason.burks/Data/Radar/DualPolSamples/AMA_20111115_1829_N0X","/tmp/ama_n0x.png");
    	//renderer.render("/Users/jason.burks/Data/Radar/forJason/IWX_20100922_1835_NET","/tmp/test.png");
    	//renderer.render("/Users/jason.burks/Desktop/RadarTexas/MAF_20120202_0424_NCR","/Users/jason.burks/Desktop/RadarTexas/images/MAF_20120202_0424_NCR.png");
    	renderer.render("/Users/jason.burks/Data/Radar/Ridge/N0K/KEAX_SDUS83_N0KEAX_201204152354","/Users/jason.burks/Desktop/k0x.png");
    	
    	//renderer.render("/Users/jason.burks/Downloads/stp_inx","/tmp/output32.png");
    	//renderer.render("/Users/jason.burks/Data/Radar/Ridge/N0R/ESX_20071130_2119_N0R","/tmp/ESX_20071130_2119_N0R_INDEXED.gif");
    	//renderer.render("/Users/jason.burks/Data/Ridge/RIDGESampleFiles/KHUN_SDUS54_N0UHTX_201101051554","/tmp/KHUN_SDUS54_N0UHTX_201101051554_AGBR.png");
//    	renderer.render("/Users/jason.burks/Data/Radar/ASR/asrprods/P500fqkw","/tmp/asr_p500fqkw.png");
//    	renderer.render("/Users/jason.burks/Data/Radar/ASR/asrprods/P500fqwa","/tmp/asr_p500fqwa.png");
//    	renderer.render("/Users/jason.burks/Data/Radar/ASR/asrprods/P550eeri","/tmp/asr_p550eeri.png");
//    	File dir = new File("/Users/jason.burks/Data/Radar/april272011/");
//    	String[] files = dir.list(new FilenameFilter() {
//
//			public boolean accept(File dir, String name) {
//				// TODO Auto-generated method stub
//				if (name.startsWith("KHUN")) return true;
//				return false;
//			}
//    		
//    	});
//    	for (String fileTo : files) {
//    		System.out.println("Got "+fileTo);
//    		renderer.render("/Users/jason.burks/Data/Radar/april272011/"+fileTo,"/Users/jason.burks/Data/Radar/april272011/output/"+fileTo+".png");
//    	}
    }

}
