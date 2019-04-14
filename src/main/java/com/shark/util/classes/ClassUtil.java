package com.shark.util.classes;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.shark.util.util.LogUtil.LOGGER;

/**
 * class util
 * @Author: SuLiang
 * @Date: 2018/9/7 0007
 */
public class ClassUtil {
	private static final BiMap<Class<?>,Class<?>> BASE_TYPE_MAP= HashBiMap.create();

	static {
		BASE_TYPE_MAP.put(byte.class,Byte.class);
		BASE_TYPE_MAP.put(short.class,Short.class);
		BASE_TYPE_MAP.put(int.class,Integer.class);
		BASE_TYPE_MAP.put(long.class,Long.class);
		BASE_TYPE_MAP.put(float.class,Float.class);
		BASE_TYPE_MAP.put(double.class,Double.class);
		BASE_TYPE_MAP.put(char.class,Character.class);
		BASE_TYPE_MAP.put(boolean.class,Boolean.class);
	}
	/**
	 * Get wrapper type of the class
	 * @param c target class
	 * @return return the wrapper type of base type
	 */
	public static Class<?> getWrapperType(Class<?> c){
		if (c==byte.class) return Byte.class;
		if (c==short.class) return Short.class;
		if (c==int.class) return Integer.class;
		if (c==long.class) return Long.class;
		if (c==float.class) return Float.class;
		if (c==double.class) return Double.class;
		if (c==char.class) return Character.class;
		if (c==boolean.class) return Boolean.class;
		return c;
	}

	/**
	 * Get base type of the class
	 * @param c target class
	 * @return get base type of wrapper type
	 */
	public static Class<?> getBaseType(Class<?> c){
		if (c==Byte.class) return byte.class;
		if (c==Short.class) return short.class;
		if (c==Integer.class) return int.class;
		if (c==Long.class) return long.class;
		if (c==Float.class) return float.class;
		if (c==Double.class) return double.class;
		if (c==Character.class) return char.class;
		if (c==Boolean.class) return boolean.class;
		return c;
	}

	/**
	 * Test weather c is a base type or not
	 * @param c class
	 * @return if class if base type,return true,else return false.
	 */
	public static boolean isBaseType(Class<?> c){
		return c==getBaseType(c);
	}

	/**
	 * Get first generic type of the class
	 * @param c class
	 * @param <T> generic parameter
	 * @return the generic type of the class
	 */
	public static <T> Class<T> getSuperGenericClass(Class c){
		Type type=c.getGenericSuperclass();
		ParameterizedType parameterizedType= (ParameterizedType) type;
		String className= parameterizedType.getActualTypeArguments()[0].getTypeName();
		try {
			return (Class<T>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			LOGGER(ClassUtil.class).error("class: {} not found",className);
		}
		return null;
	}

	/**
	 * Get first generic type of the class
	 * @param c class
	 * @param index index
	 * @param <T> generic parameter
	 * @return the generic type of the class
	 */
	public static <T> Class<T> getSuperGenericClass(Class c,int index){
		Type type=c.getGenericSuperclass();
		ParameterizedType parameterizedType= (ParameterizedType) type;
		String className= parameterizedType.getActualTypeArguments()[index].getTypeName();
		try {
			return (Class<T>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			LOGGER(ClassUtil.class).error("class: {} not found",className);
		}
		return null;
	}

	/**
	 * Get first generic type of the object
	 * @param object object
	 * @param <T> generic parameter
	 * @return the first generic type of the object
	 */
	public static <T> Class<T> getSuperGenericClass(Object object){
		return ClassUtil.getSuperGenericClass(object.getClass());
	}

	/**
	 * Get first generic type of the object
	 * @param object object
	 * @param index index
	 * @param <T> generic parameter
	 * @return the first generic type of the object
	 */
	public static <T> Class<T> getSuperGenericClass(Object object,int index){
		return ClassUtil.getSuperGenericClass(object.getClass(),index);
	}

	/**
	 * Create a new instance with the class
	 * @param c class
	 * @return a instance of the class
	 */
	public static <T> T newInstance(Class c){
		try {
			return (T) c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> Class<T> getGenericClass(Field field){
		Type type=field.getGenericType();
		if (type instanceof ParameterizedType){
			ParameterizedType p= (ParameterizedType) type;
			Class genericClazz = (Class)p.getActualTypeArguments()[0];
			return genericClazz;
		}
		return null;
	}

	public static <T> T getFirstEnum(Class<T> enumClass){
		return enumClass.getEnumConstants()[0];
	}
}
