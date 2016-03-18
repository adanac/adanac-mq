package com.adanac.framework.mq.activemq;

import java.util.Properties;

import javax.jms.Session;

import com.adanac.framework.mq.util.PropertiesUtil;

/**
 * ActiveMQ 配置
 * @author adanac
 * @version 1.0
 */
public class MQConfig {
	public static final boolean DEFAULT_ASYNC = true;
	private static final long DEFAULT_TIMEOUT = 10000;// 10s
	private static final int DEFAULT_MAXCONNECTIONS = 50;
	private static final int DEFAULT_MAXIMUM_ACTIVESESSIONPERCONNECTION = 50;

	private String userName;// 用户名
	private String password;// 密码
	private String brokerURL;// mq url
	private boolean async = DEFAULT_ASYNC;// 同步异步操作 默认异步
	private long timeOut = DEFAULT_TIMEOUT;
	private int maxConnections = DEFAULT_MAXCONNECTIONS;
	private int maximumActiveSessionPerConnection = DEFAULT_MAXIMUM_ACTIVESESSIONPERCONNECTION;

	/**
	 * 事务
	 */
	private boolean transacted = false;

	/**
	 * 消息确认方式 当设置为Session.CLIENT_ACKNOWLEDGE 需要手动调用 Message的acknowledge方法进行消息确认
	 *@see  Session.AUTO_ACKNOWLEDGE
	 *@see  Session.CLIENT_ACKNOWLEDGE
	 */
	private int acknowledgeMode = Session.AUTO_ACKNOWLEDGE;

	// topic 数据同步 消费者持久化

	/**
	 * 为消息发送方配置一个clientIDPrefix的一个属性，该属性来表示那个消费者可以获得此消息
	 */
	private String clientIDPrefix;

	public String getClientIDPrefix() {
		return clientIDPrefix;
	}

	public void setClientIDPrefix(String clientIDPrefix) {
		this.clientIDPrefix = clientIDPrefix;
	}

	private String clientID;

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	// topic 数据同步 消费者持久化

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBrokerURL() {
		return brokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public int getMaxConnections() {
		return maxConnections;
	}

	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public int getMaximumActiveSessionPerConnection() {
		return maximumActiveSessionPerConnection;
	}

	public void setMaximumActiveSessionPerConnection(int maximumActiveSessionPerConnection) {
		this.maximumActiveSessionPerConnection = maximumActiveSessionPerConnection;
	}

	public int getAcknowledgeMode() {
		return acknowledgeMode;
	}

	public void setAcknowledgeMode(int acknowledgeMode) {
		this.acknowledgeMode = acknowledgeMode;
	}

	public boolean isTransacted() {
		return transacted;
	}

	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}

	public static MQConfig buildFromProperties(Properties properties) {
		MQConfig config = new MQConfig();

		config.setBrokerURL(PropertiesUtil.getStr(properties, "brokerURL"));
		config.setUserName(PropertiesUtil.getStr(properties, "userName", null));
		config.setPassword(PropertiesUtil.getStr(properties, "password", null));
		config.setAcknowledgeMode(PropertiesUtil.getInt(properties, "acknowledgeMode", Session.AUTO_ACKNOWLEDGE));
		config.setAsync(PropertiesUtil.getBoolean(properties, "async", DEFAULT_ASYNC));
		config.setMaxConnections(PropertiesUtil.getInt(properties, "maxConnections", DEFAULT_MAXCONNECTIONS));
		config.setTransacted(PropertiesUtil.getBoolean(properties, "transacted", false));
		config.setTimeOut(PropertiesUtil.getLong(properties, "timeOut", DEFAULT_TIMEOUT));
		config.setMaximumActiveSessionPerConnection(PropertiesUtil.getInt(properties,
				"maximumActiveSessionPerConnection", DEFAULT_MAXIMUM_ACTIVESESSIONPERCONNECTION));

		config.setClientID(PropertiesUtil.getStr(properties, "clientID", null));
		config.setClientIDPrefix(PropertiesUtil.getStr(properties, "clientIDPrefix", null));

		return config;
	}

	public static MQConfig buildFromClassPathResources(String classPathResources) {

		return buildFromProperties(PropertiesUtil.loadFromClassPath(classPathResources));
	}
}
