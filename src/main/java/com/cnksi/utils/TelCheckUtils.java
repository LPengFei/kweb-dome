package com.cnksi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelCheckUtils {

    public static List<String> allphone(String str){

        List<String> cellphones = checkCellphone(str);
        List<String> checkTelephones = checkTelephone(str);
        cellphones.addAll(checkTelephones);
        return cellphones;
    }

    /**
     * 查询符合的手机号码
     * @param str
     */
    public static  List<String>  checkCellphone(String str){
        // 将给定的正则表达式编译到模式中
        Pattern pattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
        // 创建匹配给定输入与此模式的匹配器。
        Matcher matcher = pattern.matcher(str);

        List<String> phones = new ArrayList<>();

        //查找字符串中是否有符合的子字符串
        while(matcher.find()){
            //查找到符合的即输出
            phones.add(matcher.group());
        }
        return  phones;
    }

    /**
     * 查询符合的固定电话
     * @param str
     */
    public static  List<String>  checkTelephone(String str){
        // 将给定的正则表达式编译到模式中
        Pattern pattern = Pattern.compile("(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)");
        // 创建匹配给定输入与此模式的匹配器。
        Matcher matcher = pattern.matcher(str);

        List<String> phones = new ArrayList<>();

        //查找字符串中是否有符合的子字符串
        while(matcher.find()){
            //查找到符合的即输出
            phones.add(matcher.group());
        }
        return  phones;
    }
}
