package org.liangxiaokou.bmob;

import android.content.Context;

import org.liangxiaokou.app.MApplication;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.util.ToastUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by moziqi on 16-4-9.
 */
public class BmobNetUtils {


    /**
     * 注册用户
     *
     * @param context
     * @param phone
     * @param password
     * @param rePassword
     * @param email
     * @param saveListener
     */
    public static void signUp(Context context, String phone, String password, String rePassword, String email, SaveListener saveListener) {
        User user = new User();
        user.setUsername(phone);
        //user.setMobilePhoneNumber(phone);
        //手机验证
        //user.setMobilePhoneNumberVerified(true);
        user.setPassword(password);
        user.setEmail(email);
        //邮箱验证
        //user.setEmailVerified(true);
//        user.setSex(sex);
//        user.setBitch(bitch);
        if (!password.equals(rePassword)) {
            ToastUtils.toast(context.getApplicationContext(), "两次输入的密码不相同");
            return;
        }
        user.signUp(context.getApplicationContext(), saveListener);
    }
}
