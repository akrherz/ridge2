package gov.noaa.nws.radardecoderlib.radardecoders;

import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.RadialData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import gov.noaa.nws.radardecoderlib.binaryutils.MappedByteBufferReader;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.DoubleThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.StringThreshold;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import ucar.unidata.io.bzip2.CBZip2InputStream;

/**
 *
 * @author jason.burks
 */
public class LevelIIDecoder {
    BinaryReader bindecode;
    SimpleDateFormat format = new SimpleDateFormat("MM/dd/y hh:mm:ss a z");
  
    DecimalFormat decformat = new DecimalFormat("##.#");
    
    double reflecGateSize;
    double reflecMaxRange;
    public double getReflecMaxRange() {
		return reflecMaxRange;
	}

	double dopplerGateSize;
    
    HashMap<Integer,ArrayList<RadarData>> reflec = new HashMap<Integer,ArrayList<RadarData>>();
    HashMap<Integer,ArrayList<RadarData>> vel = new HashMap<Integer,ArrayList<RadarData>>();
    HashMap<Integer,ArrayList<RadarData>> sw = new HashMap<Integer,ArrayList<RadarData>>();
    
    HashMap<Integer, Float> elevationAngles = new HashMap<Integer,Float>();
    
    Threshold[] reflecThresholds;
    Threshold[] velThresholds;
    Threshold[] swThresholds;
    float previousAngle = 0;

   
    
    /** Creates a new instance of LevelIIDecoder */
    public LevelIIDecoder(BinaryReader bindecode){
        this.bindecode = bindecode;
    }
    
    public Integer[] getElevationAngles() {
    	Set<Integer> keys = reflec.keySet();
    	Integer[] array = new Integer[keys.size()];
    	return keys.toArray(array);
    }
    
    public float getElevationAngle(Integer elevNumber) {
    	return elevationAngles.get(elevNumber);
    }
    
    public ArrayList<RadarData> getRadialData(int type, int elevationAngle) {
    	if (type == 0) {
    		return reflec.get(new Integer(elevationAngle));
    	} else if (type == 1) {
    		return vel.get(new Integer(elevationAngle));
    	} else if (type == 2) {
    		return sw.get(new Integer(elevationAngle));
    	}
    	return null;
    }
    
    public void beginDecoding() {
    		try {
				//System.out.println("Size ="+bindecode.getSize()+"  "+bindecode.getCurrentPosition());
			
    		processVolumeHeaderRecord();
    		//System.out.println("Size ="+bindecode.getSize()+"  "+bindecode.getCurrentPosition());
    		processMetadataRecords();
    		//System.out.println("Size ="+bindecode.getSize()+"  "+bindecode.getCurrentPosition());
    	int count = 0;
    		while((bindecode.getSize()-bindecode.getCurrentPosition())>0) {
    			processVariableCompressedRecords();
    			//System.out.println("Size ="+bindecode.getSize()+"  "+bindecode.getCurrentPosition());
//    		if (count > 0) break;
//    			++count;
    		}
    		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    } 
    
    private void processVolumeHeaderRecord() {
		try {
			// Get archive version number
			byte[] tapeBytes = new byte[8];

			bindecode.getBytes(tapeBytes);

			String tapeString = new String(tapeBytes);
			//System.out.println("Tape String =" + tapeString);
			bindecode.getByte();
			// Extract Extension number
			byte[] extensionBytes = new byte[3];
			bindecode.getBytes(extensionBytes);
			int extensionNumber = Integer.parseInt(new String(extensionBytes));
			//System.out.println("Extension number = "+extensionNumber);
			// Extract date time of tape
			long dateTime = ((long) bindecode.getInt()) * 86400000;
			long msPastMidnight = ((long) bindecode.getInt());
			Date date = new Date(dateTime + msPastMidnight);
			 System.out.println("Time ="+date);

			// Get Radar Id
			byte[] idBytes = new byte[4];
			bindecode.getBytes(idBytes);
			String id = new String(idBytes);
			 //System.out.println("Id ="+id);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	private void processMetadataRecords() {
		try {
			
			// Control Word
			int controlWord = Math.abs(bindecode.getInt());
		   // System.out.println("Size ="+controlWord);
			//bindecode.seek(bindecode.getCurrentPosition()+controlWord);

			byte[] array = new byte[controlWord];
			bindecode.getBytes(array);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(array);
			byte[] output = new byte[325888];
			bais.read();
			bais.read();
			CBZip2InputStream bzStream = new CBZip2InputStream(bais);
			bzStream.read(output);
			// System.out.println("Readable "+);
			ByteBuffer buffer = ByteBuffer.wrap(output);
			processMetadataMessages(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processVariableCompressedRecords() {
		try {
		int controlWord = Math.abs(bindecode.getInt());
		//System.out.println("Size ="+controlWord);

		byte[] array = new byte[controlWord];
		bindecode.getBytes(array);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		byte[] output = new byte[325888];
		bais.read();
		bais.read();
		CBZip2InputStream bzStream = new CBZip2InputStream(bais);
		bzStream.read(output);
		ByteBuffer buffer = ByteBuffer.wrap(output);
		processOtherMessages(buffer);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    private void processMetadataMessages(ByteBuffer buffer) {
    	//System.out.println("Processing Metadata messages "+buffer.limit());

		buffer.position(buffer.position()+12);
		for (int i=0; i<134; ++i) {
			int size = (int)buffer.getShort();
			int channelid = (int)buffer.get();
			int messagetype = (int)buffer.get();
			int idSequence = (int)buffer.getShort();
			int juliandate = (int)buffer.getShort();
			int millis = (int)buffer.getInt();
			int numberOfSegments = (int)buffer.getShort();
			int messageSegmentNumber = (int)buffer.getShort();
			//if (messagetype ==0) break;
		//	System.out.println("Size ="+size+" channelid ="+channelid+"idseq="+idSequence+" messagetype = "+messagetype+" segments ="+numberOfSegments+" segment number="+messageSegmentNumber);
			if (size >0) {
				buffer.position((buffer.position()+(1208*2)));
			} else {
				break;
			}
		}
    }
    private void processOtherMessages(ByteBuffer buffer) {
    	//System.out.println("Processing Other messages "+buffer.limit());
    	buffer.position(buffer.position()+12);
    	previousAngle = Float.MAX_VALUE;
		for (int i=0; i<120; ++i) {
			
			int size = (int)buffer.getShort();
			int channelid = (int)buffer.get();
			int messagetype = (int)buffer.get();
			int idSequence = (int)buffer.getShort();
			int juliandate = (int)buffer.getShort();
			int millis = (int)buffer.getInt();
			int numberOfSegments = (int)buffer.getShort();
			int messageSegmentNumber = (int)buffer.getShort();
			//if (messagetype ==0) break;
		//	System.out.println("Size ="+size+" channelid ="+channelid+"idseq="+idSequence+" messagetype = "+messagetype+" segments ="+numberOfSegments+" segment number="+messageSegmentNumber);
//			if (messagetype == 31) {
//				System.out.println("Processing 31");
//			}
//			if (messagetype == 1) {
//				System.out.println("Processing 1");
//			}
			if (size >0) {
				//buffer.position((buffer.position()+(size*2)));
				byte[] array = new byte[1208*2];
				buffer.get(array);
				if (messagetype == 31) {
						processMessageType31(array);
				} else if (messagetype == 1) {
					processMessageType1(array);
				}
			} else {
				//break;
			}
//			System.out.println("Size ="+size+" channelid ="+channelid+" messagetype = "+messagetype);
//			if (size >0) {
//				
//				if (buffer.position()+(size*2)-16 < buffer.capacity()) {
//					byte[] array = new byte[size*2-16];
//					buffer.get(array);
//					if (messagetype == 31) {
//						processMessageType31(array);
//					}
//				} else {
//					break;
//				}
//			}
		}

    }
    
   
    
    private void processMessageType1(byte[] array) {
    	ByteBuffer buf = ByteBuffer.wrap(array);
    	//System.out.println("^^^^^^^^^^^^^^^^^^^^^Processing Message type 1");
    	long timePastMidnight = (long)buf.getInt();
    	long julian = (long)buf.getShort()*86400*1000;
    	Date validTime = new Date(timePastMidnight+julian);
    //	System.out.println("Time ="+validTime);
    	double unambigousRange = buf.getShort()*.1;
    //	System.out.println("unambigous range = "+unambigousRange);
    	double azimuthAngle = (buf.getShort()/8.)*(180./4096);
    //	System.out.println("AzimuthAngle ="+azimuthAngle);
    	short azimuthNumber = buf.getShort();
    //	System.out.println("AzimuthNumber ="+azimuthNumber);
    	buf.getShort();
    	Float elevationAngle = new Float(Math.floor(((buf.getShort()/8.)*(180./4096.))*10)/10);
    	
    	Integer elevationNumber = new Integer(buf.getShort());
    //	System.out.println("Elevation Angle ="+elevationAngle);
    	if (elevationAngles.get(elevationNumber) == null) {
   		 elevationAngles.put(elevationNumber, elevationAngle);
   	 }
    	int surveillenceRange = buf.getShort();
    	int dopplerRange = buf.getShort();
    	int surveRangeSampleInterval = buf.getShort();
    	int dopplerRangeSampleInterval = buf.getShort();
    	int numberSurveyBins = buf.getShort();
    	int numberDopplerBins = buf.getShort();
    	int cutSectorNumber = buf.getShort();
    	float calibrationConst = buf.getFloat();
    	reflecGateSize =surveRangeSampleInterval*.001f;
    	int reflecPointer  = buf.getShort();
    	int velPointer  = buf.getShort();
    	int swPointer  = buf.getShort();
    	
    	//System.out.println("Reflect Pointer ="+reflecPointer);
    	//System.out.println("Vel Pointer ="+velPointer);
    	///System.out.println("SW Pointer ="+swPointer);
    	buf.position(reflecPointer);
    	int[] output = new int[numberSurveyBins];
    	for (int i=0; i< numberSurveyBins; ++i) {
    		int value = (int)buf.get();
    		if (value >0) {
        		output[i] = value;
        	} else {
        		output[i] = 254+value;
        	}
    	}
    	float deltaAngle;
    	if (previousAngle == Float.MAX_VALUE) {
    		deltaAngle = 1.f;
    	} else {
    		deltaAngle = (float) (azimuthAngle - previousAngle);
    	}
    //	System.out.println("Delta Angle ="+deltaAngle);
    	RadialData reflectData = new RadialData(output,azimuthAngle,deltaAngle);
   	 if (reflec.get(elevationNumber) == null ){
   		 reflec.put(elevationNumber, new ArrayList<RadarData>());
   	 }
   	 reflec.get(elevationNumber).add(reflectData);
   	 
   	reflecThresholds = new Threshold[255];
   	reflecThresholds[0] = new StringThreshold("RF");
	reflecThresholds[254] = new StringThreshold("ND");
	for (int i=1; i<254; ++i) {
		reflecThresholds[i] = new DoubleThreshold((((double)i-2.)/2.)- 32.);
		//System.out.println("Reflect gate = "+i+" "+reflecThresholds[i]);
	}
    	buf.position(velPointer);
    	for (int i=0; i< numberDopplerBins; ++i) {
    		int value = (int)buf.get();
    		
    	}
    	buf.position(swPointer);
    	for (int i=0; i< numberDopplerBins; ++i) {
    		int value = (int)buf.get();
    		
    	}
    	previousAngle = (float) azimuthAngle;
	}

	public void processMessageType31(byte[] array) {
    	ByteBuffer buf = ByteBuffer.wrap(array);
    	//System.out.println("^^^^^^^^^^^^^^^^^^^^^Processing Message type 31");
    	byte[] stringArray = new byte[4];
    	buf.get(stringArray);
    	String radarId = new String(stringArray);
    	System.out.println("Site ="+radarId);
    	
    	long timePastMidnight = (long)buf.getInt();
    	long julian = (long)buf.getShort()*86400*1000;
    	Date validTime = new Date(timePastMidnight+julian);
    //	System.out.println("Time ="+validTime);
    	
    	int azimuthNumber = buf.getShort()/2;
    	float azimuthAngle = buf.getFloat();
    	
    	///System.out.println("Float =="+azimuthAngle);
    	
    	int compressionIndicator = buf.get();
    ///	System.out.println("Compression indicator "+compressionIndicator);
    	 buf.get(); //spare
    	 
    	 int radialLength = buf.getShort();
    	 int azimuthResSpacing = buf.get();
    	// System.out.println("Azimuth Res Spacing "+azimuthResSpacing);
    	 int radialStatus = buf.get();
    	Integer elevationNumber = new Integer(buf.get());
    	 //System.out.println("Elevation Number"+elevationNumber);
    	 int cutSectorNumber = buf.get();
    	 
    	 float elevationAngle = Math.round(buf.getFloat());
    	 
    	 if (elevationAngles.get(elevationNumber) == null) {
    		 elevationAngles.put(elevationNumber, elevationAngle);
    	 }
    	// System.out.println("Elevation Angle "+elevationAngle);
    	 
    	 int radialSpotBlanking = buf.get();
    	 
    	 float azimuthIndexMode = buf.get()*.01f;
    	 //System.out.println("AzimuthINdexMode ="+azimuthIndexMode);
    	 int dataBlockCount = buf.getShort();
    	 
    	 //System.out.println("Data block Count "+dataBlockCount);
    	 
    	 int dbPointercdct = buf.getInt();
    	 int dbedct = buf.getInt();
    	 int dbrdct = buf.getInt();
    	 int dbMR = buf.getInt();
    	 int dbMV = buf.getInt();
    	 int dbMSW = buf.getInt();
    	 
    	 
    	 double deltaAngle;
    	 if (azimuthResSpacing == 0 ){
    		 deltaAngle = 0.5;
    	 } else {
    		 deltaAngle = 1.0;
    	 }
    	 //System.out.println("dbMR "+dbMR);
    	 int[] reflecGates = processDataBlock(0,dbMR,buf);
    	 RadialData reflectData = new RadialData(reflecGates,azimuthAngle,deltaAngle);
    	 if (reflec.get(elevationNumber) == null ){
    		 reflec.put(elevationNumber, new ArrayList<RadarData>());
    	 }
    	 reflec.get(elevationNumber).add(reflectData);
    	 
//    	 int[] velGates = processDataBlock(1,dbMV,buf);
//    	 RadialData velData = new RadialData(velGates,azimuthAngle,deltaAngle);
//    	 if (vel.get(evel) == null ){
//    		 vel.put(evel, new ArrayList<RadialData>());
//    	 }
//    	 vel.get(evel).add(velData);
//    	 int[] swGates = processDataBlock(2,dbMSW,buf);
//    	 RadialData swData = new RadialData(swGates,azimuthAngle,deltaAngle);
//    	 if (sw.get(evel) == null ){
//    		 sw.put(evel, new ArrayList<RadialData>());
//    	 }
//    	 sw.get(evel).add(swData);
    }
    
    public double getReflecGateSize() {
    	//System.out.println("Reflec "+reflecGateSize);
		return reflecGateSize;
	}

	public Threshold[] getReflecThresholds() {
		return reflecThresholds;
	}

	public Threshold[] getVelThresholds() {
		return velThresholds;
	}

	public Threshold[] getSwThresholds() {
		return swThresholds;
	}

	public int[] processDataBlock(int type, int position, ByteBuffer buf) {
		///System.out.println("-------------Processing data block---------------------");
    	buf.position(position);
    	//System.out.println("Should be D "+new String(new byte[]{buf.get()}));
    	byte[] name = new byte[3];
    	buf.get(name);
    	//System.out.println("Should be  "+new String(name));
    	int shouldBeZero = buf.getInt();
    	int numberOfGates = buf.getShort();
    	reflecMaxRange = 460.;
    	//System.out.println("resolution "+(460./(double)numberOfGates));
    	float dataMomentRange = buf.getShort()*.001f;
    	
    	//System.out.println("Data Moment Range "+dataMomentRange);
    	
    	float dataMomentRangeSampleInt = buf.getShort()*.001f;
    	//System.out.println("Data Moment Range "+dataMomentRangeSampleInt);
    	if (type == 0) {
    		reflecGateSize = dataMomentRangeSampleInt;
    	} else if (type == 1) {
    		dopplerGateSize = dataMomentRangeSampleInt;
    	} else if (type == 2) {
    		dopplerGateSize = dataMomentRangeSampleInt;
    	}
    	
    	float tover = buf.getShort()*.1f;
    	
        buf.getShort();
        
        int controlFlag = buf.get();
       // System.out.println("Control Flag "+controlFlag);
        
        int dataWordSize = buf.get();
        //System.out.println("Data word size ="+dataWordSize);
        float scale = buf.getFloat();
        
        float offset = buf.getFloat();
        ///System.out.println("Scale ="+scale+" offset ="+offset);
		if (type == 0) {
			if (reflecThresholds == null) {
				reflecThresholds = new Threshold[255];
				for (int i=0; i<255; ++i) {
					reflecThresholds[i] = new DoubleThreshold((i-offset)/scale);
				}
			}
		} else if (type == 1) {
			if (velThresholds == null) {
				velThresholds = new Threshold[255];
				for (int i=0; i<255; ++i) {
					velThresholds[i] = new DoubleThreshold((i-offset)/scale);
				}
			}
		} else if (type == 2) {
			if (swThresholds == null) {
				swThresholds = new Threshold[255];
				for (int i=0; i<255; ++i) {
					swThresholds[i] = new DoubleThreshold((i-offset)/scale);
				}
			}
		}
        
        int output[] = new int[numberOfGates];
        
        for (int i=0; i< numberOfGates; ++i) {
        	
        	int value = buf.get();
        	if (value >0) {
        		output[i] = value;
        	} else {
        		output[i] = 254+value;
        	}
        		//System.out.println("Got value "+value);
        		//double valueOutOf = (value-offset)/scale;
        		//System.out.println("Value real ="+valueOutOf);
        
        }
        //System.out.println("-------------Done Processing data block---------------------");
        return output;
    }
}
