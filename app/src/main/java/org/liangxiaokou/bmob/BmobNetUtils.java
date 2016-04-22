package org.liangxiaokou.bmob;

import android.content.Context;

import org.liangxiaokou.app.MApplication;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.util.ToastUtils;

import cn.bmob.v3.listener.LogInListener;
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
        user.setNick("");//默认为空
        user.setSex(1);//默认设置为女
        //邮箱验证
        user.setEmailVerified(true);
        user.signUp(context.getApplicationContext(), saveListener);
    }

    /**
     * 登录
     *
     * @param context
     * @param username
     * @param password
     * @param saveListener
     */
    public static void login(Context context, String username, String password, SaveListener saveListener) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context.getApplicationContext(), saveListener);
    }

    /**
     * 登录
     *
     * @param context
     * @param username
     * @param password
     * @param saveListener
     */
    public static void loginByAccount(Context context, String username, String password, LogInListener saveListener) {
        User.loginByAccount(context.getApplicationContext(), username, password, saveListener);
    }
}
