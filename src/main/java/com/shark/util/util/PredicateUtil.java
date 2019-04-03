package com.shark.util.util;

import java.util.function.Predicate;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/3 0003
 */
public class PredicateUtil {

	/**
	 * Whether t satisfy the all predicate or not
	 * @param t t
	 * @param predicates {@link Predicate}
	 * @param <T> t
	 * @return true if t satisfy all predicate,else false
	 */
	public static <T> boolean test(T t,Predicate<T>...predicates){
		for (Predicate<T> predicate : predicates) {
			if (!predicate.test(t)) return false;
		}
		return true;
	}

}
