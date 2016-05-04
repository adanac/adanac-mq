package com.adanac.framework.mq;

import javax.jms.Message;

import com.adanac.framework.mq.activemq.MQConfig;
import com.adanac.framework.mq.activemq.MQTemplate;
import com.adanac.framework.mq.activemq.MsgListener;
import com.adanac.framework.mq.activemq.consumer.Consumer;
import com.adanac.framework.mq.activemq.consumer.MQConsumer;

public class ConsumerDurableTopicListener {

	public static void main(String[] args) {
		MQConfig mqConfig = new MQConfig();
		mqConfig.setBrokerURL(
				"failover:(tcp://192.168.1.16:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.17:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.9:61616?wireFormat.maxInactivityDuration=0)");

		mqConfig.setClientID("durable_topic_1_1");

		MQTemplate template = new MQTemplate(mqConfig);

		template.init();

		Consumer consumer = new MQConsumer(template);

		consumer.listenDurableTopic("durable_topic_1", "durable_topic_1_1", new MsgListener() {

			public void onMsg(Message orginMsg, Object msgContent) {
				System.out.println("durable_topic  content:" + msgContent);

			}
		});

	}
}
