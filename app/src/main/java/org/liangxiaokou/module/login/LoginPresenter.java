package org.liangxiaokou.module.login;

import android.content.Context;
import android.text.TextUtils;

import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.util.ToastUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/20.
 */
public class LoginPresenter {

    public ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
    }


    public void toLogin(Context context) {
        if (TextUtils.isEmpty(loginView.getUsername())) {
            ToastUtils.toast(context.getApplicationContext(), "请输入您的邮箱地址");
            return;
        }
        if (TextUtils.isEmpty(loginView.getPassword())) {
            ToastUtils.toast(context.getApplicationContext(), "请输入您的密码");
            return;
        }
        loginView.showLoading();
        BmobNetUtils.login(context, loginView.getUsername(), loginView.getPassword(), new SaveListener() {
            @Override
            public void onSuccess() {
                loginView.onSuccess();
                loginView.hideLoading();
            }

            @Override
            public void onFailure(int i, String s) {
                loginView.onFailure(i, s);
                loginView.hideLoading();
            }
        });
    }
}
