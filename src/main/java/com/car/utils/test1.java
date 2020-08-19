package com.car.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test1 {

	/**
	 * String.format渲染模板
	 *
	 * @param template
	 *            模版
	 * @param params
	 *            参数
	 * @return
	 */
	public static String processFormat(String template, Object... params) {
		if (template == null || params == null)
			return null;
		return String.format(template, params);
	}

	/**
	 * MessageFormat渲染模板
	 *
	 * @param template
	 *            模版
	 * @param params
	 *            参数
	 * @return
	 */
	public static String processMessage(String template, Object... params) {
		if (template == null || params == null)
			return null;
		return MessageFormat.format(template, params);
	}

	// 使用：
	public static void main(String[] args) {
		Object[] obj = new Object[] { "张三", "服务员", "5000-6000" };
		System.out.println(processFormat("您好%s，邀请你投递：%s职位，月薪：%s", obj));
		System.out.println(processMessage("您好{0}，邀请你投递：{1}职位，月薪：{2}", obj));
		String str = "0123";
		try {
			int b = Integer.valueOf(str).intValue();

			System.out.println(b);
		} catch (Exception e) {
		}

		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		// BufferedWriter bufw = new BufferedWriter(new
		// OutputStreamWriter(System.out));
		int select = 1;
		System.out.println("深圳市南山区南海大道1079号http://www.gps.com/map.aspx?lat=23.123&lng=113.123");

		String x = "深圳市南山区南海大道1079号  http://www.gps.com/map.aspx?lat=23.123&lng=113.123";

		System.out.println(str2unicode(x).replaceAll("\\\\u", ""));
		System.out.println("6df157335e0253575c71533a53576d7759279053003100300037003953f7002000200068007400740070003a002f002f007700770077002e006700700073002e0063006f006d002f006d00610070002e0061007300700078003f006c00610074003d00320033002e0031003200330026006c006e0067003d003100310033002e003100320033");
		System.out.println(unicodeToString1(str2unicode(x)));


//		TRVBP10
//		6df157335e0253575c71533a53576d7759279053003100300037003953f7002000200068007400740070003a002f002f007700770077002e006700700073002e0063006f006d002f006d00610070002e0061007300700078003f006c00610074003d00320033002e0031003200330026006c006e0067003d003100310033002e003100320033
//		#
	}

	public static String unicodeToString1(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	// 字符(串)==>Unicode编码(串)
	public static String str2unicode(String str) {
		char[] strArr = str.toCharArray();
		String unicode = "", temp = "";
		for (int index = 0; index < strArr.length; ++index) {
			temp = Integer.toHexString(strArr[index]).toUpperCase();
			while (temp.length() - 4 != 0)
				temp = "0" + temp;
			unicode += "\\u" + temp;
		}
		return unicode;
	}

	// Unicode编码(串)==>字符(串)
	public static String unicode2str(String unicodeStr) {
		int beg = 0, end = 0;
		String subStr = "";
		String result = "";
		while (beg != -1) {
			end = unicodeStr.indexOf("\\u", beg + 2);
			subStr = (-1 == end) ? unicodeStr.substring(beg + 2, unicodeStr.length())
					: unicodeStr.substring(beg + 2, end);
			result += String.valueOf((char) Integer.parseInt(subStr, 16));
			beg = end;
		}
		return result;
	}

	/*
	 * 字符串---->16进制数字
	 */
	public static String Str2Hex(String str) {
		String HexStr = "0123456789ABCDEF";
		byte[] bytes = str.getBytes();// 由默认编码获取字节流数组
		StringBuilder sb = new StringBuilder(bytes.length << 1);
		for (int i = 0; i < bytes.length; i++) {
			int head = (bytes[i] & 0xf0) >> 4;// 高四位
			int tail = bytes[i] & 0x0f; // 低四位
			sb.append(HexStr.charAt(head));
			sb.append(HexStr.charAt(tail));
		}
		return sb.toString();
	}

	/*
	 * 16进制数字---->字符串
	 */
	public static String Hex2Str(String bytes) {
		String HexStr = "0123456789ABCDEF";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() >> 1);
		// 每2位16进制整数组成一个字节
		for (int i = 0; i < bytes.length(); i += 2) {
			int cond1 = HexStr.indexOf(bytes.charAt(i)) << 4;
			int cond2 = HexStr.indexOf(bytes.charAt(i + 1));
			baos.write(cond1 | cond2);
		}
		return new String(baos.toByteArray());
	}

	public static String unicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	public static String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			if (ch > 255)
				str += "" + Integer.toHexString(ch);
			else
				str += "" + Integer.toHexString(ch);
		}
		return str;
	}
}
