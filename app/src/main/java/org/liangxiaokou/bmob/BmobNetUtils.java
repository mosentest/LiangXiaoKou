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
     * @param email
     * @param password
     * @param saveListener
     */
    public static void signUp(Context context, String email, String password, SaveListener saveListener) {
        User user = new User();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        //邮箱验证
        user.setEmailVerified(true);
        user.signUp(context.getApplicationContext(), saveListener);
    }

    /**
     * @param context
     * @param phone
     * @param password
     * @param saveListener
     */
    public static void login(Context context, String phone, String password, SaveListener saveListener) {
        User user = new User();
        user.setUsername(phone);
        user.setPassword(password);
        user.login(context.getApplicationContext(), saveListener);
    }
}
