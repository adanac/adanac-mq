package com.adanac.framework.mq.activemq.producer;

import java.lang.reflect.Method;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.jms.pool.PooledProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.SessionCallback;
import org.springframework.util.ReflectionUtils;

import com.adanac.framework.mq.activemq.MQBase;
import com.adanac.framework.mq.activemq.MQTemplate;
import com.adanac.framework.mq.activemq.MessageConfig;

/**
 * ActiveMQ producer
 * @author adanac
 * @version 1.0
 */
public class MQProducer extends MQBase implements Producer {
	private static final Logger LOG = LoggerFactory.getLogger(MQProducer.class);

	private static final Method GET_MESSAGE_RPODUCER;

	static {
		GET_MESSAGE_RPODUCER = ReflectionUtils.findMethod(PooledProducer.class, "getMessageProducer");
		GET_MESSAGE_RPODUCER.setAccessible(true);
	}

	public MQProducer(MQTemplate activeMQTemplate) {
		super(activeMQTemplate);
	}

	public void sendQueue(final String queueName, final String message, final Map<String, Object> messageProps) {

		send(queueName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.QUEUE);

	}

	public void sendQueue(final String queueName, final byte[] message, final Map<String, Object> messageProps) {

		send(queueName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.QUEUE);

	}

	public void sendQueue(final String queueName, final String message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig) {

		send(queueName, message, messageProps, messageConfig, DestinationType.QUEUE);
	}

	public void sendQueue(final String queueName, final byte[] message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig) {

		send(queueName, message, messageProps, messageConfig, DestinationType.QUEUE);
	}

	public void sendTopic(final String topicName, final String message, final Map<String, Object> messageProps) {

		send(topicName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.TOPIC);
	}

	public void sendTopic(final String topicName, final byte[] message, final Map<String, Object> messageProps) {

		send(topicName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.TOPIC);
	}

	public void sendTopic(final String topicName, final String message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig) {

		send(topicName, message, messageProps, messageConfig, DestinationType.TOPIC);
	}

	public void sendTopic(final String topicName, final byte[] message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig) {

		send(topicName, message, messageProps, messageConfig, DestinationType.TOPIC);
	}

	/**
	 * 拼接虚拟主题前缀
	 * http://activemq.apache.org/virtual-destinations.html
	 * @param topicName
	 * @return
	 */
	private String getVirtualTopic(String topicName) {
		StringBuilder topicBuild = new StringBuilder(20);
		topicBuild.append(virtualTopicPrefix);
		topicBuild.append(".");
		topicBuild.append(topicName);
		return topicBuild.toString();
	}

	public void sendVirtualTopic(final String topicName, final String message, final Map<String, Object> messageProps) {

		send(getVirtualTopic(topicName), message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.TOPIC);
	}

	public void sendVirtualTopic(final String topicName, final byte[] message, final Map<String, Object> messageProps) {

		send(getVirtualTopic(topicName), message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.TOPIC);
	}

	public void sendVirtualTopic(final String topicName, final String message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig) {

		send(getVirtualTopic(topicName), message, messageProps, messageConfig, DestinationType.TOPIC);
	}

	public void sendVirtualTopic(final String topicName, final byte[] message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig) {

		send(getVirtualTopic(topicName), message, messageProps, messageConfig, DestinationType.TOPIC);
	}

	public void sendAsyncQueue(final String queueName, final String message, final Map<String, Object> messageProps,
			AsyncCallback callback) {
		sendAsync(queueName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.QUEUE, callback);
	}

	public void sendAsyncQueue(final String queueName, final byte[] message, final Map<String, Object> messageProps,
			AsyncCallback callback) {
		sendAsync(queueName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.QUEUE, callback);
	}

	public void sendAsyncQueue(final String queueName, final String message, final Map<String, Object> messageProps,
			MessageConfig messageConfig, AsyncCallback callback) {
		sendAsync(queueName, message, messageProps, messageConfig, DestinationType.QUEUE, callback);
	}

	public void sendAsyncQueue(final String queueName, final byte[] message, final Map<String, Object> messageProps,
			MessageConfig messageConfig, AsyncCallback callback) {
		sendAsync(queueName, message, messageProps, messageConfig, DestinationType.QUEUE, callback);
	}

	public void sendAsyncTopic(final String topicName, final String message, final Map<String, Object> messageProps,
			AsyncCallback callback) {

		sendAsync(topicName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.TOPIC, callback);
	}

	public void sendAsyncTopic(final String topicName, final byte[] message, final Map<String, Object> messageProps,
			AsyncCallback callback) {

		sendAsync(topicName, message, messageProps, MessageConfig.DEFAULT_CONFIG, DestinationType.TOPIC, callback);
	}

	public void sendAsyncTopic(final String topicName, final String message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig, AsyncCallback callback) {

		sendAsync(topicName, message, messageProps, messageConfig, DestinationType.TOPIC, callback);
	}

	public void sendAsyncTopic(final String topicName, final byte[] message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig, AsyncCallback callback) {

		sendAsync(topicName, message, messageProps, messageConfig, DestinationType.TOPIC, callback);
	}

	public void sendAsyncVirtualTopic(final String topicName, final String message,
			final Map<String, Object> messageProps, AsyncCallback callback) {

		sendAsync(getVirtualTopic(topicName), message, messageProps, MessageConfig.DEFAULT_CONFIG,
				DestinationType.TOPIC, callback);
	}

	public void sendAsyncVirtualTopic(final String topicName, final byte[] message,
			final Map<String, Object> messageProps, AsyncCallback callback) {

		sendAsync(getVirtualTopic(topicName), message, messageProps, MessageConfig.DEFAULT_CONFIG,
				DestinationType.TOPIC, callback);
	}

	public void sendAsyncVirtualTopic(final String topicName, final String message,
			final Map<String, Object> messageProps, final MessageConfig messageConfig, AsyncCallback callback) {

		sendAsync(getVirtualTopic(topicName), message, messageProps, messageConfig, DestinationType.TOPIC, callback);
	}

	public void sendAsyncVirtualTopic(final String topicName, final byte[] message,
			final Map<String, Object> messageProps, final MessageConfig messageConfig, AsyncCallback callback) {

		sendAsync(getVirtualTopic(topicName), message, messageProps, messageConfig, DestinationType.TOPIC, callback);
	}

	// 异步发送 msg
	private void sendAsync(final String destinationName, final Object message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig, final DestinationType destinationType, final AsyncCallback callback) {
		activeMQTemplate.execute(new SessionCallback<Void>() {

			public Void doInJms(Session session) throws JMSException {

				Destination destination = null;

				if (destinationType.equals(DestinationType.TOPIC)) {
					destination = session.createTopic(destinationName);
				} else {
					destination = session.createQueue(destinationName);
				}

				Message msgContent = toMessage(message, messageProps, session);
				MessageProducer producer = session.createProducer(destination);

				try {
					PooledProducer pooledProducer = (PooledProducer) producer;

					ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) GET_MESSAGE_RPODUCER
							.invoke(pooledProducer, null);

					synchronized (activeMQMessageProducer) {
						activeMQMessageProducer.send(destination, msgContent, messageConfig.getDeliveryMode(),
								messageConfig.getPriority(), messageConfig.getTimeToLive(), callback);
					}

				} catch (Exception e) {
					producer.send(msgContent, messageConfig.getDeliveryMode(), messageConfig.getPriority(),
							messageConfig.getTimeToLive());
				}

				closeMessageProducer(producer);

				return null;
			}
		});
	}

	// 发送 msg
	private void send(final String destinationName, final Object message, final Map<String, Object> messageProps,
			final MessageConfig messageConfig, final DestinationType destinationType) {
		activeMQTemplate.execute(new SessionCallback<Void>() {

			public Void doInJms(Session session) throws JMSException {

				Destination destination = null;

				if (destinationType.equals(DestinationType.TOPIC)) {
					destination = session.createTopic(destinationName);
				} else {
					destination = session.createQueue(destinationName);
				}

				Message msgContent = toMessage(message, messageProps, session);
				MessageProducer producer = session.createProducer(destination);

				producer.send(msgContent, messageConfig.getDeliveryMode(), messageConfig.getPriority(),
						messageConfig.getTimeToLive());

				closeMessageProducer(producer);

				return null;
			}
		});
	}

	// 关闭messageProducer
	private void closeMessageProducer(MessageProducer producer) {
		if (null != producer) {
			try {
				producer.close();
			} catch (JMSException e) {
				LOG.error("close messageProducer error", e);
			}
		}
	}
}
