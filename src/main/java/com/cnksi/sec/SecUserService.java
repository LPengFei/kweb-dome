package com.cnksi.sec;

import com.cnksi.kconf.model.User;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class SecUserService {
    public static final SecUserService me = new SecUserService();

    /**
     * 校验 密码 8-20位，包含字母、数字
     *
     * @param password
     */
    public boolean validPassword(String password, String username) {
        System.out.println("password: ".concat(password));
        if (StrKit.notBlank(username) && password.contains(username)) throw new IncorrectCredentialsException("密码中不能包含帐号");

        boolean valid = password.length() >= 8;
        if (valid == false) {
            throw new IncorrectCredentialsException("密码大于等于8位，需同时包含大小字母、数字或特殊字符");
        }


        List<Pattern> patternList = Lists.newArrayList();
        patternList.add(Pattern.compile("[0-9]+"));
        patternList.add(Pattern.compile("[a-z]+"));
        patternList.add(Pattern.compile("[A-Z]+"));
        patternList.add(Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+"));

        int successCount = 0;
        for (Pattern pattern : patternList) {
            if (pattern.matcher(password).find()) {
                successCount++;
            }
        }
        //密码应同时包含三种以上规则
        valid = successCount >= 3;

        if (valid == false) {
            throw new IncorrectCredentialsException("密码大于等于8位，需同时包含大小字母、数字或特殊字符");
        }

        return valid;
    }

    public void encodePassword(User user, String password) {
        if (password == null) password = user.getStr("upwd");
        validPassword(password, user.getStr("uaccount"));
        user.set("upwd", encodePassword(password));
    }

    public String encodePassword(String password) {
        password = HashKit.md5(password);
        //二次加盐加密
//        password = HashKit.md5(password + "ksi");
        return password;
    }

    /**
     * 默认密码设置为：0000aaaa++
     *
     * @param user
     */
//    public String setDefaultPassword(User user) {
//        String pwd = "0000aaaa++";
//        user.set("upwd", encodePassword(pwd));
//        return pwd;
//    }

    /**
     * 设置重置密码
     * @param user
     */
    public String setResetPassword(User user){
        Preconditions.checkNotNull(user.get("uaccount"), "uname_pinyin is null");

        String pwd = user.getStr("uaccount") + "*";
        encodePassword(user, pwd);
        return pwd;
    }

    /**
     * 判断 是否是初始密码
     *
     * @return
     */
    public boolean isDefaultPassword(User user) {
        return encodePassword("0000aaaa++").equals(user.get("upwd")) || encodePassword(user.getStr("uaccount") + "*").equals(user.get("upwd"));
    }

    /**
     * 返回用户是否因为登录错误锁定
     * @param user
     * @return
     */
    public boolean isLoginLocked(User user){
        // 未开启用户错误登录锁定功能
        if (SecurityProp.errorLoginEnable == false) return false;

        Integer errorLoginCount = user.getInt("err_login_count");
        errorLoginCount = errorLoginCount == null ? 0 : errorLoginCount;
        Date errorLoginTime = user.getDate("err_login_time");

        // 判断错误次数是否达到3次，如果达到3次，说明用户已锁定，
        // 再判断错误时间是否已过15分钟, 如果未过，不能登录
        if (errorLoginCount >= SecurityProp.errLoginNumber && errorLoginTime != null) {
            // 将锁定时间+锁定截止时间 再与当前时间对比
            Date endDate = DateUtils.addMinutes(errorLoginTime, SecurityProp.errLoginLockTime);
            if (new Date().after(endDate)) {
                // 锁定时间已过，可以登录，清除锁定次数和锁定时间
                return false;
            } else {
                // 锁定时间未过，不能登录
                return true;
//                throw new LockedAccountException("用户已锁定，请" + SecurityProp.errLoginLockTime + "分钟后登录");
            }
        }

        return false;
    }

    /**
     * 解锁用户
     */
    public boolean unlock(User user) {
        return user.set("err_login_count", 0).set("err_login_time", null).set("status", "normal").update();
    }

    public boolean lock(User user) {
//        LoginUserManager.getNonNull(getPkVal().toString()).set("status", "lock");
        return user.set("status", "lock").update();
    }

}
