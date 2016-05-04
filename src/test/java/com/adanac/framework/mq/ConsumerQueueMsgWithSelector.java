package com.adanac.framework.mq;

import javax.jms.JMSException;
import javax.jms.Message;

import com.adanac.framework.mq.activemq.MQConfig;
import com.adanac.framework.mq.activemq.MQTemplate;
import com.adanac.framework.mq.activemq.MsgListener;
import com.adanac.framework.mq.activemq.consumer.Consumer;
import com.adanac.framework.mq.activemq.consumer.MQConsumer;

/**
 * 消息选择器
 */
public class ConsumerQueueMsgWithSelector {
	public static void main(String[] args) {
		MQConfig mqConfig = new MQConfig();
		mqConfig.setBrokerURL(
				"failover:(tcp://192.168.1.16:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.17:61616?wireFormat.maxInactivityDuration=0,tcp://192.168.1.9:61616?wireFormat.maxInactivityDuration=0)");

		MQTemplate template = new MQTemplate(mqConfig);
		template.init();
		Consumer consumer = new MQConsumer(template);

		consumer.listenQueue("queue_with_prop", "tag='good'", new MsgListener() {

			public void onMsg(Message orginMsg, Object msgContent) {
				try {
					Object prop = orginMsg.getObjectProperty("tag");
					System.out.println(prop);
					System.out.println("queue_with_prop content:" + msgContent + "prop:" + prop);
				} catch (JMSException e) {

					e.printStackTrace();
				}

			}
		});

	}
}
