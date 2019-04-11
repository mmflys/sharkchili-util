package com.shark.util.util;

/**
 * Number util
 * @Author: SuLiang
 * @Date: 2018/10/23 0023
 */
public class NumberUtil {

	/**
	 * Convert Number to Long
	 * @param number number
	 * @return A Long data
	 */
	public static long convertToLong(Number number){
		return number.longValue();
	}

	public static int highestIndex(int number){
		int highestOneBit=Integer.highestOneBit(number);
		return (int) MathUtil.log(2,highestOneBit);
	}
}
