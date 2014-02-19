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
import gov.noaa.nws.radardecoderlib.radardecoders.thresholds.Threshold;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author jburks
 */
public class StormTrackDecoder extends RadarDecoder {
    int lengthOfBlock;
    boolean future;
    String currentStorm="";
    String watchStorm = "C6";
    ArrayList<RadarData> data = new ArrayList<RadarData>();
    
    public StormTrackDecoder(BinaryReader bindecode, int numLevels) throws IOException {
        super(bindecode,numLevels);
    }

    @Override
    public ArrayList<RadarData> decode() throws IOException {
        process();
        return(data);
    }
    
    public void process() throws IOException {
      

            if (offsetToGraphic > 0 ) {
//        processGraphicBlock(numberToGraphic);
            }
//        System.out.println("***************************Processing Symbology Block****************************************"+numberToSymbology);
            if (offsetToSymbology > 0 ){
                processSymbologyBlock(offsetToSymbology);
            }
            // System.out.println("***************************Processing Tabular Block****************************************");
            if (offsetToTabular > 0) {
                //  processTabularBlock(numberToTabular);
            }

        
    }
    
    
    private void processGraphicBlock(long numberToGraphic) throws IOException {
            bindecode.seek((int)(numberToGraphic*2));
            lengthOfBlock = bindecode.getInt();
            int lengthOfPages = bindecode.getShort();
    }
    
    private void processTabularBlock(long numberToTabular) throws IOException {
            bindecode.seek(offsetToTabular*2);
    }
    
    private void processSymbologyBlock(long numberToSymbology) throws IOException {
            bindecode.seek(offsetToSymbology*2+4);
            lengthOfBlock = bindecode.getInt();
            
            processStormTrackSymbology();
    }
    private void processStormTrackSymbology() throws IOException {
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
       
            if (packetCode == 23){
                value =  processPacketCode23(lengthOfLayer);
            } else if (packetCode == 24) {
                value = processPacketCode24(lengthOfLayer);
            } else if (packetCode == 2) {
                value = processPacketCode2(lengthOfLayer);
            } else if (packetCode == 15) {
                value = processPacketCode15(lengthOfLayer);
            } else if (packetCode == 19) {
                value = processPacketCode19(lengthOfLayer);
            } else if (packetCode == 6) {
                value = processPacketCode6(lengthOfLayer);
            }  else if (packetCode ==25) {
                value = processPacketCode25();
            }
       
        return(value);
    }
    
    private int processPacketCode15(int lengthOfData) throws IOException {
        int total = lengthOfData;
            while (lengthOfData > 0) {
                double iposition = bindecode.getShort()*1000./(4.0);
                double jposition = bindecode.getShort()*1000./(4.0);
                char[] chars = new char[2];
                chars[0]=(char)bindecode.getByteAsInt();
                chars[1]=(char)bindecode.getByteAsInt();
                currentStorm = new String(chars);
                data.add(new TextData( iposition,jposition,new String(chars)));
                lengthOfData -= 6;
            }
        return(total);
        
    }
    private int processPacketCode19(int lengthOfData) throws IOException {
        int total = lengthOfData;
            while (lengthOfData > 0) {
                
                double iposition = bindecode.getShort()*1000/(4.0);
                double jposition = bindecode.getShort()*1000/(4.0);
                double probhail = bindecode.getShort();
                double probsevhail= bindecode.getShort();
                double maxhail = bindecode.getShort();
                lengthOfData -= 10;
            }
        return(total);
        
    }
    private int processPacketCode25() throws IOException {
            double iposition = bindecode.getShort()*1000/(4.0);
            double jposition = bindecode.getShort()*1000/(4.0);
            double radius = bindecode.getShort()*1000/(4.0);
            data.add(new MesocycloneData(iposition,jposition,radius));
        return(6);
        
    }
    private int processPacketCode2(int lengthOfData) throws IOException {
        int total = lengthOfData;
            long positionstart = bindecode.getCurrentPosition();
            double iposition = bindecode.getShort()*1000/(4.0);
            double jposition = bindecode.getShort()*1000/(4.0);
            lengthOfData -= 4;
            char[] chars = new char[1];
            int count =0;
            while (lengthOfData > 0 ) {
                chars[0]=(char)bindecode.getByteAsInt();
                lengthOfData -= 1;
            }
            data.add(new MesocycloneData(iposition, jposition, .5));
        return(total);
    }
    private int processPacketCode6(int lengthOfLayer) throws IOException {
        int total = lengthOfLayer;
            double ipositionold = bindecode.getShort()*1000/(4.0);
            double jpositionold = bindecode.getShort()*1000/(4.0);
            lengthOfLayer -=4;
            while (lengthOfLayer > 0 ) {
                double iposition = bindecode.getShort()*1000/(4.0);
                double jposition = bindecode.getShort()*1000/(4.0);
                data.add(new StormTrackData(ipositionold,jpositionold,iposition,jposition, future));

                ipositionold = iposition;
                jpositionold = jposition;
                lengthOfLayer -= 4;
            }
        return(total);
    }
    private int processPacketCode23(int lengthOfLayer) throws IOException {
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
    private int processPacketCode24(int lengthOfLayer) throws IOException {
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
    
    public Threshold[] getThresholds() {
        return(null);
    }
}
