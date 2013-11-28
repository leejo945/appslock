package com.lj.appslock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 */
public class StringUtils {

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格\s、制表符\t 、回车符\n、换行符\r 组成的字符串
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input)){
			return true;
		}
//		for (int i = 0,len = input.length(); i < len; i++) {
//			char c = input.charAt(i);
//			 
//			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
//				return false;
//			}
//		}
		return false;
	}

}
