package com.adanac.framework.mq.activemq.producer;

import java.util.Map;

import org.apache.activemq.AsyncCallback;

import com.adanac.framework.mq.activemq.MessageConfig;

/**
 * message producer
 * @author adanac
 * @version 1.0
 */
public interface Producer {
	/**
	 * 发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 */
	void sendQueue(String queueName, String message, Map<String, Object> messageProps);

	/**
	 * 发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 */
	void sendQueue(String queueName, byte[] message, Map<String, Object> messageProps);

	/**
	 * 发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 */
	void sendQueue(String queueName, String message, Map<String, Object> messageProps, MessageConfig messageConfig);

	/**
	 * 发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 */
	void sendQueue(String queueName, byte[] message, Map<String, Object> messageProps, MessageConfig messageConfig);

	/**
	 * 发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 */
	void sendTopic(String topicName, String message, Map<String, Object> messageProps);

	/**
	 * 发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 */
	void sendTopic(String topicName, byte[] message, Map<String, Object> messageProps);

	/**
	 * 发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 */
	void sendTopic(String topicName, String message, Map<String, Object> messageProps, MessageConfig messageConfig);

	/**
	 * 发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 */
	void sendTopic(String topicName, byte[] message, Map<String, Object> messageProps, MessageConfig messageConfig);

	/**
	 * 发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 */
	void sendVirtualTopic(String topicName, String message, Map<String, Object> messageProps);

	/**
	 * 发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 */
	void sendVirtualTopic(String topicName, byte[] message, Map<String, Object> messageProps);

	/**
	 * 发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 */
	void sendVirtualTopic(String topicName, String message, Map<String, Object> messageProps,
			MessageConfig messageConfig);

	/**
	 * 发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 */
	void sendVirtualTopic(String topicName, byte[] message, Map<String, Object> messageProps,
			MessageConfig messageConfig);

	/**
	 * 异步发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param callback callback
	 */
	void sendAsyncQueue(String queueName, String message, Map<String, Object> messageProps, AsyncCallback callback);

	/**
	 * 异步发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param callback callback
	 */
	void sendAsyncQueue(String queueName, byte[] message, Map<String, Object> messageProps, AsyncCallback callback);

	/**
	 * 异步发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 * @param callback  callback
	 */
	void sendAsyncQueue(String queueName, String message, Map<String, Object> messageProps, MessageConfig messageConfig,
			AsyncCallback callback);

	/**
	 * 异步发送queue消息
	 * @param queueName queue名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 * @param callback callback
	 */
	void sendAsyncQueue(String queueName, byte[] message, Map<String, Object> messageProps, MessageConfig messageConfig,
			AsyncCallback callback);

	/**
	 * 异步发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param callback callback
	 */
	void sendAsyncTopic(String topicName, String message, Map<String, Object> messageProps, AsyncCallback callback);

	/**
	 * 异步发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param callback callback
	 */
	void sendAsyncTopic(String topicName, byte[] message, Map<String, Object> messageProps, AsyncCallback callback);

	/**
	 * 异步发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 * @param callback callback
	 */
	void sendAsyncTopic(String topicName, String message, Map<String, Object> messageProps, MessageConfig messageConfig,
			AsyncCallback callback);

	/**
	 * 异步发送topic消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 * @param callback callback
	 */
	void sendAsyncTopic(String topicName, byte[] message, Map<String, Object> messageProps, MessageConfig messageConfig,
			AsyncCallback callback);

	/**
	 * 异步发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param callback callback
	 */
	void sendAsyncVirtualTopic(String topicName, String message, Map<String, Object> messageProps,
			AsyncCallback callback);

	/**
	 * 异步发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param callback callback
	 */
	void sendAsyncVirtualTopic(String topicName, byte[] message, Map<String, Object> messageProps,
			AsyncCallback callback);

	/**
	 * 异步发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 * @param callback callback
	 */
	void sendAsyncVirtualTopic(String topicName, String message, Map<String, Object> messageProps,
			MessageConfig messageConfig, AsyncCallback callback);

	/**
	 * 异步发送虚拟主题消息
	 * @param topicName topic名称
	 * @param message 消息内容
	 * @param messageProps 消息属性
	 * @param messageConfig 消息配置
	 * @param callback callback
	 */
	void sendAsyncVirtualTopic(String topicName, byte[] message, Map<String, Object> messageProps,
			MessageConfig messageConfig, AsyncCallback callback);
}
