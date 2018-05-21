package com.cnksi.sec;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by xyl on 2017/11/22, 022.
 */
public class XssEncode {

    /**
     * 特殊字符串处理
     */
    private static Map<Character, Character> charMap = new HashMap<>();
    static {
        charMap.put('>', '＞');
        charMap.put('<', '＜');
        charMap.put('(', '（');
        charMap.put(')', '）');
        charMap.put('\'', '‘');
        charMap.put('&', '＆');
        charMap.put('\\', '＼');
        charMap.put('/', '／');
        charMap.put('#', '＃');
//        charMap.put('.', '．');

        charMap.put('\"', '“');
        charMap.put(';', '；');
//        charMap.put(',', '，');
        charMap.put('@', '＠');

    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        //空字符串，数字无需处理
        if (str == null || str.isEmpty() || StringUtils.isNumericSpace(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str.length() + 16);
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            Character charFromMap = charMap.get(aChar);
            if (charFromMap == null){
                sb.append(aChar);
            }else{
                sb.append(charFromMap);
            }
        }

        //匹配js、sql关键字，不区分大小写
        Pattern pattern = Pattern.compile("script|alert|function|insert|into|values|drop|where|0x0d|0x0a", Pattern.CASE_INSENSITIVE);
        str = pattern.matcher(sb.toString()).replaceAll("");

        return str;
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
