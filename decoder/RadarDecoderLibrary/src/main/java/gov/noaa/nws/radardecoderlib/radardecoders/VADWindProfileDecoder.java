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
import gov.noaa.nws.radardata.StormTrackData;
import gov.noaa.nws.radardata.TextData;
import gov.noaa.nws.radardata.WindData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import java.io.IOException;
/**
 *
 * @author jburks
 */
public class VADWindProfileDecoder extends RadarDecoder {
    int lengthOfBlock;
    boolean future;
    String currentStorm="";
    String watchStorm = "C6";
    
    public VADWindProfileDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode, numLevels);
    }

    
    
    protected void process() throws IOException {
        // System.out.println("Beginning decoding VAD Wind data");
//        System.out.println("Message Code ="+messageCode);
//        System.out.println("Date of Message "+bindecode.read(2,2));
//        System.out.println(" This is the value Message Code ="+messageCode);
//        System.out.println("Number of Blocks "+bindecode.read(2,16));
//        System.out.println("Block Divider"+bindecode.read(2,18));
//        System.out.println("latitude "+bindecode.read(4,20));
//        System.out.println("longitude "+bindecode.read(4,24));
        //  System.out.println("***************************Processing Graphic Block****************************************");
        if (offsetToGraphic > 0 ) {
//        processGraphicBlock(numberToGraphic);
        }
      //  System.out.println("***************************Processing Symbology Block****************************************");
        if (offsetToSymbology > 0 ){
            // System.out.println("Number to symbology = "+numberToSymbology);
            processSymbologyBlock(offsetToSymbology);
        }
        // System.out.println("***************************Processing Tabular Block****************************************");
        if (offsetToTabular > 0) {
            //  processTabularBlock(numberToTabular);
        }
//        System.out.println("*******************************************************************************************");
//        dataLayerBytes = bindecode.read(4, (numberToSymbology*2)+12);
//        System.out.println("Length of data layer "+dataLayerBytes);
    }
    
//    private void readRows(int numRangeBins, int numRadials, int startByte) {
//        lengthOfBlock = lengthOfBlock - 16 -14;
//        startByte  = (int)(offsetToSymbology*2)+22;
//        System.out.println("Start Byte "+startByte);
//        bindecode.seek(startByte);
//        while (lengthOfBlock > 0) {
//                System.out.println("I = "+bindecode.read(2,true));
//                lengthOfBlock = lengthOfBlock -2;
//                System.out.println("J="+bindecode.read(2,true));
//                lengthOfBlock = lengthOfBlock - 2;
//                System.out.println("ID "+bindecode.readSingleByte()+" "+bindecode.readSingleByte());
//                lengthOfBlock = lengthOfBlock - 2;
//        }
//    }
    
    private void processGraphicBlock(long numberToGraphic) throws IOException{
        try {
            bindecode.seek((int)(numberToGraphic*2));
//        System.out.println("block divider "+bindecode.read(2,true));
//        System.out.println("block ID  "+bindecode.read(2));
            lengthOfBlock = bindecode.getInt();
//        System.out.println("Length of Block  "+lengthOfBlock);
//        System.out.println("Number of Pages  "+bindecode.read(2));
//        System.out.println("Page Number  "+bindecode.read(2));
            int lengthOfPages = bindecode.getShort();
//        System.out.println("Length of Pages  "+lengthOfPages);
//        System.out.println("Need to start decoding stuff");
            
            //    processPacketCodeEight(lengthOfBlock);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void processTabularBlock(long numberToTabular) throws IOException{
        try {
            bindecode.seek(numberToTabular*2);
//        System.out.println("block divider "+bindecode.read(2,true));
//        System.out.println("block ID  "+bindecode.read(2));
//        System.out.println("Length of Block  "+bindecode.read(4));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void processSymbologyBlock(long numberToSymbology) throws IOException{
       // System.out.println(" starting at "+numberToSymbology);
            bindecode.seek(offsetToSymbology*2+4);
//        System.out.println("block divider "+bindecode.getShort());
//        System.out.println("block ID  "+bindecode.getShort());
            lengthOfBlock = bindecode.getInt();
            
      //  System.out.println("Length of Block  "+lengthOfBlock);
        bindecode.getShort();
        bindecode.getShort();
        bindecode.getInt();
            
            
            processVADSymbology();
    }
    private void processVADSymbology() throws IOException {
            lengthOfBlock -= 12;
            while (lengthOfBlock > 0) {
                int packetCode = bindecode.getShort();
                int lenData = bindecode.getShort();
                lengthOfBlock -= 4;
                int value = processPacket(packetCode, lenData);
                lengthOfBlock -= (value);
                
            }
    }
    
    private int processPacket(int packetCode, int lengthOfLayer) throws IOException {
        int value = 0;
        if (packetCode == 10) {
            value = processPacketCode10(lengthOfLayer);
        } else if (packetCode == 8) {
            value = processPacketCode8(lengthOfLayer);
        } else if (packetCode == 4) {
            value = processPacketCode4(lengthOfLayer);
        }
        return(value);
    }
    
    private int processPacketCode8(int lengthOfData) throws IOException{
        int total = lengthOfData;
            while (lengthOfData > 0) {
                double value = bindecode.getShort();
                double iposition = bindecode.getShort();
                double jposition = bindecode.getShort();
                lengthOfData -= 6;
                char[] characters = new char[lengthOfData];
                int i = 0;
                while (lengthOfData > 0) {
                    characters[i] = (char)bindecode.getByteAsInt();
                    --lengthOfData;
                    ++i;
                }
                data.add(new TextData( iposition,jposition,new String(characters)));
            }
        return(total);
        
    }
    
    private int processPacketCode10(int lengthOfLayer) throws IOException{
        int total = lengthOfLayer;
            double value = bindecode.getShort();
            lengthOfLayer -=2;
            while (lengthOfLayer > 0 ) {
                double ipositionold = bindecode.getShort();
                double jpositionold = bindecode.getShort();
                double iposition = bindecode.getShort();
                double jposition = bindecode.getShort();
                data.add(new StormTrackData(ipositionold,jpositionold,iposition,jposition,future));
                lengthOfLayer -= 8;
            }
        return(total);
    }
    
    private int processPacketCode4(int lengthOfLayer) throws IOException{
        //decoding linked vector
        int total = lengthOfLayer;
            double value = bindecode.getShort();
            double iposition = bindecode.getShort();
            double jposition = bindecode.getShort();
            double winddir = bindecode.getShort();
            double windspeed = bindecode.getShort();
            data.add(new WindData( iposition, jposition, (int)value, windspeed, winddir));
            lengthOfLayer -= 10;
        return(total);
    }
    private int processPacketCode23(int lengthOfLayer) throws IOException{
        future=false;
        int total = lengthOfLayer;
            while (lengthOfLayer > 0) {
                int packetCode = bindecode.getShort();
                int lenData = bindecode.getShort();
                int value =  processPacket(packetCode, lenData);
                lengthOfLayer -= (lenData+4);
            }
        return(total);
        
    }
    private int processPacketCode24(int lengthOfLayer) throws IOException{
        future=true;
        int total = lengthOfLayer;
            while (lengthOfLayer > 0) {
                int packetCode = bindecode.getShort();
                int lenData = bindecode.getShort();
                int value =  processPacket(packetCode, lenData);
                lengthOfLayer -= (lenData+4);
            }
        return(total);
        
    }
    
//    private void processPacketCodeEight(int lengthOfBlock) {
//        lengthOfBlock -= 12;
//        String output = new String();
//        while (lengthOfBlock > 0) {
//            int packetCode = bindecode.read(2);
//            //System.out.println("Packet Code  "+packetCode);
//            int lengthofDataBlock = bindecode.read(2);
//            lengthOfBlock -= (lengthofDataBlock+4);
//           // System.out.println("Length of Pages  "+lengthOfBlock);
//           // System.out.println("Length of data block  "+lengthofDataBlock);
//            if (packetCode == 8 ){
//                int colorcode = bindecode.read(2);
//           //     System.out.println("ColorCode = "+colorcode);
//                lengthofDataBlock -= 2;
//                int istart = bindecode.read(2);
//            //    System.out.println("I start = "+colorcode);
//                lengthofDataBlock -= 2;
//                int jstart = bindecode.read(2);
//             //   System.out.println("J start = "+colorcode);
//                lengthofDataBlock -= 2;
//
//                while (lengthofDataBlock > 0) {
//                    output += (char)bindecode.readSingleByte();
//                    --lengthofDataBlock;
//
//                }
//                output +="\n";
//             //   System.out.println(output);
//            }
//        }
//    }
   
}
