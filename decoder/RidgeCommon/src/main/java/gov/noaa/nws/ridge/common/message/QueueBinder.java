package gov.noaa.nws.ridge.common.message;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import com.rabbitmq.client.Channel;

public class QueueBinder implements InitializingBean {

	private ConnectionFactory connectionFactory;
	private Queue queue;
	private String exchangeName;
	private String[] routingKeys;

	public QueueBinder() {

	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String[] getRoutingKeys() {
		return routingKeys;
	}

	public void setRoutingKeys(String[] routingKeys) {
		this.routingKeys = routingKeys;
	}

	public void afterPropertiesSet() throws Exception {
		//LoggerFactory.getLogger(this.getClass()).info("QueueBinder with: " + exchangeName);
		try {
			Channel channel = connectionFactory.createConnection()
					.createChannel(true);
			channel.queueDeclare(queue.getName(), queue.isDurable(),
					queue.isExclusive(), queue.isAutoDelete(),
					queue.getArguments());
			for (String key : routingKeys) {
			//	LoggerFactory.getLogger(this.getClass()).info("QueueBinder with: " + queue.getName()+" "+this.exchangeName+" "+key);
				channel.queueBind(queue.getName(), this.exchangeName, key);
			}
		} catch (Exception e) {
			throw e;
		}

	}

}
