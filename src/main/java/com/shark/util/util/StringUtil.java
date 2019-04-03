package com.shark.util.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * String util
 * @Author: SuLiang
 * @Date: 2018/9/27 0027
 */
public class StringUtil {

	/**
	 * Remove prefix or postfix from string. prefix could be null,postfix could be null.
	 * @param origin origin string
	 * @param prefix prefix string
	 * @param postfix postfix string
	 * @return a string that have no the prefix and ths postfix
	 */
	public static String removeFix(String origin,String prefix,String postfix){
		String result=origin;
		if (!isEmpty(prefix)){
			result=origin.substring(prefix.length());
		}
		if (!isEmpty(postfix)){
			result=result.substring(0,result.length()-postfix.length());
		}
		return result;
	}

	/**
	 *  <p>Replace symbol with object orderly.</p>
	 *  <pre>
	 * 	eg: select * from student where id=?,name=?,address=?
	 * 	    select * from student where id=1,name="mike",address="China"
	 * 	</pre>
	 * @param str origin string
	 * @param symbol the symbol that will be replaced.
	 * @param objects objects to replace the symbol.
	 * @return a string the symbol had ben replaced.
	 */
	public static String replaceSymbol(String str, String symbol, Object...objects){
		if (str.contains(symbol)){
			Map<Integer,Integer> indexs=indexOf(str,symbol);
			StringBuilder builder=new StringBuilder();
			if (indexs!=null&&!indexs.isEmpty()){
				int maxKey= Collections.max(indexs.keySet());
				int endIndex = 0;
				for (int i = 1; i <= maxKey&&i-1<objects.length; i++) {
					int lastIndex=indexs.get(i-1)==null?0:indexs.get(i-1);
					int startIndex=lastIndex==0?0:lastIndex+symbol.length();
					endIndex=indexs.get(i);
					builder.append(str, startIndex, endIndex).append(objects[i-1]);
				}
				// 添加上末尾字符
				if (endIndex<str.length()){
					builder.append(str,endIndex+symbol.length(),str.length());
				}
			}
			return builder.toString();
		}else {
			return str;
		}
	}

	/**
	 * Replace str in StringBuilder.
	 * @param builder A not null StringBuilder
	 * @param oldStr the string will be replaced
	 * @param newStr the string will replace the old string
	 * @return return the builder that have no old string.
	 */
	public static StringBuilder replace(StringBuilder builder,String oldStr,String newStr){
		String str=builder.toString().replace(oldStr,newStr);
		return new StringBuilder(str);
	}

	/**
	 * StringBuilder append String continually.
	 * @param builder A StringBuilder
	 * @param strs the strings will be append to the builder.
	 */
	public static void append(StringBuilder builder,String...strs){
		for (String str : strs) {
			builder.append(str);
		}
	}

	/**
	 * Get lower hump class name.
	 * @param object object
	 * @return the string that is lower camel case style of the class represent this object
	 */
	public static String classLowerHump(Object object){
		return firstLowercase(object.getClass().getSimpleName());
	}

	/**
	 * Get lower hump class name.
	 * @param c class
	 * @return the string that is lower camel case style of the class
	 */
	public static String classLowerHump(Class c){
		return firstLowercase(c.getSimpleName());
	}

	/**
	 * lower hump
	 * @param element the strings will be concat
	 * @return a string that is concat with element,and is lower camel case style.
	 */
	public static String lowerHump(String...element){
		StringBuilder hump=new StringBuilder();
		for (int i = 0; i < element.length; i++) {
			if (i==0){
				hump.append(firstLowercase(element[i]));
			}else {
				hump.append(firstUppercase(element[i]));
			}
		}
		return hump.toString();
	}

	/**
	 * Converts the first letter of the string to lowercase
	 *
	 * @param str a String object
	 * @return a string that first letter is lower case
	 */
	public static String firstLowercase(String str) {
		String firstLetter = String.valueOf(str.charAt(0));
		str = str.substring(1);
		str = firstLetter.toLowerCase() + str;
		return str;
	}

	/**
	 * Converts the first letter of a string to write large
	 *
	 * @param str a String object
	 * @return a string first letter is upper case
	 */
	public static String firstUppercase(String str) {
		String firstLetter = String.valueOf(str.charAt(0));
		str = str.substring(1);
		str = firstLetter.toUpperCase() + str;
		return str;
	}

	/**
	 * eg: template: this message %s is not a service message, orgs: obj.
	 * print result: this message obj is not a service message.
	 *
	 * @param str origin string contain placeholder symbol %s
	 * @param args object will replace %s from origin string
	 * @return a string that object replaced placeholder
	 */
	public static String format(String str, Object... args) {
		if (args != null && args.length != 0) {
			str = String.valueOf(str);
			StringBuilder builder = new StringBuilder(str.length() + 16 * args.length);
			int templateStart = 0, i, placeholderStart;
			for (i = 0; i < args.length; templateStart = placeholderStart + 2) {
				placeholderStart = str.indexOf("%s", templateStart);
				if (placeholderStart == -1) {
					placeholderStart = str.indexOf("{}", templateStart);
					if (placeholderStart == -1) {
						break;
					}
				}
				builder.append(str, templateStart, placeholderStart);
				builder.append(args[i++]);
			}

			builder.append(str.substring(templateStart));
			if (i < args.length) {
				builder.append(" [");
				builder.append(args[i++]);
				while (i < args.length) {
					builder.append(", ");
					builder.append(args[i++]);
				}
				builder.append(']');
			}
			return builder.toString();
		} else {
			return str;
		}
	}

	/**
	 * Judge whether str is '\n' or '\n\r' or '\r'
	 *
	 * @param str string
	 * @return if str is a new line char,return true,else false
	 */
	public static boolean isNewLineChar(String str) {
		return str.equals("\r") || str.equals("\n") || str.equals("\n\r");
	}

	/**
	 * Judge whether str is empty or not(include '',' ',null).
	 * @param str string
	 * @return if str==null or str=="" or str==" ",return true,else return false.
	 */
	public static boolean isEmpty(String str) {
		if (str == null) return true;
		else {
			str=str.trim();
			return str.equals("");
		}
	}

	/**
	 * Judge whether str is null or not(include '',null).
	 * @param str string
	 * @return if str==null or str=="",return true
	 */
	public static boolean isNull(String str){
		return str==null||str.equals("");
	}

	/**
	 * Judge whether two ip address are same.
	 * @param ip1 ip address 1
	 * @param ip2 ip address 1
	 * @return if ip1==ip2,return true,else false.And localhost==172.0.0.1,return true.
	 */
	public static boolean compareIp(String ip1,String ip2){
		List<String> localhost=Lists.newArrayList("localhost","172.0.0.1");
		if (localhost.contains(ip1)&&localhost.contains(ip2)) {
			return true;
		} else {
			return ip1.equals(ip2);
		}
	}

	/**
	 * Split string to a list.
	 * data can`t be split between leftBound and rightBound
	 * @param str origin string
	 * @param regex separator
	 * @param leftBound left symbol of bound,eg: "aba,su"
	 * @param rightBound right symbol of bound,eg: "aba,su"
	 * @return a list
	 */
	public List<String> split(String str, String regex, String leftBound, String rightBound) {
		List<String> result = Lists.newArrayList();
		List<String> origin = Arrays.asList(str.split(regex));
		List<Integer> leftCombineIndex = Lists.newArrayList();
		List<Integer> rightCombineIndex = Lists.newArrayList();
		for (int i = 0; i < origin.size(); i++) {
			String doStr = null;
			boolean leftBoundStr = origin.get(i).startsWith(leftBound);
			boolean rightBoundStr = origin.get(i).endsWith(rightBound);
			if (!(leftBoundStr && rightBoundStr)) {
				if (leftBoundStr) {
					leftCombineIndex.add(i);
				} else if (rightBoundStr) {
					rightCombineIndex.add(i);
				}
			}
		}
		if (!leftCombineIndex.isEmpty() || !rightCombineIndex.isEmpty()) {
			List<String> boundStrList = origin.subList(leftCombineIndex.get(0), rightCombineIndex.get(rightCombineIndex.size() - 1) + 1);
			StringBuilder boundStr = new StringBuilder();
			for (String s : boundStrList) {
				if (!boundStr.toString().equals("")) boundStr.append(",");
				boundStr.append(s);
			}
			List left = origin.subList(0, leftCombineIndex.get(0));
			List right = origin.subList(rightCombineIndex.get(rightCombineIndex.size() - 1) + 1, origin.size());
			result.addAll(left);
			result.add(boundStr.toString());
			result.addAll(right);
		} else {
			result = origin;
		}
		List<String> strings = Lists.newArrayList();
		for (String s : result) {
			strings.add(s.replace("\"", ""));
		}
		return strings;
	}

	/**
	 * Find index of str in origin.
	 * @param origin origin string
	 * @param str string that is being find.
	 * @return a map,key is 1(firstly occurrence),2(secondly occurrence)...,value is the left index map to key.
	 */
	public static Map<Integer, Integer> indexOf(String origin, String str){
		Map<Integer,Integer> indexs= Maps.newHashMap();
		int originLength=origin.length();
		int length=str.length();
		if (originLength<length){
			return null;
		}else {
			int count=1;
			for (int i = 0; i <= originLength-length; i++) {
				String subStr=origin.substring(i,i+length);
				if (subStr.equals(str)){
					indexs.put(count++,i);
				}
			}
			return indexs;
		}
	}

	/**
	 * Set local system separator for the path
	 * @param path path
	 * @return path
	 */
	public static String setLocalSeparator(String path){
		char localSeparator= File.separatorChar;
		boolean localSlash=localSeparator=='/';
		boolean localBackSlash=localSeparator=='\\';

		boolean haveSlash=path.contains("/");
		boolean haveBackSlash=path.contains("\\");
		if (!haveBackSlash&&!haveSlash) return path;
		if (haveBackSlash&&haveSlash) return path;

		if (haveSlash&localBackSlash){
			return path.replace('/', '\\');
		}

		if (haveBackSlash&&localSlash){
			return path.replace('\\', '/');
		}
		return path;
	}

	/**
	 * Set separator for the string
	 * @param str string
	 * @param sp separator
	 * @return a string
	 */
	public static String setSeparator(String str,char sp){
		if (sp=='\\'){
			return str.replace('/', sp);
		}
		if (sp=='/') {
			return str.replace('\\', '/');
		}
		return str;
	}

	/**
	 * Get root path of project
	 * @return a string
	 */
	public static String getUserDir(){
		return System.getProperty("user.dir");
	}

	/**
	 * Get class path
	 * @return array of string
	 */
	public static String[] getClassPath(){
		String classPaths = System.getProperty("java.class.path");
		return classPaths.split(";");
	}
}
