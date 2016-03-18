package com.adanac.framework.mq.activemq.consumer;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.SessionCallback;
import org.springframework.util.StringUtils;

import com.adanac.framework.mq.activemq.MQBase;
import com.adanac.framework.mq.activemq.MQTemplate;
import com.adanac.framework.mq.activemq.MsgListener;
import com.adanac.framework.mq.exception.MqException;

public class MQConsumer extends MQBase implements Consumer {
	private static final Logger LOG = LoggerFactory.getLogger(MQConsumer.class);

	public MQConsumer(MQTemplate activeMQTemplate) {
		super(activeMQTemplate);

	}

	/**
	 * 监听 queue
	 * 
	 * @param queueName queue name
	 * @param listener 监听器
	 */
	public void listenQueue(final String queueName, final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.subscriberName = null;
		consumerConfig.messageSelector = null;

		listen(queueName, listener, DestinationType.QUEUE, consumerConfig);
	}

	/**
	 * 拼接虚拟主题queue name
	 *  http://activemq.apache.org/virtual-destinations.html
	 * @return
	 */
	private String getVirtualTopicQueueName(String topicName, String consumerName) {
		StringBuilder builder = new StringBuilder(30);
		builder.append(virtualTopicConsumerPrefix);
		builder.append(".");
		builder.append(consumerName);
		builder.append(".");
		builder.append(virtualTopicPrefix);
		builder.append(".");
		builder.append(topicName);
		return builder.toString();

	}

	/**
	 * 监听 虚拟主题
	 * 
	 * @param topicName topic name
	 * @param consumerName
	 * @param listener 监听器
	 */
	public void listenVirtualTopic(final String topicName, final String consumerName, final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.subscriberName = null;
		consumerConfig.messageSelector = null;

		listen(getVirtualTopicQueueName(topicName, consumerName), listener, DestinationType.QUEUE, consumerConfig);
	}

	public void getVirtualTopicMsg(final String topicName, final String consumerName, final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.subscriberName = null;
		consumerConfig.messageSelector = null;
		getDestinationContent(getVirtualTopicQueueName(topicName, consumerName), listener, DestinationType.QUEUE,
				consumerConfig);

	}

	public void getVirtualTopicMsg(final String topicName, final String consumerName, String messageSelector,
			final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.subscriberName = null;
		consumerConfig.messageSelector = messageSelector;
		getDestinationContent(getVirtualTopicQueueName(topicName, consumerName), listener, DestinationType.QUEUE,
				consumerConfig);
	}

	/**
	 * 监听 虚拟主题
	 * 
	 * @param topicName topic name
	 * @param consumerName
	 * @param listener 监听器
	 */
	public void listenVirtualTopic(final String topicName, final String consumerName, String messageSelector,
			final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.subscriberName = null;
		consumerConfig.messageSelector = messageSelector;

		listen(getVirtualTopicQueueName(topicName, consumerName), listener, DestinationType.QUEUE, consumerConfig);
	}

	/**
	 * 监听 queue
	 * 
	 * @param queueName queue name
	 * @param listener 监听器
	 * @param messageSelector 消息选择器 http://activemq.apache.org/selectors.html
	 */
	public void listenQueue(final String queueName, String messageSelector, final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.subscriberName = null;
		consumerConfig.messageSelector = messageSelector;

		listen(queueName, listener, DestinationType.QUEUE, consumerConfig);
	}

	/**
	 * 监听 topic
	 * @param topicName topic name
	 * @param listener 监听器
	 */
	public void listenTopic(final String topicName, final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.messageSelector = null;
		consumerConfig.subscriberName = null;

		listen(topicName, listener, DestinationType.TOPIC, consumerConfig);

	}

	/**
	 * 监听 topic
	 * @param topicName topic name
	 * @param listener 监听器
	 * @param messageSelector 消息选择 http://activemq.apache.org/selectors.html
	 */
	public void listenTopic(final String topicName, final String messageSelector, final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = false;
		consumerConfig.subscriberName = null;
		consumerConfig.messageSelector = messageSelector;

		listen(topicName, listener, DestinationType.TOPIC, consumerConfig);

	}

	/**
	 * 监听 DurableTopic
	 * @param topicName
	 * @param listener
	 * @param subscriberName
	 */
	public void listenDurableTopic(final String topicName, final String subscriberName, final MsgListener listener) {

		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = true;
		consumerConfig.subscriberName = subscriberName;
		consumerConfig.messageSelector = null;

		listen(topicName, listener, DestinationType.TOPIC, consumerConfig);
	}

	/**
	 * 监听 DurableTopic
	 * @param topicName topic name
	 * @param listener 监听器
	 * @param subscriberName 订阅者名称
	 * @param messageSelector 消息选择 过滤消息 http://activemq.apache.org/selectors.html
	 */
	public void listenDurableTopic(final String topicName, String subscriberName, String messageSelector,
			final MsgListener listener) {
		MessageConsumerConfig consumerConfig = new MessageConsumerConfig();

		consumerConfig.durableSubscriber = true;
		consumerConfig.subscriberName = subscriberName;
		consumerConfig.messageSelector = messageSelector;

		listen(topicName, listener, DestinationType.TOPIC, consumerConfig);
	}

	private void listen(final String destinationName, final MsgListener listener, final DestinationType destinationType,
			final MessageConsumerConfig messageConsumerConfig) {

		Connection connection = null;
		Session seesion = null;
		Destination destination = null;
		MessageConsumer consumer = null;

		try {

			connection = activeMQTemplate.getConnection();

			seesion = activeMQTemplate.createSession(connection);

			if (destinationType.equals(DestinationType.TOPIC)) {
				destination = seesion.createTopic(destinationName);

			} else {
				destination = seesion.createQueue(destinationName);
			}

			consumer = createMessageConsumer(seesion, destination, messageConsumerConfig);

		} catch (JMSException e) {
			throw new MqException("add listen error", e);
		}

		try {
			final Session seesionForTransaction = seesion;
			consumer.setMessageListener(new MessageListener() {

				public void onMessage(Message message) {

					Object receiveMsgContent = null;
					try {
						receiveMsgContent = fromMessage(message);
					} catch (JMSException e) {
						LOG.error("topic message 转换出错", e);
					}

					try {
						listener.onMsg(message, receiveMsgContent);

						if (activeMQTemplate.getMqConfig().isTransacted()) {
							seesionForTransaction.commit();
						}
					} catch (Exception e) {
						LOG.error("", e);

						if (activeMQTemplate.getMqConfig().isTransacted()) {
							try {
								seesionForTransaction.rollback();
							} catch (JMSException rollbackE) {
								LOG.error("出错 rollback", e);
							}
						}
					}

				}
			});

		} catch (JMSException e) {
			LOG.error("", e);
		}

	}

	public void getQueueMsg(final String queueName, final MsgListener listener) {
		MessageConsumerConfig messageConsumerConfig = new MessageConsumerConfig();
		messageConsumerConfig.durableSubscriber = false;
		messageConsumerConfig.subscriberName = null;
		messageConsumerConfig.messageSelector = null;

		getDestinationContent(queueName, listener, DestinationType.QUEUE, messageConsumerConfig);
	}

	public void getQueueMsg(final String queueName, final String messageSelector, final MsgListener listener) {

		MessageConsumerConfig messageConsumerConfig = new MessageConsumerConfig();
		messageConsumerConfig.durableSubscriber = false;
		messageConsumerConfig.subscriberName = null;
		messageConsumerConfig.messageSelector = messageSelector;

		getDestinationContent(queueName, listener, DestinationType.QUEUE, messageConsumerConfig);
	}

	public void getDurableTopicMsg(final String topicName, final String subscriberName, final MsgListener listener) {
		MessageConsumerConfig messageConsumerConfig = new MessageConsumerConfig();
		messageConsumerConfig.durableSubscriber = true;
		messageConsumerConfig.subscriberName = subscriberName;
		messageConsumerConfig.messageSelector = null;

		getDestinationContent(topicName, listener, DestinationType.TOPIC, messageConsumerConfig);
	}

	public void getDurableTopicMsg(final String topicName, final String subscriberName, final String messageSelector,
			final MsgListener listener) {

		MessageConsumerConfig messageConsumerConfig = new MessageConsumerConfig();
		messageConsumerConfig.durableSubscriber = true;
		messageConsumerConfig.subscriberName = subscriberName;
		messageConsumerConfig.messageSelector = messageSelector;

		getDestinationContent(topicName, listener, DestinationType.TOPIC, messageConsumerConfig);
	}

	private void getDestinationContent(final String destinationName, final MsgListener listener,
			final DestinationType destinationType, final MessageConsumerConfig messageConsumerConfig) {
		activeMQTemplate.execute(new SessionCallback<Void>() {

			public Void doInJms(Session session) throws JMSException {

				Destination destination = null;

				if (destinationType.equals(DestinationType.TOPIC)) {
					destination = session.createTopic(destinationName);
				} else {
					destination = session.createQueue(destinationName);
				}

				MessageConsumer consumer = null;

				consumer = createMessageConsumer(session, destination, messageConsumerConfig);

				Message recMsg = consumer.receive(activeMQTemplate.getMqConfig().getTimeOut());
				Object receiveMsgContent = null;
				try {
					receiveMsgContent = fromMessage(recMsg);
				} catch (JMSException e) {
					LOG.error("msg converter error", e);
				}

				listener.onMsg(recMsg, receiveMsgContent);

				closeMessageConsumer(consumer);
				return null;
			}
		});
	}

	/**
	 * 创建MessageConsumer
	 * @return
	 * @throws JMSException 
	 */
	private MessageConsumer createMessageConsumer(Session seesion, Destination destination,
			MessageConsumerConfig consumerConfig) throws JMSException {
		MessageConsumer consumer = null;

		if (consumerConfig.durableSubscriber) {
			// 持久化topic

			if (StringUtils.hasText(consumerConfig.messageSelector)) {
				consumer = seesion.createDurableSubscriber((Topic) destination, consumerConfig.subscriberName,
						consumerConfig.messageSelector, false);
			} else {
				consumer = seesion.createDurableSubscriber((Topic) destination, consumerConfig.subscriberName);
			}

			// 持久化topic

		} else {
			if (StringUtils.hasText(consumerConfig.messageSelector)) {
				consumer = seesion.createConsumer(destination, consumerConfig.messageSelector);
			} else {
				consumer = seesion.createConsumer(destination);
			}

		}

		return consumer;
	}

	/**
	 * 关闭 consumer
	 * @param consumer
	 */
	private void closeMessageConsumer(MessageConsumer consumer) {
		if (null != consumer) {
			try {
				consumer.close();
			} catch (JMSException e) {
				LOG.error("close MessageConsumer error", e);
			}
		}
	}

	/**
	 * MessageConsumer config
	 * @author chucun
	 *
	 */
	private static class MessageConsumerConfig {

		private boolean durableSubscriber;// 是否是持久化topic订阅
		private String subscriberName;// 订阅者名称
		private String messageSelector;// 消息选择

	}
}
