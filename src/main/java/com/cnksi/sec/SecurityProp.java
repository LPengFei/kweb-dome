package com.cnksi.sec;

import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.jfinal.kit.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全校验常量配置
 * Created by xyl on 2017/9/5, 005.
 */
public class SecurityProp {

    public static Prop prop = PropKit.getProp();//PropKit.use("sec.txt");
    public static Boolean captcha;
    public static Boolean errorLoginEnable;
    public static Integer errLoginNumber;
    public static Integer errLoginLockTime;

    /**
     * 文件访问白名单过滤
     */
    public static String accessFileWhitelistStr;

    /**
     * 文件上传白名单过滤
     */
    public static String uploadFileWhitelistStr;

    public static String accessIp;
    public static int changePwdTime; //单位：月

    /**
     * 启动一段时间未登录，必须修改密码
     */
    public static boolean changePwdTimeEnabled;
    /**
     * 一定时间内未登录人员，锁定(默认3个月)
     */
    public static int lockUnloginUserTime;

    /**
     * 最大登录人数，默认200
     */
    public static int maxLoginUserNum;

    /**
     * session过期时间
     */
    public static int sessionTime;

    static {
        init(prop);
    }

    public static void init(Prop prop){
        SecurityProp.prop = prop;
        captcha = prop.getBoolean("captcha", false);
        errorLoginEnable = prop.getBoolean("error.login.enable", true);
        errLoginNumber = getInt("error.login.number", 3);
        errLoginLockTime = getInt("error.login.lock.time", 15);
        accessFileWhitelistStr = prop.get("access.file.whitelist", "");
        uploadFileWhitelistStr = prop.get("upload.file.whitelist", uploadFileWhitelistStr);
        accessIp = prop.get("access.ip", "");
        changePwdTime = getInt("change.pwd.time", 3);
        changePwdTimeEnabled = prop.getBoolean("change.pwd.time.enabled", true);
        lockUnloginUserTime = getInt("lock.unlogin.user.time", 3);
        maxLoginUserNum = getInt("max.login.user.num", 200);
        sessionTime = getInt("session.time", 30);

    }

    private static Integer getInt(String name, Integer defualtVal){
        Integer result = TypeUtils.castToInt(prop.get(name));
        return result == null ? defualtVal : result;
    }

    /**
     * 更新配置文件
     */
    public static boolean updatePropFile(String namePara, String valuePara){

        String value = StringUtils.defaultString(valuePara, "");
        String name = namePara.trim().replace("_", ".");

        //验证输入
        validSecValue(name, value);

        Preconditions.checkArgument(!name.contains(".enable"), "不能修改" + name);
        Preconditions.checkArgument(prop.containsKey(name), "修改的配置不存在(请不要修改键)");

        //首先判断prop中存在的值与当前修改值，如果相同，无需更新
        String valueInProp = prop.get(name);
        if (StringUtils.equals(valueInProp, valuePara)) return true;

        setProp(name, value);

        Path path = Paths.get(PathKit.getRootClassPath(), "sec.txt");
        try {
            List<String> lines = Files.readAllLines(path, Charsets.UTF_8);

            //修改name匹配行的值
            lines = lines.stream()
                         .map(line -> {
                             if (StrKit.notBlank(line) && line.trim().matches(name + "\\s?=.*"))
                                 line = String.join(" = ", name, value);
                             return line;
                         }).collect(Collectors.toList());

            Files.write(path, lines, Charsets.UTF_8);

        } catch (IOException e) {
            LogKit.error("读取配置文件" + path.getFileName() + "失败", e);
            return false;
        }
        return true;
    }

    /**
     * 校验安全设置
     * @return
     * @param value
     */
    public static void validSecValue(String key, String value){
        HashMap<String, String> dataTypeMap = Maps.newHashMap();
        dataTypeMap.put("upload.file.whitelist", "string");
        dataTypeMap.put("access.ip", "string");
        dataTypeMap.put("login.time", "string");
        dataTypeMap.put("access.file.whitelist", "string");

        dataTypeMap.put("error.login.lock.time", "int");
        dataTypeMap.put("error.login.number", "int");
        dataTypeMap.put("change.pwd.time", "int");
        dataTypeMap.put("session.time", "int");
        dataTypeMap.put("max.login.user.num", "int");

        dataTypeMap.put("change.pwd.time.enabled", "bool");
        dataTypeMap.put("error.login.enable", "bool");

        String dataType = dataTypeMap.get(key);
        if ("int".equals(dataType)) {
            Preconditions.checkArgument(StringUtils.isNumeric(value) && TypeUtils.castToDouble(value) >= 0, "(值) 只能输入数字");
            int intVal = TypeUtils.castToInt(value);
            switch (key) {
                case "session.time":
                    Preconditions.checkArgument(intBetween(intVal, 1, 30), "会话过期时间设置 1-30 之间的数字");
                    break;
                case "change.pwd.time":
                    Preconditions.checkArgument(intBetween(intVal, 1, 3), "用户口令有效期时间设置为1-3个月");
                    break;
                case "error.login.number":
                    //错误登陆次数验证，次数只能是1-20位，锁定时间>=10
                    Preconditions.checkArgument(intBetween(intVal, 1, 20), "错误次数在1-20之间");
                    break;
                case "error.login.lock.time":
                    //错误登陆次数验证，次数只能是1-20位，锁定时间>=10
                    Preconditions.checkArgument(intVal >= 10, "锁定时间大于等于10分钟");
                    break;
                case "max.login.user.num":
                    //错误登陆次数验证，次数只能是1-20位，锁定时间>=10
                    Preconditions.checkArgument(intVal > 0, "同时最大在线用户数应大于0");
                    break;
            }
        } else if ("bool".equals(dataType)) {
            Preconditions.checkArgument("true".equals(value) || "false".equals(value), "(值) 只能输入 true 或 false");
        } else if ("string".equals(dataType)) {

        }

        if ("login.time".equals(key)){
            Preconditions.checkArgument(value.matches("\\d{2}:\\d{2} - \\d{2}:\\d{2}"), "时间格式不正确，正确格式：08:00 - 22:00");
        }


    }

    /**
     * 判断int数值是否在某区间内
     * @param compare
     * @param min
     * @param max
     * @return
     */
    static boolean intBetween(int compare, int min, int max){
        return compare >= min && compare <= max;
    }

    public static void setProp(String name, String value){
        prop.getProperties().setProperty(name, value);
        init(prop);
    }

}
