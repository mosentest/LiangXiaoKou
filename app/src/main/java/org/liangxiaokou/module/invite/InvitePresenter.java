package org.liangxiaokou.module.invite;

import android.content.Context;

import org.liangxiaokou.app.MApplication;
import org.liangxiaokou.module.login.LoginActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by moziqi on 16-4-30.
 */
public class InvitePresenter {
    private InviteView inviteView;

    public InvitePresenter(InviteView inviteView) {
        this.inviteView = inviteView;
    }

    public void toLogout(Context context) {
        inviteView.showLoading();
        BmobUser.logOut(context.getApplicationContext());   //清除缓存用户对象
        MApplication.getInstance().AppExit();
        inviteView.hideLoading();
        inviteView.onSuccess(InvitePresenter.class.getName());
    }
}
