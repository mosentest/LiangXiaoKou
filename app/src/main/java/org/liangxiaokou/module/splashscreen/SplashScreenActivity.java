package org.liangxiaokou.module.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import org.liangxiaokou.app.GeneralActivity;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.home.HomeActivity;
import org.liangxiaokou.module.invite.InviteActivity;
import org.liangxiaokou.module.login.LoginActivity;
import org.liangxiaokou.module.welcome.WelcomeActivity;
import org.mo.netstatus.NetStateReceiver;
import org.mo.netstatus.NetUtils;

import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;

public class SplashScreenActivity extends GeneralActivity {


    private StaticHandler handler;

    private static class StaticHandler extends Handler {
        private final WeakReference<Activity> weakReference;

        public StaticHandler(Activity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private static class SplashHandler implements Runnable {
        private final WeakReference<Activity> weakReference;

        public SplashHandler(Activity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        public void run() {
            Activity activity = weakReference.get();
            if (activity != null) {
                User user = BmobUser.getCurrentUser(activity, User.class);
                if (user != null) {
                    //用户信息不为空
                    if (user.getIsOk() && user.getHaveLove()) {
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    } else if (user.getIsOk() && !user.getHaveLove()) {
                        activity.startActivity(new Intent(activity, InviteActivity.class));
                    } else {
                        activity.startActivity(new Intent(activity, WelcomeActivity.class));
                    }
                } else {
                    //缓存用户对象为空时， 可打开用户注册界面…
                    activity.startActivity(new Intent(activity, LoginActivity.class));
                }
                activity.finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    protected PendingTransitionMode getPendingTransitionMode() {
        return PendingTransitionMode.TOP;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        handler = new StaticHandler(this);
        handler.postDelayed(new SplashHandler(this), 3000);
    }

    @Override
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {

    }

    @Override
    public void PreOnRestart() {

    }

    @Override
    public void PreOnPause() {

    }

    @Override
    public void PreOnStop() {

    }

    @Override
    public void PreOnDestroy() {

    }

    @Override
    public boolean PreOnKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
