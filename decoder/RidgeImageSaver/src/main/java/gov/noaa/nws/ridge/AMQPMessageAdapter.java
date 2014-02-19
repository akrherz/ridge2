/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridge;


import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;
import gov.noaa.nws.ridge.common.message.AMQPMessageSerializer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 *
 * @author Jason.Burks
 */
public class AMQPMessageAdapter implements MessageListener{
    ThreadPoolTaskExecutor writerExecutor;
    String startPath;

    public void setWriterExecutor(ThreadPoolTaskExecutor writerExecutor) {
        this.writerExecutor = writerExecutor;
    }
    
    public void setStartPath(String startPath) {
		this.startPath = startPath;
		Logger.getLogger(AMQPMessageAdapter.class).info("Starting Path is "+startPath);
	}

	public void onMessage(Message message) {
      try {
    	  ProcessedRadarFile radarFile = AMQPMessageSerializer.unmarshall(message);
    	  writerExecutor.execute(new FileWriterTask(radarFile,startPath));
  } catch (Exception ex) {
	  ex.printStackTrace();
      Logger.getLogger(AMQPMessageAdapter.class.getName()).log(Level.INFO, null, ex);
 }
		
	}

}
