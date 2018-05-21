package com.cnksi.kcore.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.log4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zf on 2017/3/23.
 */
public class PinyinUtil {
	private static Logger logger = Logger.getLogger(PinyinUtil.class.getName());

	/**
	 * Description : 根据汉字获得此汉字的拼音
	 *
	 * @param hanzhis
	 * @return
	 *
	 */
	public static String getPinYin(String hanzhis) {
		return getPinYin(hanzhis, false);
	}

	/**
	 * Description : 根据汉字获得此汉字的拼音首字母
	 *
	 * @param hanzhis
	 * @return
	 *
	 */
	public static String getPinYinHeadChar(String hanzhis) {
		return getPinYin(hanzhis, true);
	}

	/**
	 * 获取第一个汉字的全拼+其他汉字的首字母
	 * 
	 * @param username
	 * @return
	 */
	public static String getAccountPinyin(String username) {
		StringBuffer accountBuffer = new StringBuffer();
		accountBuffer.append(PinyinUtil.getPinYin(username.substring(0, 1)).toLowerCase());
		for (int i = 1; i < username.length(); i++) {
			accountBuffer.append(PinyinUtil.getPinYinHeadChar(username.substring(i, i + 1)).toLowerCase());
		}
		// System.out.println(accountBuffer.toString());
		return accountBuffer.toString();
	}

	private static String getPinYin(String hanzhis, boolean isHeadChar) {
		int len = hanzhis.length();
		char[] hanzhi = hanzhis.toCharArray();

		// 设置输出格式
		HanyuPinyinOutputFormat formatParam = new HanyuPinyinOutputFormat();
		formatParam.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		formatParam.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		formatParam.setVCharType(HanyuPinyinVCharType.WITH_V);

		StringBuilder py = new StringBuilder();
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5]+");
		for (int i = 0; i < len; i++) {
			char c = hanzhi[i];
			Matcher matcher = pattern.matcher(String.valueOf(c));
			// 检查是否是汉字,如果不是汉字就不转换
			if (!matcher.matches()) {
				py.append(c);
				continue;
			}
			// 对汉字进行转换成拼音
			try {
				String[] t2 = PinyinHelper.toHanyuPinyinStringArray(c, formatParam);
				if (isHeadChar) {
					py.append(t2[0].charAt(0));
				} else {
					py.append(t2[0]);
				}

			} catch (BadHanyuPinyinOutputFormatCombination e) {
				logger.error(c + " to pinyin error!", e);
				py.append(c);
			}
		}

		return py.toString();
	}

	public static void main(String[] args) {
		String test = "景志斌";

		System.out.println(getAccountPinyin(test));
	}
}
