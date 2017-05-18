package com.adanac.framework.mq;

import javax.jms.DeliveryMode;

import com.adanac.framework.mq.activemq.MQConfig;
import com.adanac.framework.mq.activemq.MQTemplate;
import com.adanac.framework.mq.activemq.MessageConfig;
import com.adanac.framework.mq.activemq.producer.MQProducer;
import com.adanac.framework.mq.activemq.producer.Producer;

/**
 * 发送DurableTopic
 *
 */
public class ProducerDurableTopic {
	public static void main(String[] args) {
		MQConfig mqConfig = new MQConfig();

		mqConfig.setBrokerURL(
				"failover:(tcp://192.168.1.16:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.17:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.9:61616?wireFormat.maxInactivityDuration=0)");
		mqConfig.setClientIDPrefix("durable_topic_1");

		MQTemplate template = new MQTemplate(mqConfig);
		template.init();
		Producer producer = new MQProducer(template);

		MessageConfig messageConfig = new MessageConfig();
		messageConfig.setDeliveryMode(DeliveryMode.PERSISTENT);

		producer.sendTopic("durable_topic_1", "durable_1", null, messageConfig);
		producer.sendTopic("durable_topic_1", "durable_2", null, messageConfig);

		template.destory();
	}

}