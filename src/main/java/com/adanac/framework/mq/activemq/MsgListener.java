package com.adanac.framework.mq.activemq;

import javax.jms.Message;

/**
 * 消息 listener
 * @author adanac
 * @version 1.0
 */
public interface MsgListener {
	/**
	 * 接收到消息
	 * @param orginMsg activemq原始消息
	 * @param msgContent 转换过的消息内容
	 */
	void onMsg(Message orginMsg, Object msgContent);
}
