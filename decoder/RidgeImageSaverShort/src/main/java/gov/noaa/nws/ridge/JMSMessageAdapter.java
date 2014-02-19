/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;
import gov.noaa.nws.ridge.common.message.JMSMessageSerializer;

import javax.jms.BytesMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 *
 * @author Jason.Burks
 */
public class JMSMessageAdapter implements MessageListener{
    ThreadPoolTaskExecutor writerExecutor;
    String startPath;

    public void onMessage(Message message) {
    	try {
    	ProcessedRadarFile radarFile = JMSMessageSerializer.unmarshall(message);
            writerExecutor.execute(new ShortFileWriterTask(radarFile,startPath));
    	}catch(Exception e) {
	    	  e.printStackTrace();
	          Logger.getLogger(JMSMessageAdapter.class.getName()).log(Level.INFO, null, e);
	      }    

    }

    public void setWriterExecutor(ThreadPoolTaskExecutor writerExecutor) {
        this.writerExecutor = writerExecutor;
    }

    
    public void setStartPath(String startPath) {
    	Logger.getLogger(JMSMessageAdapter.class).info("Starting Path is "+startPath);
		this.startPath = startPath;
	}
}
