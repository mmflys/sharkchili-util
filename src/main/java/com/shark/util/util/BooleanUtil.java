package com.shark.util.util;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/12 0012
 */
public class BooleanUtil {

	public static boolean allTrue(boolean...booleans){
		for (boolean aBoolean : booleans) {
			if (!aBoolean){
				return false;
			}
		}
		return true;
	}

	public static boolean anyTrue(boolean...booleans){
		for (boolean aBoolean : booleans) {
			if (aBoolean){
				return true;
			}
		}
		return false;
	}


}
