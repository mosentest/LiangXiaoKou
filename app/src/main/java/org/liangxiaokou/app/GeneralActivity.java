package org.liangxiaokou.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import org.liangxiaokou.util.StatusBarCompat;
import org.liangxiaokou.util.ThirdUtils;

/**
 * Created by Administrator on 2015/12/23.
 */
public abstract class GeneralActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        MApplication.getInstance().getmNewsLifecycleHandler().onActivityStarted(this);
        PreOnStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        //StatusBarCompat.compat(this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreOnResume();
        ThirdUtils.statisticsInActivityResume(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PreOnRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreOnPause();
        MApplication.getInstance().getmNewsLifecycleHandler().onActivityPaused(this);
        ThirdUtils.statisticsInActivityPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MApplication.getInstance().getmNewsLifecycleHandler().onActivityStopped(this);
        PreOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreOnDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (PreOnKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }


    protected abstract void initView();

    protected abstract void initData();

    protected abstract void PreOnStart();

    protected abstract void PreOnResume();

    protected abstract void PreOnRestart();

    protected abstract void PreOnPause();

    protected abstract void PreOnStop();

    protected abstract void PreOnDestroy();

    protected abstract boolean PreOnKeyDown(int keyCode, KeyEvent event);

}
