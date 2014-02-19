/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

import gov.noaa.nws.radardecoderlib.binaryutils.BinaryUtilities;
import gov.noaa.nws.radardecoderlib.binaryutils.MappedByteBufferReader;
import gov.noaa.nws.radardecoderlib.gis.CoordinateHolder;
import gov.noaa.nws.radardecoderlib.gis.GeographicsCoordinateFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.DecoderFactory;
import gov.noaa.nws.radardecoderlib.radardecoders.RadarDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.StormRelativeMotionDecoder;
import gov.noaa.nws.radardecoderlib.radardecoders.StormTotalPrecipDecoder;
import gov.noaa.nws.radardecoderlib.renderers.RadarSpatialRenderer;
import gov.noaa.nws.radardecoderlib.renderers.RendererFactory;
import gov.noaa.nws.ridgeserver.events.fileevents.FileAlreadyProcessedException;
import gov.noaa.nws.ridgeserver.events.fileevents.FileNotReadyException;
import gov.noaa.nws.ridgeserver.events.fileevents.NewFileEvent;
import gov.noaa.nws.ridgeserver.postprocessing.ProcessRadarFile;

import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

/**
 *
 * @author Jason.Burks
 */
public class DecodeTask implements Runnable { 
    NewFileEvent event;
    RidgeNamer namer;
    RadarProductType type;
    ProcessRadarFile processer;
    TimeCheck timeHolder;
    public DecodeTask(NewFileEvent event,RadarProductType type,RidgeNamer namer,ProcessRadarFile processer, TimeCheck holder) {
        this.event = event;
        this.type = type;
        this.namer = namer;
        this.processer = processer;
        this.timeHolder = holder;
    }
    
	public void run() {
		if (type != null) {
			try {
				
				//Setup Binary Reader for the decoder
				event.setBinaryReader(new MappedByteBufferReader(event.getFilename()));
				//Check to see if the data is zlib compressed, this is the way with NOAAPort data.
				if (BinaryUtilities.isReady(event.getBinaryReader()) != true) {
					throw new FileNotReadyException("File not ready so try again "+event.getFilename());
				}
				if (BinaryUtilities.isZlibCompressed(event.getBinaryReader())) {
					event.setBinaryReader(BinaryUtilities.zlibUncompress(event.getBinaryReader()));
				}
				//Check to see if the file has a header, so that I can skip it.
				BinaryUtilities.setupHeaderOffset(event.getBinaryReader());
				//now skip to beginning of the file, including the header consideration
				event.getBinaryReader().seek(0);
				//Check to see if it is BZIP2 Compressed, TDWR data is usually BZIP2 Compressed
				if (BinaryUtilities.isBZip2Compressed(event.getBinaryReader())) {
					//If it is compressed, uncompress it.
					event.setBinaryReader(BinaryUtilities.unCompress(event.getBinaryReader()));
				}
				//Setup the proper decoder for this radar data type
				RadarDecoder decoder = DecoderFactory.getDecoder(type.getRadarData(), event.getBinaryReader());
				// reset decoder and start decoding
				//Get the date of the radar data, and set it within the namer
				namer.setDateOfRadarImage(decoder.getRadarScanTime());
				//extract the ZZZ, and XXX
				event.setZZZ(namer.getZZZ());
				event.setXXX(namer.getXXX());
				event.setMessageCode(type.getRadarData().getMessageCode());
				//Check to see if we have already processed this image, checking is done within TimeHolder,
				//which is product specific.
				long timeOfCurrentlyBeingProcessed = decoder.getRadarScanTime().getTime();
				if (timeHolder.hasBeenProcessed(timeOfCurrentlyBeingProcessed) == true) {
					throw new FileAlreadyProcessedException("File already been processed");
				}
				//get the right radar renderer.
				RadarSpatialRenderer renderer = RendererFactory.getRadarRenderer(type.getRadarData(), type.getImageWidth(), type.getImageHeight());
				//Setup coordinate system.
				CoordinateHolder holder = GeographicsCoordinateFactory.getTransformForGeo(type.getImageWidth(), type.getImageHeight(), decoder.getRadarLocation(), type.getRangeInMeters());
				renderer.setTransform(holder.getTransform(), decoder
						.getRadarLocation(), decoder.getElevationAngle());
				
				//get id for the radar
				event.setSiteID(decoder.getSiteID());
				//get generation time for the radar data, and VCP,and elevation angle, etc
				event.setGenerationTime(decoder.getRadarGenerationTime().getTime());
				event.setVCP(decoder.getVCP());
				event.setTimeProcessingStart();
				event.setElevationAngle((float) decoder.getElevationAngle());
				event.setValidTime(decoder.getRadarScanTime());
				event.setLongitudeOfRadar(decoder.getRadarLocation().getOrdinate(0));
				event.setLatitudeOfRadar(decoder.getRadarLocation().getOrdinate(1));
				event.setUpperLeft(holder.getUpperLeft());
				event.setLowerRight(holder.getLowerRight());
				//If this product is StormTotal precip I need to get the time the precip begin and end
				if (decoder instanceof StormTotalPrecipDecoder) {
					event.setStormTotalPrecipBegin(((StormTotalPrecipDecoder) decoder).getStormTotalPrecipBegin());
					event.setStormTotalPrecipEnd(((StormTotalPrecipDecoder) decoder).getStormTotalPrecipEnd());
				}
				if (decoder instanceof StormRelativeMotionDecoder) {
					event.setStormRelativeDirection(((StormRelativeMotionDecoder) decoder).getStormRelativeDirection());
					event.setStormRelativeSpeed(((StormRelativeMotionDecoder) decoder).getStormRelativeSpeed());
				} 
				//get the color curve
				renderer.setColor(type.getColorManager().getColors(decoder.getThresholds()));
				//Set start time to monitor how long it takes to process
				long startTime = System.currentTimeMillis();
				//get the radar data
				renderer.setRadarData(decoder.decode());
				event.setDecodeTime((int) (System.currentTimeMillis() - startTime));
				startTime = System.currentTimeMillis();
				event.setImage(renderer.render());
				event.setTimeToRender((int) (System.currentTimeMillis() - startTime));
				startTime = System.currentTimeMillis();
				//Write the png out into a Byte Array
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(event.getImage(), "png", bos);
				bos.close();
				event.setByteImage(bos.toByteArray());
				//Put the time of this radar image into the time holder
				timeHolder.insertTime(timeOfCurrentlyBeingProcessed);
				//Set time finished
				event.setTimeFinished();
				Logger.getLogger(DecodeTask.class).info(
						event.getFilename() + " "+event.getMessageCode()+" "+ event.getZZZ() + " "
								+ event.getXXX() + " tpt="
								+ event.getProcessTotalTime() + " rt="
								+ event.getTimeToRender());
				//Send the processed image to jms
				
				processer.processRadarFile(event.getRadarFile());
				event.getBinaryReader().close();
				event.setBinaryReader(null);
				renderer = null;
				decoder = null;
			} catch (FileNotReadyException e1) {
				//System.out.println(e1.getMessage());
				//This will result in the file being processed again. it was not ready the first time around.
			} catch (FileAlreadyProcessedException e2) {
				Logger.getLogger(DecodeTask.class).info("File already processed "+event.getFilename());
			} catch (Exception e) {
				Logger.getLogger(DecodeTask.class).info(
						"Problem decoding " + event.getFilename() + "  "
								+ e.getMessage());
			}
			
		}
		//delete the file
		DeleteSingleton.getInstance().deleteFile(event.getFilename());
	}

}
