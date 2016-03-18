package com.adanac.framework.mq.activemq.convert;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.jms.pool.PooledSession;
import org.apache.activemq.jms.pool.SessionHolder;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * @author adanac
 * @version 1.0
 */
public class MsgConverter extends SimpleMessageConverter {
	private static final Method SAFE_GET_SESSION_HOLDER;

	static {
		SAFE_GET_SESSION_HOLDER = ReflectionUtils.findMethod(PooledSession.class, "safeGetSessionHolder");
		SAFE_GET_SESSION_HOLDER.setAccessible(true);
	}

	/**
	 * 设置消息属性
	 * @param message 消息
	 * @param props 消息属性
	 * @throws JMSException
	 */
	private void createMessageProperty(Message message, Map<String, Object> props) throws JMSException {

		if (null == props || props.size() == 0) {
			return;
		}

		for (Map.Entry<String, Object> entry : props.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			message.setObjectProperty(key, value);
		}

	}

	/**
	 * 转换成mq消息
	 * @param object 消息内容
	 * @param props 消息属性
	 * @param session
	 * @return
	 * @throws JMSException
	 * @throws MessageConversionException
	 */
	public Message toMessage(Object object, Map<String, Object> props, Session session)
			throws JMSException, MessageConversionException {
		MessageConversionException exception = null;
		Message message = null;

		if (object instanceof Message) {
			message = (Message) object;
		} else if (object instanceof String) {
			message = createMessageForString((String) object, session);
		} else if (object instanceof byte[]) {
			message = createMessageForByteArray((byte[]) object, session);
		} else if (object instanceof Map) {
			message = createMessageForMap((Map) object, session);
		} else if (object instanceof Serializable) {
			message = createMessageForSerializable(((Serializable) object), session);
		} else if (object instanceof InputStream) {// 文件流的形式

			message = createMessageForInputStream((InputStream) object, session);
		} else {
			exception = new MessageConversionException("Cannot convert object of type ["
					+ ObjectUtils.nullSafeClassName(object) + "] to JMS message. Supported message "
					+ "payloads are: String, byte array, Map<String,?>, Serializable object.");
		}

		if (null != exception) {
			throw exception;
		} else {
			// 设置消息属性
			createMessageProperty(message, props);

			return message;
		}
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {

		if (message instanceof TextMessage) {
			return extractStringFromMessage((TextMessage) message);
		} else if (message instanceof BytesMessage) {
			return extractByteArrayFromMessage((BytesMessage) message);
		} else if (message instanceof MapMessage) {
			return extractMapFromMessage((MapMessage) message);
		} else if (message instanceof ObjectMessage) {
			return extractSerializableFromMessage((ObjectMessage) message);
		} else if (message instanceof BlobMessage) {
			return extractInputStreamFromMessage((BlobMessage) message);
		} else {
			return message;
		}
	}

	private InputStream extractInputStreamFromMessage(BlobMessage message) throws JMSException {

		try {
			return message.getInputStream();
		} catch (IOException e) {
			JMSException jmsExceptionWrap = new JMSException("");
			jmsExceptionWrap.setLinkedException(e);
			throw jmsExceptionWrap;
		} catch (JMSException e) {
			throw e;
		}
	}

	private Message createMessageForInputStream(InputStream object, Session session) throws JMSException {

		try {
			PooledSession pooledSession = (PooledSession) session;
			SessionHolder sessionHolder = (SessionHolder) SAFE_GET_SESSION_HOLDER.invoke(pooledSession, null);

			ActiveMQSession activeMQSession = (ActiveMQSession) sessionHolder.getSession();
			return activeMQSession.createBlobMessage(object);
		} catch (IllegalAccessException e) {
			throw exceptionWrap(e);
		} catch (IllegalArgumentException e) {
			throw exceptionWrap(e);
		} catch (InvocationTargetException e) {
			throw exceptionWrap(e);
		}

	}

	// exception wrap
	private JMSException exceptionWrap(Exception e) {
		JMSException exceptionWrap = new JMSException("");
		exceptionWrap.setLinkedException(e);
		return exceptionWrap;
	}
}
