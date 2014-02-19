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
import gov.noaa.nws.radardata.MesocycloneData;
import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.StormTrackData;
import gov.noaa.nws.radardata.TextData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author jburks
 */
public class MesocycloneDecoder extends RadarDecoder {
	 int lengthOfLayer;
	    boolean future;
	    String currentStorm="";
	    String watchStorm = "C6";
	    MesocycloneData currMeso;
    public MesocycloneDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode,numLevels);
    }

    
    
    protected void process() throws IOException {
        //    System.out.println("***************************Processing Symbology Block****************************************");
            if (offsetToSymbology > 0 ){
                processSymbologyBlock(offsetToSymbology);
            } else {
                throw new IOException("Bad offset to symbology");
            }
    }
    

    
    private void processGraphicBlock(long numberToGraphic) throws IOException{
            bindecode.seek((int)(numberToGraphic*2));
//        System.out.println("block divider "+bindecode.read(2,true));
//        System.out.println("block ID  "+bindecode.read(2));
            int lengthOfBlock = bindecode.getInt();
//        System.out.println("Length of Block  "+lengthOfBlock);
//        System.out.println("Number of Pages  "+bindecode.read(2));
//        System.out.println("Page Number  "+bindecode.read(2));
            int lengthOfPages = bindecode.getShort();
//        System.out.println("Length of Pages  "+lengthOfPages);
//        System.out.println("Need to start decoding stuff");
            
          //  processPacketCode8(lengthOfBlock);
       
    }
    
    private void processTabularBlock(long numberToTabular) throws IOException {
            bindecode.seek((int)(offsetToTabular*2));
//        System.out.println("block divider "+bindecode.read(2,true));
//        System.out.println("block ID  "+bindecode.read(2));
//        System.out.println("Length of Block  "+bindecode.read(4));
       
    }
    
    private void processSymbologyBlock(long numberToSymbology) throws IOException{
            bindecode.seek((int)(offsetToSymbology*2)+4);
            int lengthOfBlock = bindecode.getInt();
            bindecode.skip(4);
           // System.out.println("Length data layer "+bindecode.getInt());
            lengthOfLayer =bindecode.getInt();
           
            processMesocycloneSymbology();
        
    }
    
    private void processMesocycloneSymbology() throws IOException{
            
            while (lengthOfLayer > 0) {
                int packetCode = bindecode.getShort();
                int lenData = bindecode.getShort();
                	processPacket(packetCode,lenData);
                	lengthOfLayer -= (4+lenData);
                }
            }
            
        
    
//    private void processPacketCode3() throws IOException{
//            double iposition = bindecode.getShort()*1000./(4.0);
//            double jposition = bindecode.getShort()*1000./(4.0);
//            double radius = bindecode.getShort()*1000./(4.0);
//            data.add(new MesocycloneData(iposition,jposition,radius));
//        
//    }
//    private void processPacketCode11() throws IOException{
//            double iposition = bindecode.getShort()*1000./(4.0);
//            double jposition = bindecode.getShort()*1000./(4.0);
//            double radius = bindecode.getShort()*1000./(4.0);
//           data.add(new MesocycloneData(iposition,jposition,radius));
//       
//    }
//    public void processPacketCode15() throws IOException {
//            double iposition = bindecode.getShort()*1000./(4.0);
//            double jposition = bindecode.getShort()*1000./(4.0);
//            char[] chars = new char[2];
//            chars[0]=(char)bindecode.getByteAsInt();
//            chars[1]=(char)bindecode.getByteAsInt();
//            data.add(new TextData(iposition,jposition,new String(chars)));
//       
//    }
//    public void processPacketCode20() throws IOException {
//        double iposition = bindecode.getShort()*1000./(4.0);
//        double jposition = bindecode.getShort()*1000./(4.0);
//        int featureType = bindecode.getShort();
//        int featureatt = bindecode.getShort();
//        System.out.println(iposition+", "+jposition+" "+"Feature Type ="+featureType+" Feature Attribute ="+featureatt);
//        
//        data.add(new TextData(iposition,jposition,new String("Meso")));
//   
//}
//    
//    public void processPacketCode23() throws IOException {
//        double iposition = bindecode.getShort()*1000./(4.0);
//        double jposition = bindecode.getShort()*1000./(4.0);
//        int featureType = bindecode.getShort();
//        int featureatt = bindecode.getShort();
//        System.out.println(iposition+", "+jposition+" "+"Feature Type ="+featureType+" Feature Attribute ="+featureatt);
//        data.add(new TextData(iposition,jposition,new String("Meso")));
//   
//}
//    public void processPacketCode24() throws IOException {
//        double iposition = bindecode.getShort()*1000./(4.0);
//        double jposition = bindecode.getShort()*1000./(4.0);
//        int featureType = bindecode.getShort();
//        int featureatt = bindecode.getShort();
//        System.out.println(iposition+", "+jposition+" "+"Feature Type ="+featureType+" Feature Attribute ="+featureatt);
//        
//        data.add(new TextData(iposition,jposition,new String("Meso")));
//   
//}
    private void processPacketCode8(int lengthOfData) throws IOException{
    		int valueOfText = bindecode.getShort();
    		lengthOfData -= 2;
    		int ipoint = bindecode.getShort();
    		int jpoint = bindecode.getShort();
    		lengthOfData -= 4;
    		String text = new String();
    		while (lengthOfData >0) {
    			text += bindecode.getChar();
    			lengthOfData -= 1;
    		}
    		currMeso.setName(text);
       
    } 
    
    private void processPacketCode2(int lengthOfData) throws IOException {
        
            long positionstart = bindecode.getCurrentPosition();
            double iposition = bindecode.getShort()*1000/(4.0);
            double jposition = bindecode.getShort()*1000/(4.0);
            lengthOfData -= 4;
            char[] chars = new char[1];
            while (lengthOfData > 0 ) {
                chars[0]=(char)bindecode.getByteAsInt();
                lengthOfData -= 1;
            }
           // data.add(new MesocycloneData(iposition, jposition, .5));
        
    }
    
    private void processPacketCode6(int lengthOfData) throws IOException {
            double ipositionold = bindecode.getShort()*1000/(4.0);
            double jpositionold = bindecode.getShort()*1000/(4.0);
            lengthOfData -=4;
            while (lengthOfData > 0 ) {
                double iposition = bindecode.getShort()*1000/(4.0);
                double jposition = bindecode.getShort()*1000/(4.0);
               // data.add(new StormTrackData(ipositionold,jpositionold,iposition,jposition, future));

                ipositionold = iposition;
                jpositionold = jposition;
                lengthOfData -= 4;
            }
    }
    
    private void processPacketCode23(int lengthOfLayerTemp) throws IOException {
        future=false;
        
            while (lengthOfLayerTemp > 0) {
                int packetCode = bindecode.getShort();
                int lenData = bindecode.getShort();
                processPacket(packetCode, lenData);
                lengthOfLayerTemp -= (lenData+4);
            }
        
    }
    private void processPacketCode24(int lengthOfLayer) throws IOException {
        future=true;
        
            while (lengthOfLayer > 0) {
                int packetCode = bindecode.getShort();
                int lenData = bindecode.getShort();
                processPacket(packetCode, lenData);
                lengthOfLayer -= (lenData+4);
            }
        
    }
    
    public void processPacketCode20() throws IOException {
      double iposition = bindecode.getShort()*1000./(4.0);
      double jposition = bindecode.getShort()*1000./(4.0);
      int featureType = bindecode.getShort();
      double featureatt = bindecode.getShort()*1000./(4.0);
     // System.out.println(iposition+", "+jposition+" "+"Feature Type ="+featureType+" Feature Attribute ="+featureatt);
      if (featureType <= 4 || featureType == 9 || featureType == 10 || featureType == 11) {
    	  MesocycloneData cyclone = new MesocycloneData(iposition, jposition, featureatt);
    	  currMeso = cyclone;
    	  data.add(cyclone);
      }
      //data.add(new TextData(iposition,jposition,new String("Meso")));
 
}
   
    private void processPacket(int packetCode, int lengthOfLayer) throws IOException {
    	//System.out.println("Packet code ="+packetCode);
            if (packetCode == 23){
                 processPacketCode23(lengthOfLayer);
            } else if (packetCode == 24) {
               processPacketCode24(lengthOfLayer);
            } else if (packetCode == 2) {
                processPacketCode2(lengthOfLayer);
            } else if (packetCode == 6) {
                processPacketCode6(lengthOfLayer);
            } else if (packetCode == 8) {
                processPacketCode8(lengthOfLayer);
            } else if (packetCode == 20) {
                processPacketCode20();
            } else {
            	bindecode.skip(lengthOfLayer);
            }
       
    }
    
}
