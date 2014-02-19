package gov.noaa.nws.radardecoderlib.radardecoders;

import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import  gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jason.burks
 */
public class CopyOfLevelIIDecoder {
    BinaryReader bindecode;
    final int headerlength = 24;
    final int cmtheader = 12;
    final int blocksize = 2432;
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/y hh:mm:ss a z");
    double previousangle=0;
    double azimuthangle;
    double deltaAngle;
    double elevationangle;
    Date genDate;
    int vcp;
    String id;
    DecimalFormat decformat = new DecimalFormat("##.#");
    
    /** Creates a new instance of LevelIIDecoder */
    public CopyOfLevelIIDecoder(BinaryReader bindecode){
        this.bindecode = bindecode;
    }
    
    public void beginDecoding() {
        
        try {
            long numRecords = bindecode.getSize()/(headerlength+blocksize+cmtheader);
             bindecode.seek(20);
             byte[] bytes = new byte[4];
              bindecode.getBytes(bytes);
             
             id =  new String(bytes);
             System.out.println("ID ="+id);
             
            //need to get the radar id or the call sign.
//            System.out.println("Size = "+numRecords);
//            System.out.println("Decoding");
//            System.out.println("Getting filename ");
            for (int i=0; i<numRecords; ++i) {
              System.out.println("Seeking to "+(i*blocksize+headerlength+cmtheader));
               bindecode.seek(i*blocksize+headerlength+cmtheader);
               int lengthofthisblock = bindecode.getShort();
                int channelid = bindecode.getByteAsInt();
                int messagetype = bindecode.getByteAsInt();
              //  System.out.println("Message type="+messagetype);
                if (messagetype == 1) {
                    int idseq = bindecode.getShort();
                    double dateTime = ((double)bindecode.getShort())*86400000.;
                    System.out.println(new Date((long)dateTime));
                    long timeinmillisgen = (long)dateTime+(long)bindecode.getInt();
                    Date dategen = new Date(timeinmillisgen);
                   System.out.println("Date ="+dategen);
                    int numberOfMessageSegments = bindecode.getShort();
                    int messageSegmentNumber = bindecode.getShort();
                    int collectionTime = bindecode.getInt();
                    double modifiedJulianDate = ((double)bindecode.getShort())*86400000;
                    genDate = new Date((long)modifiedJulianDate+collectionTime);
                    System.out.println("Collection Date = "+format.format(genDate));
                    bindecode.getShort(); //Unambigous range
                                    azimuthangle = (bindecode.getShort() & 0xffff)/8.*(180./4096);
                                    System.out.println("Azimuth angl===="+azimuthangle);
                    deltaAngle = (azimuthangle-previousangle);
                    previousangle = azimuthangle;
                    int radialNumber = bindecode.getShort();
                    int radialstatus = bindecode.getShort();
                     int value = bindecode.getShort() & 0xffff;
                    //System.out.println("First value ="+value);
                    //System.out.println("Second value ="+);
                               elevationangle = Math.floor(((value/8.)*(180./4096.))*10)/10;
//                    if (elevationangle == 1.3) {
//                        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//                    }
                    
                    bindecode.getShort(); //RDA Elevation Number
                    bindecode.getShort(); //range to first gate of reflectivity
                    bindecode.getShort(); //range to first gate of doppler data
                    int reflecGateSize = bindecode.getShort();
                    int dopplegateSize = bindecode.getShort();
//                    System.out.println("Reflectivity gate size = "+(reflecGateSize/1852.002756));
//                    System.out.println("Doppler gate size = "+dopplegateSize/1852.002756);
                                int numberOfReflectivityGates = bindecode.getShort() & 0xffff;
                                System.out.println("Number of Reflect gates "+numberOfReflectivityGates);
                    int numberOfDopplerGates = bindecode.getShort()& 0xffff;
                    System.out.println("Number of Doppler gates "+numberOfDopplerGates);
                    bindecode.getShort(); //Sector number within cut
                    bindecode.getInt(); //gain calibration really is a real
                    int reflecPointer = bindecode.getShort();
                    int velPointer = bindecode.getShort();
                    int spectrumPointer = bindecode.getShort();
                    int dopplerRes = bindecode.getShort();
                    vcp = bindecode.getShort();
                    if (reflecPointer > 0) {
//                        System.out.println("Refelect");
                        processRelfectivityGates(i*blocksize+headerlength+cmtheader+reflecPointer,numberOfReflectivityGates);
                    } else {
                      //  System.out.println("Not found");
                    }
                    if (velPointer > 0) {
//                        System.out.println("Velocity");
                        processVelocityGates(i*blocksize+headerlength+cmtheader+velPointer,numberOfDopplerGates);
                    } else {
                       // System.out.println("Not found");
                    }
                    if (spectrumPointer > 0) {
//                        System.out.println("Spectrum");
                        processSpectrumGates(i*blocksize+headerlength+cmtheader+velPointer,numberOfDopplerGates);
                    } else {
                       // System.out.println("Not found");
                    }
                }
                
//            System.out.println("GEn Date = "+format.format(dategen));
            }
               
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
   private void processRelfectivityGates(int start,int numbergates) {
       
       //TODO need to account for value of 0 or 1 for range folding, etc
        try {
            bindecode.seek(start);
            double[] data = new double[numbergates];
            for (int i=0; i<numbergates; ++i) {
                data[i] = (((double)bindecode.getByteAsInt()-2.)/2.)- 32.;
               //System.out.println("Processing reflect gate");
            }
         //  fireRenderEvent(new LevelIIRadialDataLoadEvent("Level II radial Event",data,azimuthangle,deltaAngle,elevationangle,vcp,0,genDate));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
   }
   private void processVelocityGates(int start,int numbergates) {
       //TODO need to account for value of 0 or 1 for range folding, etc and diff velocity calculation based on resolution
        try {
            bindecode.seek(start);
            double[] data = new double[numbergates];
            for (int i=0; i<numbergates; ++i) {
                data[i] = (((double)bindecode.getByteAsInt()-2.)/2.)- 63.5;
               
            }
          //  fireRenderEvent(new LevelIIRadialDataLoadEvent("Level II radial Event",data,azimuthangle,deltaAngle,elevationangle,vcp,1,genDate));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
   }
   private void processSpectrumGates(int start,int numbergates) {
	   System.out.println("Number of gates "+numbergates);
       //TODO need to account for value of 0 or 1 for range folding, etc and diff velocity calculation based on resolution
        try {
            bindecode.seek(start);
            double[] data = new double[numbergates];
            for (int i=0; i<numbergates; ++i) {
                data[i] = (((double)bindecode.getByteAsInt()-2.)/2.)- 63.5;
               
            }
         //   fireRenderEvent(new LevelIIRadialDataLoadEvent("Level II radial Event",data,azimuthangle,deltaAngle,elevationangle,vcp,2,genDate));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
   }
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//       
//            //BasicBinaryReader bindecode = new BasicBinaryReader("C:/Jason/Data/Radar/LevelII/20060123150406.raw");
//            BasicBinaryReader bindecode = new BasicBinaryReader("C:/Jason/Data/Radar/LevelII/ARMOR/L2_NA_000_125_20060823203057");
//            LevelIIDecoder decoder = new LevelIIDecoder(bindecode);
//            LevelIIDataCatcher catcher = new LevelIIDataCatcher();
//            decoder.addDataLoadEventListener(catcher);
//            decoder.beginDecoding();
//        
//    }
    
  

   
    
}
