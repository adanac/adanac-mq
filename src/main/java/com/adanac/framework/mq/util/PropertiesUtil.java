package com.adanac.framework.mq.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

/**
 * Properties 工具
 * @author adanac
 * @version 1.0
 */
public class PropertiesUtil {
	/**
	 * 加载classPath下的配置文件 properties
	 * @param classPath
	 * @return
	 */
	public static Properties loadFromClassPath(String classPath) {
		Resource classPathResource = new ClassPathResource(classPath);
		InputStream in = null;

		try {
			in = classPathResource.getInputStream();
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {

				}
			}
		}

	}

	public static String getStr(Properties properties, String key, String defaultValue) {
		String propertyValue = getStr(properties, key);

		return StringUtils.hasText(propertyValue) ? propertyValue : defaultValue;
	}

	public static String getStr(Properties properties, String key) {
		return properties.getProperty(key);
	}

	public static int getInt(Properties properties, String key, int defaultValue) {
		String propertyValue = getStr(properties, key);

		return StringUtils.hasText(propertyValue) ? Integer.parseInt(propertyValue) : defaultValue;
	}

	public static boolean getBoolean(Properties properties, String key, boolean defaultValue) {
		String propertyValue = getStr(properties, key);

		return StringUtils.hasText(propertyValue) ? Boolean.valueOf(propertyValue) : defaultValue;
	}

	public static long getLong(Properties properties, String key, long defaultValue) {
		String propertyValue = getStr(properties, key);

		return StringUtils.hasText(propertyValue) ? Long.parseLong(propertyValue) : defaultValue;
	}
}
