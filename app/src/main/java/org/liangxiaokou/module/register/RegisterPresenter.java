package org.liangxiaokou.module.register;

import android.content.Context;
import android.text.TextUtils;

import org.liangxiaokou.bmob.BmobListener;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.util.ToastUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/20.
 */
public class RegisterPresenter {

    private IRegisterView registerView;

    public RegisterPresenter(IRegisterView registerView) {
        this.registerView = registerView;
    }

    /**
     * 注册
     *
     * @param context
     */
    public void toRegister(Context context) {
        if (TextUtils.isEmpty(registerView.getUsername())) {
            ToastUtils.toast(context.getApplicationContext(), "请输入您的邮箱地址");
            return;
        }
        if (TextUtils.isEmpty(registerView.getPassword())) {
            ToastUtils.toast(context.getApplicationContext(), "请输入您的密码");
            return;
        }
        if (!registerView.getPassword().equals(registerView.getRePassword())) {
            ToastUtils.toast(context.getApplicationContext(), "两次输入的密码不相同");
            return;
        }
        registerView.showLoading();
        BmobNetUtils.signUp(context,
                registerView.getUsername(),
                registerView.getPassword(),
                new BmobListener() {
                    @Override
                    public void onSuccess() {
                        registerView.onSuccess();
                        registerView.hideLoading();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        registerView.onFailure(i, s);
                        registerView.hideLoading();
                    }
                });
    }
}
