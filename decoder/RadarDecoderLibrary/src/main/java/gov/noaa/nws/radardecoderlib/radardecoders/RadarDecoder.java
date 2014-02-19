/*
 * RadarDecoder.java
 *
 * Created on October 6, 2005, 10:41 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gov.noaa.nws.radardecoderlib.radardecoders;
import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.DoubleThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.StringThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import java.io.IOException;
import java.util.*;
import org.geotools.geometry.GeneralDirectPosition;
import org.opengis.geometry.DirectPosition;
/**
 *
 * @author jburks
 */
public abstract class RadarDecoder  implements DecoderInterface{
    //TODO need to make threadded
    /** Creates a new instance of RadarDecoder */
    BinaryReader bindecode;
    int messageCode;
    long generationDate;
    long offsetToSymbology;
    long offsetToGraphic;
    long offsetToTabular;
    int dataLayerBytes; 
    int radarID;
    int vcp;
    double radarHeight;
    int  numThresholds=16;
    DirectPosition radarLocation;
    double elevationAngle =0;
    Date scanTime;
    Date generationTime;
    ArrayList<RadarData> data = new ArrayList<RadarData>();
    
    
    public RadarDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        this.bindecode = bindecode;

        //Get the messageCode
        bindecode.seek(0);
        messageCode = bindecode.getShort();
        //Get site ID for the radar
        bindecode.seek(12);
        radarID = bindecode.getShort();
        //Get radar Location
        bindecode.seek(20);
        double latitude = (double)(bindecode.getInt())*.001;
        double longitude = (double)(bindecode.getInt())*.001;
        radarLocation = new GeneralDirectPosition(longitude,latitude);
        //Get Height of Radar
        bindecode.seek(28);
        radarHeight = bindecode.getShort();
        //Get radar VCP
        bindecode.seek(34);
        vcp = bindecode.getShort();
        //Scan time
        bindecode.seek(40);
        int oneScan = bindecode.getShort();
        int twoScan = bindecode.getInt();
        scanTime  = new Date((long)((twoScan+(oneScan-1)*86400.)*1000.));
        //Get generation time
        bindecode.seek(46);
        int one = bindecode.getShort();
        int two = bindecode.getInt();
         generationTime = new Date((long)((two+(one-1)*86400.)*1000.));

        //Get radar elevation Angle
        bindecode.seek(58);
        elevationAngle = bindecode.getShort()*.1;
        //Get offset to symbology, graphic, and tabular
        bindecode.seek(108);
        offsetToSymbology = bindecode.getInt();
        offsetToGraphic = bindecode.getInt();
        offsetToTabular = bindecode.getInt();
    }

    public ArrayList<RadarData> decode() throws IOException {
        process();
        return(data);

    }

    protected void process() throws IOException {

    }

    public int getMessageCode() {
        return messageCode;
    }
     public static int getMessageCode(BinaryReader bin) throws Exception {
        bin.seek(0);
        return(bin.getShort());
    }
     
     public int getSiteID() {
        return(radarID);
    }
     
      public int getVCP() {
        return(vcp);
    }
      public double getElevationAngle() {
         return(elevationAngle);
      }
     public DirectPosition getRadarLocation() {
        return(radarLocation);
     }
     public  double getRadarHeight() {
        return(radarHeight);
    }
    public int getRadarID() {
        return(radarID);
    }

    public Date getRadarScanTime() {
       return(scanTime);
    }
    public Date getRadarGenerationTime() {
        return(generationTime);
    }
    
    
     public Threshold[] getThresholds() throws IOException {
        //Need to take care of strange getShort issue. Does not return negative values over -1
        Threshold[] thresholds = new Threshold[numThresholds];
            bindecode.seek(60);
            for (int i=0; i<= 15; ++i){
                thresholds[i] = getValue();
            }
        return(thresholds);
    }
    
    protected Threshold getValue() throws IOException {
        Threshold threshold = null;
        BitSet set = BinaryUtilities.fromByteToBitSet(bindecode.getByte());
        BitSet setnew = BinaryUtilities.fromByteToBitSet(bindecode.getByte());
        double value =0;
            for (int j=0; j<setnew.length(); ++j) {
                if (setnew.get(j)) {
                    value = value +Math.pow(2,j);
                }
            } 
        if (!set.get(7)) {
            int optional = -1;
            if (set.get(0)) {
                value = value*-1;
            }
            if (set.get(2)) {
                optional = DoubleThreshold.LESS_THAN;
            }
            if (set.get(3)) {
                optional = DoubleThreshold.GREATER_THAN;
            }
            if (set.get(6)) {
             value = value/100.;
            }
            if (set.get(5)) {
                value = value/20.;
            }
            if (set.get(4)) {
                value = value/10.;
            }
            if (optional == -1) {
                threshold = new DoubleThreshold(value);
            } else {
                threshold = new DoubleThreshold(value,optional);
            }
        } else {
        	int valueInt = (int)value;
        	switch (valueInt) {
        	case 1:
        		threshold = new StringThreshold("TH");
        		break;
        	case 2:
        		threshold = new StringThreshold("ND");
        		break;
        	case 3:
        		threshold = new StringThreshold("RF");
        		break;
        	case 4:
        		threshold = new StringThreshold("BI");
        		break;
        	case 5:
        		threshold = new StringThreshold("GC");
        		break;
        	case 6:
        		threshold = new StringThreshold("IC");
        		break;
        	case 7:
        		threshold = new StringThreshold("GR");
        		break;
        	case 8:
        		threshold = new StringThreshold("WS");
        		break;
        	case 9:
        		threshold = new StringThreshold("DS");
        		break;
        	case 10:
        		threshold = new StringThreshold("RA");
        		break;
        	case 11:
        		threshold = new StringThreshold("HR");
        		break;
        	case 12:
        		threshold = new StringThreshold("BD");
        		break;
        	case 13:
        		threshold = new StringThreshold("HA");
        		break;
        	case 14:
        		threshold = new StringThreshold("UK");
        		break;
        	default:
        		threshold = new StringThreshold("????");
        		break;
        	}

        }
        return(threshold);
    }

   
}
