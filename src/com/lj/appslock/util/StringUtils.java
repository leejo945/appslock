package com.lj.appslock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * �ַ����������߰�
 */
public class StringUtils {

	/**
	 * �жϸ����ַ����Ƿ�հ״��� �հ״���ָ�ɿո�\s���Ʊ��\t ���س���\n�����з�\r ��ɵ��ַ���
	 * �������ַ���Ϊnull����ַ���������true
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
