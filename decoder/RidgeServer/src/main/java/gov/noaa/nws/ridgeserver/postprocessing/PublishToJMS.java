/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.ridgeserver.postprocessing;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;
import javax.jms.Destination;
import org.springframework.jms.core.JmsTemplate;

/**
 *
 * @author Jason.Burks
 */
public class PublishToJMS implements ProcessRadarFile {
    JmsTemplate template;
    Destination destination;
    
    public void processRadarFile(ProcessedRadarFile radarFile) {
        template.send(destination,new ImageMessageCreator(radarFile));
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
    public void setTemplate(JmsTemplate template) {
        this.template = template;
    }
}
