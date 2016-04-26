package org.liangxiaokou.module.welcome;

import android.content.Context;
import android.text.TextUtils;

import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.util.ToastUtils;

import java.util.List;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/4/23.
 */
public class WelcomePresenter {


    private IWelcomeView iWelcomeView;

    private int sex = -1;//设置性别（0男，1女）

    public WelcomePresenter(IWelcomeView iWelcomeView) {
        this.iWelcomeView = iWelcomeView;
    }

    public void toMain(Context context) {
        if (getSex() == -1) {
            ToastUtils.toast(context.getApplicationContext(), "请选择您的性别");
            return;
        }
        if (TextUtils.isEmpty(iWelcomeView.getNick())) {
            ToastUtils.toast(context.getApplicationContext(), "请输入您的昵称");
            return;
        }
        iWelcomeView.showLoading();
        User currentUser = User.getCurrentUser(context.getApplicationContext(), User.class);
        User newUser = new User();
        newUser.setSex(getSex());
        newUser.setNick(iWelcomeView.getNick());
        newUser.setIsOk(true);
        BmobNetUtils.updateUserInfo(context.getApplicationContext(), newUser, currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                iWelcomeView.onSuccess();
                iWelcomeView.hideLoading();
            }

            @Override
            public void onFailure(int i, String s) {
                iWelcomeView.onFailure(i, s);
                iWelcomeView.hideLoading();
            }
        });
    }

    private int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
