package org.liangxiaokou.bmob;

import org.liangxiaokou.app.MApplication;
import org.liangxiaokou.bean.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by moziqi on 16-4-9.
 */
public class BmobNetUtils {


    /**
     * 注册用户
     *
     * @param phone
     * @param password
     * @param email
     * @param sex
     * @param bitch
     * @param saveListener
     */
    public static void signUp(String phone, String password, String email, Boolean sex, String bitch, SaveListener saveListener) {
        User user = new User();
        user.setUsername(phone);
        //user.setMobilePhoneNumber(phone);
        //手机验证
        //user.setMobilePhoneNumberVerified(true);
        user.setPassword(password);
        user.setEmail(email);
        //邮箱验证
        //user.setEmailVerified(true);
        user.setSex(sex);
        user.setBitch(bitch);
        user.signUp(MApplication.getInstance(), saveListener);
    }
}
