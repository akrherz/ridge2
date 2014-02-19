package gov.noaa.nws.ridgeserver.postprocessing;

import gov.noaa.nws.ridge.common.event.ProcessedRadarFile;
import gov.noaa.nws.ridge.common.message.AMQPMessageSerializer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class AMQPPublisher implements ProcessRadarFile {

	private RabbitTemplate rabbitTemplate;

	public AMQPPublisher() {

	}
	public void processRadarFile(ProcessedRadarFile radarFile) {
		try {
			String key = radarFile.getXXX()+"."+radarFile.getZZZ();
			Message message = AMQPMessageSerializer.marshall(radarFile);
			rabbitTemplate.send(key, message);
		} catch (Exception e) {
			System.out.println("Exception "+e);
			e.printStackTrace();
		}
	}

	public RabbitTemplate getRabbitTemplate() {
		return rabbitTemplate;
	}

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	

}
