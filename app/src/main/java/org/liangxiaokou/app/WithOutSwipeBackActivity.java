package org.liangxiaokou.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import org.liangxiaokou.module.R;
import org.liangxiaokou.util.StatusBarCompat;
import org.liangxiaokou.util.ThirdUtils;

/**
 * Created by Eric on 15/3/3.
 */
public abstract class WithOutSwipeBackActivity extends AppCompatActivity implements IActivity {

    protected Toolbar toolbar;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this);
        initView();
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PreOnRestart();
    }


    public void onResume() {
        super.onResume();
        PreOnResume();
        ThirdUtils.statisticsInActivityResume(this);
    }

    public void onPause() {
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);

    }
}
