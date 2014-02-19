/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package renderertest;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.RadarProductData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import gov.noaa.nws.radardecoderlib.binaryutils.MappedByteBufferReader;
import gov.noaa.nws.radardecoderlib.builders.ProductConfigReader;
import gov.noaa.nws.radardecoderlib.colorcurves.FourBitReflectivity;
import gov.noaa.nws.radardecoderlib.gis.CoordinateHolder;
import gov.noaa.nws.radardecoderlib.gis.GeographicsCoordinateFactory;
import gov.noaa.nws.radardecoderlib.gis.PolarFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.CompositeDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.DecoderFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.FourBitRadialDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.RadarDecoder;
import gov.noaa.nws.radardecoderlib.renderers.BaseRenderer;
import gov.noaa.nws.radardecoderlib.renderers.CompositeSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RadarSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RadarSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RadialSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RendererFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.geotools.geometry.GeneralDirectPosition;
import org.junit.Test;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.operation.MathTransform;

/**
 *
 * @author Jason.Burks
 */
public class TestRenderersPolar {
    int width =800;
    int height = 800;
    
    String fileToProcess;
    String outputname;
    
    @Test
    public void testProduct() {
    	
    }
    
    public void setFilesToProcess(String fileToProcess, String outputname) {
    	this.fileToProcess = fileToProcess;
    	this.outputname = outputname;
    }
    public void render() {
         
        try {
        	MappedByteBufferReader reader = new MappedByteBufferReader(fileToProcess);
            BinaryUtilities.setupHeaderOffset(reader);
            RadarProductData radarData = ProductConfigReader.getInstance().getProductData(RadarDecoder.getMessageCode(reader));
            RadarDecoder decoder = DecoderFactory.getDecoder(radarData, reader);
            System.out.println("Message Code "+decoder.getMessageCode());
            System.out.println("Scan Time =="+decoder.getRadarScanTime());
            System.out.println("Generation Time =="+decoder.getRadarGenerationTime());
            ArrayList<RadarData> data = decoder.decode();
            System.out.println("Data ="+data.size());
            RadarSpatialRenderer renderer = RendererFactory.getRadarRenderer(radarData, width, height);
            renderer.setColor(new FourBitReflectivity().getColors());
            renderer.setRadarData(data);
            CoordinateHolder holder = PolarFactory.getTransformForPolar(width,height,decoder.getRadarLocation().getOrdinate(0),decoder.getRadarLocation().getOrdinate(1),130*1852.);
            System.out.println("Holder ==="+holder.getUpperLeft()+"   "+holder.getLowerRight());
//            MathTransform transform = holder.getTransform();
//            DirectPosition pos = new GeneralDirectPosition(0,0);
//            transform.transform(pos, pos);
//            System.out.println(pos);
            MathTransform transform = holder.getTransform();
            renderer.setTransform(holder.getTransform(), decoder.getRadarLocation(),decoder.getElevationAngle());
            BufferedImage image = renderer.render();
            ImageIO.write(image,"png",new File(outputname));
        } catch (Exception ex) {
            Logger.getLogger(TestRenderersPolar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       TestRenderersPolar polar =  new TestRenderersPolar();
       polar.setFilesToProcess("C:/temp/BRO/KBRO_SDUS24_N1RBRO_200807231801.txt","C:/temp/BRO/KBRO_SDUS24_N1RBRO_200807231801.png");
       polar.render();
//        new TestRenderers("C:/Jason/Data/Radar/Ridge/ComparisonTestData/Orig/FWS_20030406_0156_NCR","C:/temp/outputncr.png");
//        new TestRenderers("C:/Jason/Data/Radar/tempDatatar/tempData/tempData/tvs/20080508_1855","C:/temp/outputTVS.png");
//        new TestRenderers("C:/Jason/Data/Radar/Ridge/NVW/HTX_20060808_1402_NVW","C:/temp/outputNVW.png");
       
    }

}
