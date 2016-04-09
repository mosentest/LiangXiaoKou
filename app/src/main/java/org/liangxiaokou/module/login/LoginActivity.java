package org.liangxiaokou.module.login;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import org.liangxiaokou.app.GeneralActivity;
import org.liangxiaokou.app.SwipeBackActivity;
import org.liangxiaokou.module.R;

public class LoginActivity extends GeneralActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        return false;
    }
}
