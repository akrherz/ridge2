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
import gov.noaa.nws.radardata.RadarData;
import gov.noaa.nws.radardata.TVSData;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author jburks
 */
public class TVSDecoder extends RadarDecoder {
    
    int lengthOfBlock;
    ArrayList<RadarData> data = new ArrayList();
   
    public TVSDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode,numLevels);
    }

   public ArrayList<RadarData> decode() throws IOException {
        process();
        return(data);
    }
    
    public void process() throws IOException {
            long offsetbyte = (offsetToSymbology*2);
            bindecode.seek(offsetbyte+4);
            
            lengthOfBlock = bindecode.getInt();
//        System.out.println("Beginning decoding Storm Track data");
//        System.out.println("Message Code ="+messageCode);
//        System.out.println("Date of Message "+bindecode.read(2,2));
//        System.out.println("Message Code ="+messageCode);
//        System.out.println("Number of Blocks "+bindecode.read(2,16));
//        System.out.println("Block Divider"+bindecode.read(2,18));
//        System.out.println("latitude "+bindecode.read(4,20));
//        System.out.println("longitude "+bindecode.read(4,24)
            //  System.out.println("***************************Processing Graphic Block****************************************");
//        processGraphicBlock(numberToGraphic);
//        System.out.println("***************************Processing Symbology Block****************************************");
            processSymbologyBlock(offsetToSymbology);
            // System.out.println("***************************Processing Tabular Block****************************************");
            //  processTabularBlock(numberToTabular);
//        System.out.println("*******************************************************************************************");
//            bindecode.seek(offsetbyte+12);
//            dataLayerBytes = bindecode.getInt();
       
//        System.out.println("Length of data layer "+dataLayerBytes);
        //readRows(numberRangeBins, numberRadials, (int)(offsetToSymbology*2)+30);
    }
    
    private void readRows(int numRangeBins, int numRadials, int startByte) throws IOException {
            lengthOfBlock = lengthOfBlock - 16 -14;
            startByte  = (int)(offsetToSymbology*2)+22;
//        System.out.println("Start Byte "+startByte);
            bindecode.seek(startByte);
            while (lengthOfBlock > 0) {
//                System.out.println("I = "+bindecode.read(2,true));
                lengthOfBlock = lengthOfBlock -2;
//                System.out.println("J="+bindecode.read(2,true));
                lengthOfBlock = lengthOfBlock - 2;
//                System.out.println("ID "+bindecode.readSingleByte()+" "+bindecode.readSingleByte());
                lengthOfBlock = lengthOfBlock - 2;
            }
    }
    
    private void processGraphicBlock(long numberToGraphic) throws IOException {
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
            
            //processPacketCodeEight(lengthOfBlock);
    }
    
    private void processTabularBlock(long numberToTabular) throws IOException {
            bindecode.seek((int)(numberToTabular*2));
//        System.out.println("block divider "+bindecode.read(2,true));
//        System.out.println("block ID  "+bindecode.read(2));
//        System.out.println("Length of Block  "+bindecode.read(4));
    }
    
    private void processSymbologyBlock(long numberToSymbology) throws IOException{
            bindecode.seek(numberToSymbology*2+4);
//        System.out.println("block divider "+bindecode.read(2,true));
//        System.out.println("block ID  "+bindecode.read(2));
            int lengthOfBlock = bindecode.getInt();
            bindecode.seek(numberToSymbology*2+16);
//        System.out.println("Length of Block  "+lengthOfBlock);
//        System.out.println("Number of Layers "+bindecode.getShort());
//        System.out.println("Layer Divider "+bindecode.getShort());
//        System.out.println("Length data layer "+bindecode.getInt());
//            
            
            processTVSSymbology(lengthOfBlock);
        
    }
    
    private void processTVSSymbology(int lengthOfBlock) throws IOException {
            lengthOfBlock -= 8;
            while (lengthOfBlock > 0) {
                int packetCode = bindecode.getShort();
                int lenData = bindecode.getShort();
                lengthOfBlock -= (4+lenData);
                if (packetCode == 12 ){
                    double iposition = bindecode.getShort()*1000./(4.0);
                    lenData -= 2;
                    double jposition = bindecode.getShort()*1000./(4.0);
                    lenData -= 2;
                    int radius = bindecode.getShort();
                     System.out.println("New TVS");
                    data.add(new TVSData(iposition,jposition));
                }
            }
    }
    
  
    public Threshold[] getThresholds() {
        return(null);
    }
    
}
