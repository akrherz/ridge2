/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.chains;

import gov.noaa.nws.ridgeserver.events.fileevents.NewFileEvent;
import gov.noaa.nws.ridgeserver.fileutils.FileDeliveryListener;
import gov.noaa.nws.ridgeserver.postprocessing.ProcessRadarFile;

import java.io.File;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 * @author Jason.Burks
 */
public class DecodeManager implements FileDeliveryListener {

	ThreadPoolTaskExecutor decoderExecutor;
	RadarTypeManager productManager;
    ProcessRadarFile processer;
    TimeManager timeManager;
    



    public void setTimeManager(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    public void setProcesser(ProcessRadarFile processer) {
        this.processer = processer;
    }

    public void setProductManager(RadarTypeManager productManager) {
    	DecimalFormat format = new DecimalFormat("##.#");
    	String[] types = productManager.getListOfTypes();
    	Logger.getLogger(DecodeManager.class).info("Product Manager Supported Types");
    	for (String type : types) {
    		try {
				RadarProductType radarProduct = productManager.getRadarType(type);
				Logger.getLogger(DecodeManager.class).info("Type="+type+" messageCode="+radarProduct.getMessageCode()+" colorcurve="+radarProduct.getRadarData().getDefaultColorCurveName()+" range of image="+format.format(radarProduct.rangeInMeters*0.000621371192)+" miles");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	Logger.getLogger(DecodeManager.class).info("End of Product Manager Supported Types");
        this.productManager = productManager;
    }

    public void setDecoderExecutor(ThreadPoolTaskExecutor decoderExecutor) {
        this.decoderExecutor = decoderExecutor;
        
    }

    public void deliverFile(File file) {
        RadarProductType type = null;
        RidgeNamer namer =null;
        TimeCheck holder = null;
        File fileNew = new File(file.getAbsoluteFile() + ".ridge");
        file.renameTo(fileNew);
        file = null;
        NewFileEvent event = new NewFileEvent(fileNew.getAbsolutePath());
        try {
            namer = new RidgeNamer(event.getFilename());
            type = productManager.getRadarType(namer.getZZZ());
            holder = timeManager.getProductTimeHolder(namer.getXXX()+":"+namer.getZZZ());
            decoderExecutor.execute(new DecodeTask(event, type, namer,processer,holder)); 
        } catch (Exception ex) {
            Logger.getLogger(DecodeManager.class.getName()).info(ex);
            DeleteSingleton.getInstance().deleteFile(event.getFilename());
        }
       
    }

    public void deliverFileInBytes(byte[] bytes) {
       
    }


}
