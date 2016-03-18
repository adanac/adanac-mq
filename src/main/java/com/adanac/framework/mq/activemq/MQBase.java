package com.adanac.framework.mq.activemq;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;

import com.adanac.framework.mq.activemq.convert.MsgConverter;

/**
 * 
 * @author adanac
 * @version 1.0
 */
public abstract class MQBase {
	private static final String DEFAULT_VIRTUAL_TOPIC_PREFIX = "VirtualTopic";// 默认虚拟主题前缀
	private static final String DEFAULT_VIRTUAL_TOPIC_CONSUMER_PREFIX = "Consumer";// 默认虚拟主题消费者前缀

	/**
	 * 虚拟主题前缀
	 */
	public String virtualTopicPrefix = DEFAULT_VIRTUAL_TOPIC_PREFIX;

	/**
	 * 虚拟主题消费者前缀
	 */
	public String virtualTopicConsumerPrefix = DEFAULT_VIRTUAL_TOPIC_CONSUMER_PREFIX;

	/**
	 * 消息类型 topic queue
	 */
	public enum DestinationType {
		TOPIC, QUEUE
	}

	/**
	 * 消息转换
	 */
	private MsgConverter msgConverter = new MsgConverter();

	public MQTemplate activeMQTemplate;

	protected MQBase(MQTemplate activeMQTemplate) {
		this.activeMQTemplate = activeMQTemplate;
	}

	public Message toMessage(Object message, Map<String, Object> props, Session session)
			throws MessageConversionException, JMSException {
		return getMsgConverter().toMessage(message, props, session);
	}

	public Object fromMessage(Message message) throws MessageConversionException, JMSException {
		return getMsgConverter().fromMessage(message);
	}

	public MsgConverter getMsgConverter() {
		return msgConverter;
	}

	public void setMsgConverter(MsgConverter msgConverter) {
		this.msgConverter = msgConverter;
	}

	/**
	 * 设置虚拟主题前缀
	 * @param virtualTopicPrefix
	 */
	public void setVirtualTopicPrefix(String virtualTopicPrefix) {
		this.virtualTopicPrefix = virtualTopicPrefix;
	}

	/**
	 * 设置虚拟主题消费者前缀
	 * @param virtualTopicConsumerPrefix
	 */
	public void setVirtualTopicConsumerPrefix(String virtualTopicConsumerPrefix) {
		this.virtualTopicConsumerPrefix = virtualTopicConsumerPrefix;
	}
}
