package com.adanac.framework.mq.activemq;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;
import org.apache.activemq.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.SessionCallback;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.adanac.framework.mq.exception.MqException;
import com.adanac.framework.uniconfig.client.UniconfigClient;
import com.adanac.framework.uniconfig.client.UniconfigClientImpl;
import com.adanac.framework.uniconfig.client.UniconfigNode;

/**
 * ActiveMQ 操作模板
 * 
 * @author adanac
 * @version 1.0
 */
public class MQTemplate implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(MQTemplate.class);

	/**
	 * mq配置
	 */
	private MQConfig mqConfig;

	private PooledConnectionFactory pool;

	/**
	 * 通过ActiveMQConfig创建
	 * 
	 * @param mqConfig
	 */
	public MQTemplate(MQConfig mqConfig) {
		this.mqConfig = mqConfig;

	}

	/**
	 * 通过Properties创建
	 * 
	 * @param properties
	 */
	public MQTemplate(Properties properties) {
		this(MQConfig.buildFromProperties(properties));
	}

	/**
	 * 通过统一配置创建
	 * 
	 * @param unconfigNode
	 */
	public MQTemplate(String nodeName) {

		try {
			UniconfigClient uniconfigClient = UniconfigClientImpl.getInstance();

			UniconfigNode uniconfigNode = uniconfigClient.getConfig(nodeName);

			uniconfigNode.sync();

			String nodeValue = uniconfigNode.getValue();

			Assert.hasText(nodeValue, "获取不到配置节点信息");

			Properties properties = new Properties();

			properties.load(new StringReader(nodeValue));

			this.mqConfig = MQConfig.buildFromProperties(properties);
		} catch (Exception e) {
			throw new RuntimeException("创建失败", e);
		}
	}

	/**
	 * 初始化
	 */
	public void init() {
		ActiveMQConnectionFactory connectionFactory = createConnectionFactory();
		this.pool = createPool(connectionFactory);
		this.pool.initConnectionsPool();
	}

	/**
	 * 消费
	 */
	public void destory() {
		this.pool.stop();
	}

	/**
	 * 创建ActiveMQConnectionFactory
	 * 
	 * @return
	 */
	private ActiveMQConnectionFactory createConnectionFactory() {

		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(mqConfig.getBrokerURL());
		activeMQConnectionFactory.setUseAsyncSend(mqConfig.isAsync());// 异步同步发送

		// 用户名和密码
		activeMQConnectionFactory.setUserName(mqConfig.getUserName());
		activeMQConnectionFactory.setPassword(mqConfig.getPassword());

		activeMQConnectionFactory.setTransportListener(new TransportListener() {

			public void transportResumed() {

				LOG.info("transportResumed");

			}

			public void transportInterupted() {

				LOG.info("transportInterupted");

			}

			public void onException(IOException error) {
				LOG.error("onException", error);

			}

			public void onCommand(Object command) {
				LOG.info("onCommand {} ", command);

			}
		});

		if (StringUtils.hasLength(mqConfig.getClientIDPrefix())) {
			activeMQConnectionFactory.setClientIDPrefix(mqConfig.getClientIDPrefix());

		}

		return activeMQConnectionFactory;
	}

	private PooledConnectionFactory createPool(ActiveMQConnectionFactory activeMQConnectionFactory) {
		PooledConnectionFactory pool = new PooledConnectionFactory();

		pool.setConnectionFactory(activeMQConnectionFactory);
		// 设置最大连接数
		pool.setMaxConnections(mqConfig.getMaxConnections());
		// 设置最小
		pool.setMaximumActiveSessionPerConnection(mqConfig.getMaximumActiveSessionPerConnection());

		// 后台对象清理时，休眠时间超过了3000毫秒的对象为过期
		// pool.setTimeBetweenExpirationCheckMillis(3000);
		// pool.setIdleTimeout(60 * 1000);

		return pool;
	}

	/*
	 * public void setMqConfig(MQConfig mqConfig) { this.mqConfig = mqConfig; }
	 */

	public MQConfig getMqConfig() {
		return mqConfig;
	}

	/**
	 * 关闭connection
	 * 
	 * @param connection
	 */
	public void closeConnection(Connection connection) {
		if (null != connection) {
			try {
				connection.close();
			} catch (JMSException e) {
				LOG.error("error when close connection", e);
			}
		}
	}

	/**
	 * 关闭session
	 * 
	 * @param session
	 */
	public void closeSession(Session session) {
		if (null != session) {
			try {
				session.close();
			} catch (JMSException e) {
				LOG.error("error when close session");
			}
		}
	}

	/**
	 * 创建connection
	 * 
	 * @return
	 */
	public Connection getConnection() {
		try {
			Connection connection = pool.createConnection();

			// 重试次数设置

			if (StringUtils.hasLength(mqConfig.getClientID())) {
				connection.setClientID(mqConfig.getClientID());
			}

			connection.start();
			return connection;
		} catch (JMSException e) {
			throw new MqException("get connection error", e);
		}
	}

	/**
	 * 创建session
	 * 
	 * @param connection
	 * @return
	 */
	public Session createSession(Connection connection) {
		try {
			return connection.createSession(mqConfig.isTransacted(), mqConfig.getAcknowledgeMode());
		} catch (JMSException e) {
			throw new MqException("create session error", e);
		}
	}

	public <T> T execute(SessionCallback<T> action) {
		Connection connection = null;
		Session session = null;
		try {
			connection = getConnection();

			session = createSession(connection);
			T result = action.doInJms(session);

			// 如果开始事务
			if (mqConfig.isTransacted()) {
				session.commit();
			}

			return result;
		} catch (JMSException e) {

			if (mqConfig.isTransacted()) {
				try {
					session.rollback();
				} catch (JMSException er) {
					LOG.error("rollback error", er);
				}
			}

			throw new MqException("", e);
		} finally {
			closeSession(session);
			closeConnection(connection);
		}

	}

	public void afterPropertiesSet() throws Exception {
		init();

	}
}
