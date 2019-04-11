package com.shark.util.util;

/**
 * Match util
 * @Author: SuLiang
 * @Date: 2018/10/23 0023
 */
public class MathUtil {

	/**
	 * Calculate sum of n elements.
	 * @param es elements
	 * @return the sum of the elements
	 */
	public static Integer sum(int...es){
		Integer sum=0;
		for (int e : es) {
			sum+=e;
		}
		return sum;
	}

	public static double  log(int base,int x){
		return Math.log(x)/Math.log(base);
	}

}
