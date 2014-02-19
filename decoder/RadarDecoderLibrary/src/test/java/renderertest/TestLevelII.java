/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package renderertest;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardata.RadialData;
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
import gov.noaa.nws.radardecoderlib.radardecoders.LevelIIDecoder;
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

import org.geotools.geometry.GeneralDirectPosition;
import org.junit.Test;

/**
 *
 * @author Jason.Burks
 */
public class TestLevelII {
    String fileToProcess;
    
    
    public TestLevelII() {
    	
    }
    
    @Test
    public void testProduct() {
 
    	
    }
    
    public void process(String fileToProcess) {
    	System.out.println("Going to be processing "+fileToProcess);
    	this.fileToProcess = fileToProcess;
           MappedByteBufferReader reader;
		try {
			reader = new MappedByteBufferReader(fileToProcess);
			 LevelIIDecoder decoder = new LevelIIDecoder(reader);
			 decoder.beginDecoding();
			 Integer[]  elevationAngles  = decoder.getElevationAngles();
			 for (Integer elevation: elevationAngles) {
				 System.out.println("Working on "+elevation+"  "+decoder.getElevationAngle(elevation));
				// if (elevation.equals(new Integer(2))) {
				 Threshold[] thresholds = decoder.getReflecThresholds();
				 ArrayList<RadarData> currData = decoder.getRadialData(0, elevation);
				 System.out.println("CurrData "+currData);
				 RadarSpatialRenderer renderer = new RadialSpatialRenderer(3000,3000,decoder.getReflecGateSize()*1000.,decoder.getReflecMaxRange()*1000.);
				 ColorCurveManager colmanager = new ColorCurveManager("/colorcurves/LevelIIReflectivityColorCurveManager.xml");
	                
	                Color[] colors = colmanager.getColors(thresholds);
	                for (Color color:colors) {
	                	if (color != null) {
	                	//System.out.println("Color "+color.getAlpha());
	                	} else {
	                		//System.out.println("Null");
	                	}
	                }
	                renderer.setColor(colmanager.getColors(thresholds));
	               
	                renderer.setRadarData(currData);
	                CoordinateHolder holder = GeographicsCoordinateFactory.getTransformForGeo(3000, 3000, new GeneralDirectPosition(87.0,34.0), 230. * 1852.);
	                renderer.setTransform(holder.getTransform(), new GeneralDirectPosition(87.0,34.0), decoder.getElevationAngle(elevation));
	                //CreateWorldFileFromRadar.createWorldFileFromRadar(CreateWorldFileFromRadar.createWorldFilename(outputname), holder.getUpperLeft(), holder.getLowerRight(), width, height);
	                BufferedImage image = renderer.render();
	                ImageIO.write(image, "png", new File("/tmp/test_"+elevation+".png"));
				// }
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
        

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    	TestLevelII decoder = new TestLevelII();
    	decoder.process("/Users/jason.burks/Data/Radar/LevelII/KHTX_20060601_2359");
    	
    	
        
    }

}
