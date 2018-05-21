package com.cnksi.utils;

/**
 * 将阿拉伯数字转换为对应的中文<br/>
 * 1, 一
 */
public class NumberToZH {
    public static final String[] ZH = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    /**
     * 罗马字母
     */
    public static final String[] romanChars = "Ⅰ,Ⅱ,Ⅲ,Ⅳ,Ⅴ,Ⅵ,Ⅶ,Ⅷ,Ⅸ".split(",");
    static final String[] digits = {"十", "百", "千", "万"};

    /**
     * 将十位数阿拉伯数字转换为对应的中文
     *
     * @param num
     * @return
     */
    public static String convert(int num) {
        if (num <= 0 || num > 99999) {
            return "";
        }

        String numStr = String.valueOf(num);
        //		System.out.println(numStr);
        StringBuffer sb = new StringBuffer();

        int len = numStr.length();
        if (len == 1) {//个位数
            sb.append(ZH[num]);
        } else if (len == 2) {//十位数
            String digitStr = digits[0];
            if (numStr.startsWith("1")) {
                sb.append(digitStr);
            } else {
                sb.append(ZH[num / 10]).append(digitStr);
            }
            if (!numStr.endsWith("0")) {
                sb.append(ZH[num % 10]);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(convert(11));
    }
}
