package org.liangxiaokou.module.splashscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yalantis.starwars.interfaces.TilesFrameLayoutListener;

import org.liangxiaokou.bean.User;
import org.liangxiaokou.module.home.HomeActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.module.invite.InviteActivity;
import org.liangxiaokou.module.login.ILoginView;
import org.liangxiaokou.module.login.LoginActivity;
import org.liangxiaokou.module.welcome.WelcomeActivity;

import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class SplashScreenActivityFragment extends GeneralFragment implements TilesFrameLayoutListener {

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
                        activity.startActivity(new Intent(activity, HomeActivity.class));
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

    public SplashScreenActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_splash_screen, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyLoad();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        handler = new StaticHandler(getActivity());
        handler.postDelayed(new SplashHandler(getActivity()), 3000);
    }

    @Override
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {
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
    protected void lazyLoad() {
//        if (!isPrepared || !isVisible) {
//            return;
//        }
        initView();
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onAnimationFinished() {
    }

}
