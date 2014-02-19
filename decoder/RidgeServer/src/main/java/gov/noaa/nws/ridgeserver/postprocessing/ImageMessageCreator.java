/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.postprocessing;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;
import gov.noaa.nws.ridge.common.message.JMSMessageSerializer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.springframework.jms.core.MessageCreator;

/**
 *
 * @author Jason.Burks
 */
public class ImageMessageCreator implements MessageCreator {
    ProcessedRadarFile radarFile ;

    public ImageMessageCreator(ProcessedRadarFile radarFile) {
        this.radarFile = radarFile;
    }
    
    public Message createMessage(Session session) throws JMSException {
        Message message;
		try {
			message = JMSMessageSerializer.marshall(radarFile, session);
			return(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JMSException("Problem creating message "+e.getMessage());
		}
       
    }
}


