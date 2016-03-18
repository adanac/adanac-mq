package com.adanac.framework.mq.activemq.consumer;

import com.adanac.framework.mq.activemq.MsgListener;

/**
 * message consumer
 * @author adanac
 * @version 1.0
 */
public interface Consumer {
	/**
	 * 监听queue
	 * @param queueName queue名称
	 * @param listener listener
	 */
	void listenQueue(String queueName, MsgListener listener);

	/**
	 * 监听queue
	 * @param queueName queue名称
	 * @param messageSelector 消息选择器
	 * @param listener listener
	 */
	void listenQueue(String queueName, String messageSelector, MsgListener listener);

	/**
	 * 监听虚拟主题
	 * @param topicName topic名称
	 * @param consumerName consumerName
	 * @param listener listener
	 */
	void listenVirtualTopic(String topicName, String consumerName, MsgListener listener);

	/**
	 * 监听虚拟主题
	 * @param topicName topic名称
	 * @param consumerName consumerName
	 * @param messageSelector 消息选择器
	 * @param listener listener
	 */
	void listenVirtualTopic(String topicName, String consumerName, String messageSelector, MsgListener listener);

	/**
	 * 监听topic
	 * @param topicName topic名称
	 * @param listener listener
	 */
	void listenTopic(String topicName, MsgListener listener);

	/**
	 * 监听topic
	 * @param topicName topic名称
	 * @param messageSelector 消息选择器
	 * @param listener listener
	 */
	void listenTopic(String topicName, String messageSelector, MsgListener listener);

	/**
	 * 监听DurableTopic
	 * @param topicName topic名称
	 * @param subscriberName
	 * @param listener
	 */
	void listenDurableTopic(String topicName, String subscriberName, MsgListener listener);

	/**
	 * 监听DurableTopic
	 * @param topicName topic名称
	 * @param subscriberName
	 * @param messageSelector 消息选择器
	 * @param listener
	 */
	void listenDurableTopic(String topicName, String subscriberName, String messageSelector, MsgListener listener);

	/**
	 * get queue message
	 * @param queueName queue名称
	 * @param listener
	 */
	void getQueueMsg(String queueName, MsgListener listener);

	/**
	 * get queue message
	 * @param queueName queue名称
	 * @param messageSelector 消息选择
	 * @param listener
	 */
	void getQueueMsg(String queueName, String messageSelector, MsgListener listener);

	/**
	 * get DurableTopic message
	 * @param topicName
	 * @param subscriberName
	 * @param listener
	 */
	void getDurableTopicMsg(String topicName, String subscriberName, MsgListener listener);

	/**
	 * get  DurableTopic message
	 * @param topicName
	 * @param subscriberName
	 * @param messageSelector
	 * @param listener
	 */
	void getDurableTopicMsg(String topicName, String subscriberName, String messageSelector, MsgListener listener);

	/**
	 * get VirtualTopic message
	 * @param topicName topic名称
	 * @param consumerName
	 * @param listener
	 */
	void getVirtualTopicMsg(String topicName, String consumerName, MsgListener listener);

	/**
	 * get VirtualTopic message
	 * @param topicName topic名称
	 * @param consumerName
	 * @param messageSelector 消息选择器
	 * @param listener
	 */
	void getVirtualTopicMsg(String topicName, String consumerName, String messageSelector, MsgListener listener);

}
