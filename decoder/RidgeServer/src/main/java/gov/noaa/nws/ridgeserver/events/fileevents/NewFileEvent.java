/*
 * NewFileEvent.java
 *
 * Created on December 1, 2005, 11:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.events.fileevents;
import gov.noaa.nws.radardecoderlib.binaryutils.BinaryReader;
import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;

import java.awt.image.BufferedImage;
import java.util.Date;
import org.opengis.geometry.DirectPosition;
/**
 *
 * @author jburks
 */
public class NewFileEvent {
    BinaryReader binaryReader;
    String filename;
    long timeReceived;
    long timeFinished;
    int messageCode;
    String outputFilename;
    long inputFileSize=0;
    
    long generationTime;
   
    long totalBinsDrawen=0;
    long totalBinsPossible=0;
    boolean deleteWhenDone = false;
    
	private double longitudeOfRadar, latitudeOfRadar;
    
    int timeToRender;
    int decodeTime;
    long timeProcessingStart;
    ProcessedRadarFile radarFile = new ProcessedRadarFile();
   
    
    /** Creates a new instance of NewFileEvent */
    public NewFileEvent(String filename) {
        this.filename = filename;
        timeReceived = System.currentTimeMillis();
        
    }

    public BufferedImage getImage() {
       return radarFile.getImage();
    }

    public void setImage(BufferedImage image) {
    	radarFile.setImageWidth(image.getWidth());
    	radarFile.setImageHeight(image.getHeight());
        radarFile.setImage(image);
    }

    public DirectPosition getLowerRight() {
        return radarFile.getLowerRight();
    }

    public void setLowerRight(DirectPosition lowerRight) {
        radarFile.setLowerRight(lowerRight);
    }

    public DirectPosition getUpperLeft() {
        return radarFile.getUpperLeft();
    }

    public void setUpperLeft(DirectPosition upperLeft) {
    	radarFile.setUpperLeft(upperLeft);
    }


    public double getLatitudeOfRadar() {
        return latitudeOfRadar;
    }

    public void setLatitudeOfRadar(double latitudeOfRadar) {
        this.latitudeOfRadar = latitudeOfRadar;
    }

    public double getLongitudeOfRadar() {
        return longitudeOfRadar;
    }

    public void setLongitudeOfRadar(double longitudeOfRadar) {
        this.longitudeOfRadar = longitudeOfRadar;
    }

    public Date getValidTime() {
        return radarFile.getValidTime();
    }

    public void setValidTime(Date validTime) {
        radarFile.setValidTime(validTime);
    }
  

    public long getTotalBinsPossible() {
        return totalBinsPossible;
    }

    public long getTotalBinsDrawen() {
        return totalBinsDrawen;
    }

    public void setTotalBinsPossible(long totalBinsPossible) {
        this.totalBinsPossible = totalBinsPossible;
    }

    public void setTotalBinsDrawen(long totalBinsDrawen) {
        this.totalBinsDrawen = totalBinsDrawen;
    }

    public String getFilename() {
        return(filename);
    }
    
    public long getTimeReceived(){
        return(timeReceived);
    }
    
    public void setSiteID(int siteID){
       radarFile.setSiteID(siteID);
    }
    public int getSiteID() {
        return(radarFile.getSiteID());
    }
    public void setGenerationTime(long time) {
        this.generationTime = time;
    }
    public long getGenerationTime() {
        return(generationTime);
    }
    public void setVCP(int vcp) {
        radarFile.setVcp(vcp);
    }
    public ProcessedRadarFile getRadarFile() {
		return radarFile;
	}

	public int getVCP() {
        return(radarFile.getVcp());
    }
    public void setOutputFilename(String name) {
        this.outputFilename = name;
    }
    
    public String getOutputFilename() {
        return(outputFilename);
    }
    public void setDeleteWhenDone(boolean deleteOnDone) {
        this.deleteWhenDone = deleteOnDone;
    }
    public boolean deleteWhenDone() {
        return(deleteWhenDone);
    }
    public void setBinaryReader(BinaryReader binaryReader) {
        this.binaryReader = binaryReader;
    }
    
    public BinaryReader getBinaryReader(){
        return(binaryReader);
    }
    public void setTimeFinished(){
        this.timeFinished = System.currentTimeMillis();
    }
    
    public long getTimeFinished() {
        return(timeFinished);
    }
     public void setTimeProcessingStart(){
        this.timeProcessingStart = System.currentTimeMillis();
    }
    
    public long getTimeProcessingStart() {
        return(timeProcessingStart);
    }
    
    public void setMessageCode(int messageCode) {
        this.messageCode = messageCode;
    }
    
    public int getMessageCode(){
        return(messageCode);
    }
    
    public long getProcessTotalTime(){
        return(timeFinished - timeReceived);
    }
    public long getProcessTime(){
        return(timeFinished - timeProcessingStart);
    }
    
    public void setFileInputSize(long inputFileSize) {
        this.inputFileSize = inputFileSize;
    }
    public long getFileInputSize(){
        return(inputFileSize);
    }

    public String getZZZ() {
        return radarFile.getZZZ();
    }

    public void setZZZ(String ZZZ) {
        radarFile.setZZZ(ZZZ);
    }

    
    public int getDecodeTime() {
        return decodeTime;
    }

    public void setDecodeTime(int decodeTime) {
        this.decodeTime = decodeTime;
    }

    public long getInputFileSize() {
        return inputFileSize;
    }

    public void setInputFileSize(long inputFileSize) {
        this.inputFileSize = inputFileSize;
    }

  

    public int getTimeToRender() {
        return timeToRender;
    }

    public void setTimeToRender(int timeToRender) {
        this.timeToRender = timeToRender;
    }

    public String getXXX() {
        return radarFile.getXXX();
    }

    public void setXXX(String XXX) {
        radarFile.setXXX(XXX);
    }
    public byte[] getByteImage() {
        return radarFile.getByteImage();
    }

    public void setByteImage(byte[] byteImage) {
        radarFile.setByteImage(byteImage);
    }
    
    public float getElevationAngle() {
		return radarFile.getElevationAngle();
	}

	public void setElevationAngle(float elevationAngle) {
		radarFile.setElevationAngle(elevationAngle);
	}

	public Date getStormTotalPrecipBegin() {
		return radarFile.getStormTotalPrecipBegin();
	}

	public void setStormTotalPrecipBegin(Date stormTotalPrecipBegin) {
		radarFile.setStormTotalPrecipBegin(stormTotalPrecipBegin);
	}

	public Date getStormTotalPrecipEnd() {
		return radarFile.getStormTotalPrecipEnd();
	}

	public void setStormTotalPrecipEnd(Date stormTotalPrecipEnd) {
		radarFile.setStormTotalPrecipEnd(stormTotalPrecipEnd);
	}
	
	public float getStormRelativeSpeed() {
		return radarFile.getStormRelativeSpeed();
	}

	public void setStormRelativeSpeed(float stormrelativespeed) {
		radarFile.setStormRelativeSpeed(stormrelativespeed);
	}

	public float getStormRelativeDirection() {
		return radarFile.getStormRelativeDirection();
	}

	public void setStormRelativeDirection(float stormrelativedirection) {
		radarFile.setStormRelativeDirection(stormrelativedirection);
	}
	
	public void setImageWidth(int imageWidth) {
		radarFile.setImageWidth(imageWidth);
	}
	public int getImageWidth() {
		return radarFile.getImageWidth();
	}
	
	public void setImageHeight(int imageHeight) {
		radarFile.setImageHeight(imageHeight);
	}
	public int getImageHeight() {
		return radarFile.getImageHeight();
	}

}
