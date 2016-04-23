package org.liangxiaokou.module.splashscreen;

import android.os.Bundle;
import android.view.KeyEvent;

import org.liangxiaokou.app.GeneralActivity;
import org.liangxiaokou.module.R;

public class SplashScreenActivity extends GeneralActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

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
}
