package com.adanac.framework.mq.exception;

/**
 * mq异常
 * @author adanac
 * @version 1.0
 */
public class MqException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7606007798562397035L;

	public MqException() {
	}

	public MqException(String message) {
		super(message);
	}

	public MqException(String message, Throwable cause) {
		super(message, cause);
	}
}
