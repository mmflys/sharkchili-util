package com.shark.util.util;

import com.shark.util.Exception.ProperitesException;

import java.util.Properties;
import java.util.function.Consumer;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/1 0001
 */
public class PropertyUtil {

	/**
	 * Check property is not null
	 * @param properties {@link Properties}
	 * @param key key
	 */
	public static void checkNotNull(Properties properties,String key){
		if (properties.get(key)==null){
			throw new ProperitesException("property %s is null",key);
		}
	}

	/**
	 * Set key if the key value is`t null
	 * @param properties {@link Properties}
	 * @param key key string
	 * @param consumer set value
	 */
	public static void doIfNotNull(Properties properties, String key, Consumer<Object> consumer){
		if (properties.get(key)!=null){
			consumer.accept(properties.get(key));
		}
	}

	/**
	 * Get value mapped to the key
	 * @param properties {@link Properties}
	 * @param key key string
	 * @param defaultValue default value if null
	 * @return  value
	 */
	public static Object getOrDefault(Properties properties, String key,Object defaultValue){
		return properties.get(key)==null?defaultValue:properties.get(key);
	}

	/**
	 * Do something if key value is null with default value and do other thing if not null
	 * @param properties {@link Properties}
	 * @param key key string
	 * @param consumer do some thing
	 * @param defaultValue do if null
	 */
	public static void doIfNullOrDefault(Properties properties, String key, Consumer<Object> consumer, Object defaultValue){
		if (properties.get(key)!=null){
			consumer.accept(properties.getProperty(key));
		}else {
			consumer.accept(defaultValue);
		}
	}
}
