package org.liangxiaokou.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import org.liangxiaokou.module.R;
import org.liangxiaokou.util.StatusBarCompat;
import org.liangxiaokou.util.ThirdUtils;
import org.liangxiaokou.widget.dialog.listener.OnBtnClickL;
import org.liangxiaokou.widget.dialog.widget.MaterialDialog;

import dmax.dialog.SpotsDialog;

/**
 * Created by Administrator on 2015/12/23.
 */
public abstract class GeneralActivity extends AppCompatActivity {


    protected MaterialDialog materialDialog;//对话框
    protected AlertDialog alertDialog;

    @Override
    protected void onStart() {
        MApplication.getInstance().getmNewsLifecycleHandler().onActivityStarted(this);
        PreOnStart();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialDialog = new MaterialDialog(this);
//        materialDialog.setOnBtnClickL(new OnBtnClickL() {
//            @Override
//            public void onBtnClick() {
//
//            }
//        }, new OnBtnClickL() {
//            @Override
//            public void onBtnClick() {
//
//            }
//        }, new OnBtnClickL() {
//            @Override
//            public void onBtnClick() {
//
//            }
//        });
        alertDialog = new SpotsDialog(this, R.style.CustomDialog);
        MApplication.getInstance().addActivity(this);
    }

    public void showBack(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
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
        ThirdUtils.statisticsInActivityResume(this);
        PreOnResume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        PreOnRestart();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        MApplication.getInstance().getmNewsLifecycleHandler().onActivityPaused(this);
        ThirdUtils.statisticsInActivityPause(this);
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.hide();
        }
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.hide();
        }
        PreOnPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        MApplication.getInstance().getmNewsLifecycleHandler().onActivityStopped(this);
        PreOnStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        alertDialog = null;
        materialDialog = null;
        PreOnDestroy();
        MApplication.getInstance().finishActivity(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (PreOnKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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


    public abstract void initView();

    public abstract void initData();

    public abstract void PreOnStart();

    public abstract void PreOnResume();

    public abstract void PreOnRestart();

    public abstract void PreOnPause();

    public abstract void PreOnStop();

    public abstract void PreOnDestroy();

    public abstract boolean PreOnKeyDown(int keyCode, KeyEvent event);

}
