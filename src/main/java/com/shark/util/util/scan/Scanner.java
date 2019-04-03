package com.shark.util.util.scan;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Scan file include directory,jar and regular file
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/11/29 0029
 */
public interface Scanner<T> {

	/**
	 * Scan file from the parent url
	 * @param filters filter file
	 * @return a set of file from the url
	 */
	public Set<T> scan(Predicate<T>...filters);

	/**
	 * Search one file represented by the target name from the url
	 * @param targetName target file,named as "Test.class" or "com/shark/Test.class"
	 * @return one file
	 */
	public T search(String targetName);

}
