package com.adanac.framework.mq;

import javax.jms.DeliveryMode;

import com.adanac.framework.mq.activemq.MQConfig;
import com.adanac.framework.mq.activemq.MQTemplate;
import com.adanac.framework.mq.activemq.MessageConfig;
import com.adanac.framework.mq.activemq.producer.MQProducer;
import com.adanac.framework.mq.activemq.producer.Producer;

/**
 * 发送queue message
 *
 */

public class ProducerQueueMessage {
	public static void main(String[] args) {
		MQConfig mqConfig = new MQConfig();
		mqConfig.setBrokerURL(
				"failover:(tcp://192.168.1.16:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.17:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.9:61616?wireFormat.maxInactivityDuration=0)");

		MQTemplate template = new MQTemplate(mqConfig);
		template.init();
		Producer producer = new MQProducer(template);

		for (int i = 0; i < 1; i++) {
			MessageConfig config = new MessageConfig();
			config.setTimeToLive(100 * 1000);
			config.setDeliveryMode(DeliveryMode.PERSISTENT);

			config.setPriority(5);
			producer.sendQueue("queue_test", "contet", null, config);
		}

		template.destory();

	}

}