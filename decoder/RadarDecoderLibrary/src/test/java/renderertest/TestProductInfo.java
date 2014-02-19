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
public class TestProductInfo {
    int width = 800;
    int height = 800;
    String fileToProcess;
    
    
    public TestProductInfo() {
    	
    }
    
    @Test
    public void testProduct() {
 
    	
    }
    
    public void render(String fileToProcess) {
    	this.fileToProcess = fileToProcess;
            
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
                System.out.println("Size ===="+reader.getSize());
                reader.seek(0);
                System.out.println("Message code =="+RadarDecoder.getMessageCode(reader)+"  reader "+reader);
                reader.seek(0);
                RadarProductData radarData = ProductConfigReader.getInstance().getProductData(RadarDecoder.getMessageCode(reader));
                RadarDecoder decoder = DecoderFactory.getDecoder(radarData, reader);
                System.out.println("Message Code " + decoder.getMessageCode());
                System.out.println("Scan Time ==" + decoder.getRadarScanTime());
                System.out.println("Generation Time ==" + decoder.getRadarGenerationTime());
                ArrayList<RadarData> data = decoder.decode();
                RadarSpatialRenderer renderer = RendererFactory.getRadarRenderer(radarData, width, height);
                ColorCurveManager colmanager = new ColorCurveManager("/colorcurves/" + radarData.getDefaultColorCurveName() + ".xml");
                Threshold[] thresholds = decoder.getThresholds();
                for (Threshold threshold:thresholds) {
                	System.out.println("Threshold "+threshold);
                }
                Color[] colors = colmanager.getColors(thresholds);
                int i=0;
                for (Color color:colors) {
                	if (color != null) {
                		System.out.println("Bin="+i+" Color rgb="+color.toString()+" alpha="+color.getAlpha()+" thresholds="+thresholds[i]);
                	} else {
                		System.out.println("Bin="+i+" Null");
                	}
                	++i;
                }
            } catch (Exception ex) {
                Logger.getLogger(TestProductInfo.class.getName()).log(Level.SEVERE, null, ex);
            }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TestProductInfo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(TestProductInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    	TestProductInfo renderer = new TestProductInfo();
    	
    	renderer.render("/Users/jason.burks/Downloads/sn(2).last");
       

       
    }

}
