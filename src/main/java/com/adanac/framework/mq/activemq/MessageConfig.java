package com.adanac.framework.mq.activemq;

import javax.jms.DeliveryMode;
import javax.jms.Message;

/**
 * 消息配置
 * @author adanac
 * @version 1.0
 */
public class MessageConfig {
	// 默认配置
	public static final MessageConfig DEFAULT_CONFIG = new MessageConfig(DeliveryMode.PERSISTENT,
			Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);

	/**
	 * 消息传输模式 持久化传送和非持久化传输模式
	 * @see DeliveryMode.PERSISTENT 
	 * @see DeliveryMode.NON_PERSISTENT
	 */
	private int deliveryMode;

	/**
	 * 消息级别
	 * @see Message.DEFAULT_PRIORITY
	 */
	private int priority;

	/**
	 * 消息存活时间 单位为毫秒
	 * @see  Message.DEFAULT_TIME_TO_LIVE
	 */
	private long timeToLive;

	public int getDeliveryMode() {
		return deliveryMode;
	}

	/**
	 * 消息传输模式 持久化传送和非持久化传输模式
	 * @see DeliveryMode.PERSISTENT 
	 * @see DeliveryMode.NON_PERSISTENT
	 */
	public void setDeliveryMode(int deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public int getPriority() {
		return priority;
	}

	/**
	 * 消息级别
	 * @see Message.DEFAULT_PRIORITY
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	/**
	 * 消息存活时间 单位为毫秒
	 * @see  Message.DEFAULT_TIME_TO_LIVE
	 */
	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}

	public MessageConfig() {
		this(DeliveryMode.PERSISTENT, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
	}

	public MessageConfig(int deliveryMode, int priority, long timeToLive) {

		this.deliveryMode = deliveryMode;
		this.priority = priority;
		this.timeToLive = timeToLive;
	}
}
