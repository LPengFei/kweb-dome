package com.cnksi.kcore.utils;

import com.jfinal.kit.StrKit;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.regex.Pattern;

public class KStrKit extends StrKit {

	private KStrKit() {

	}

	public static boolean isNumber(String s){
		Pattern pattern = Pattern.compile("^\\d+\\.?\\d+$");
		return pattern.matcher(s).matches();
	}

	/**
	 * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
	 * 
	 * @param strSrc
	 *            要加密的字符串
	 * @return
	 */
	public static String Encrypt(String strSrc) {
		return Encrypt(strSrc, "SHA-256");
	}

	public static String Encrypt(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "SHA-256";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 把下划线的命名变成驼峰命名方式
	 * 
	 * @param fildeName
	 * @return
	 */
	public static String toCamel(String fildeName) {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	public static String toStr(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String toStr(Object obj, String defualtStr) {
		return obj == null ? defualtStr : obj.toString();
	}
	
	public static String trimEmpty(Object obj) {
		return toStr(obj).trim();
	}

	/**
	 * 判断是否是强密码
	 * 
	 * @param pwd
	 *            密码字符串
	 * @return
	 */
	public static boolean isStrongPwd(String pwd) {
		// 默认至少6位密码
		return isStrongPwd(pwd, 6);
	}

	// 密码必须包含字母和数据
	public static boolean isStrongPwd(String pwd, int passwordLength) {
		if (pwd.length() >= passwordLength && pwd.matches("(?i)^((\\d+[\\da-z]*[a-z]+)|([a-z]+[\\da-z]*\\d+)|([a-z]+[\\da-z]*[a-z]*)|(\\d+[\\da-z]*\\d*))$")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 网页前端对密码进行sha-256加密，数据库在存储用户时采用sha-256加密存储
	 *
	 * @param args
	 */
	public static void main(String args[]) {
//		String s = Encrypt("admin", "MD5");
//		System.out.println(s);
//
//		s = Encrypt("admin", "SHA-1");
//		System.out.println(s);
//
//		s = Encrypt("admin", "SHA-256");
//		System.out.println(s);

		System.out.println(isNumber("123456.989"));

	}

	/** 
	 * @Title: uuid 
	 * @param @return    设定文件 
	 * @return Object    返回类型 
	 * @throws 
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}


	/**
	 * 多字符拼接，以逗号分隔；禁止null
	 *
	 * @param str
	 * @return
	 */
	public static String join(String... str) {
		String tmp = String.join(",", str);
		tmp = tmp.replace("，", ",").replaceAll("[,]+", ",");//中文逗号转英文逗号  多个转单个
		//去除首尾
		while (tmp.startsWith(",")) {
			tmp = tmp.substring(1, tmp.length());
		}
		while (tmp.endsWith(",")) {
			tmp = tmp.substring(0, tmp.length() - 1);
		}
		return tmp;
	}

	/**
	 * 字符替换，将多个重复字符替换成一个
	 * 注：该方法只适用于常用字符替换
	 *
	 * @param base
	 * @param s
	 * @return
	 */
	public static String replaceDupe(String base, String s) {
		return base.replace("[" + s + "]+", s);
	}

	/**
	 * 去除字符串首尾的多余字符；
	 * @param base
	 * @param s
	 * @return
	 */
	public static String trimStr(String base, String s) {
		String tmp = base;
		while (tmp.startsWith(s)) {
			tmp = tmp.substring(1);
		}
		while (tmp.endsWith(s)) {
			tmp = tmp.substring(0, tmp.length() - 1);
		}
		return tmp;
	}


	/**
	 * 判断字符串是否全为空，若其中一个不为空则返回false
	 *
	 * @param strs
	 * @return
	 */
	public static boolean isAllBlank(String... strs) {
		boolean result = true;
		for (String s : strs) {
			result = result && StrKit.isBlank(s);
			if (result == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断所有字符串是否有部分不为空，若有不为空则返回true
	 *
	 * @param strs
	 * @return
	 */
	public static boolean notAllBlank(String... strs) {
		boolean result = false;
		for (String s : strs) {
			result = result || StrKit.notBlank(s);
			if (result == true) {
				return true;
			}
		}
		return true;
	}

	/**
	 * 是否含有中文字符
	 * @return
	 * @param str
	 */
	public static boolean hasChinese(String str){
		return str.chars().anyMatch(value -> isChinese((char) value));
	}
	/**
	 * 判断所有字符串是否都不为空,若有一个为空，返回false
	 *
	 * @param strs
	 * @return
	 */
	public static boolean allNotBlank(String... strs) {
		return notBlank(strs);
	}


	/**
	 *  根据Unicode编码完美的判断中文汉字和符号
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}
}
