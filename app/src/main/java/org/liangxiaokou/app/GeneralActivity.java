package org.liangxiaokou.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.liangxiaokou.module.R;
import org.liangxiaokou.util.ThirdUtils;
import org.liangxiaokou.util.ToastUtils;
import org.liangxiaokou.util.VolleyLog;
import org.liangxiaokou.widget.dialog.widget.MaterialDialog;
import org.mo.netstatus.NetChangeObserver;
import org.mo.netstatus.NetStateReceiver;
import org.mo.netstatus.NetUtils;

import dmax.dialog.SpotsDialog;

/**
 * Created by Administrator on 2015/12/23.
 */
public abstract class GeneralActivity extends AppCompatActivity implements IActivity {


    protected MaterialDialog materialDialog;//对话框
    protected AlertDialog alertDialog;

    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;


    public enum PendingTransitionMode {
        RIGHT, TOP;
    }

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
        alertDialog = new SpotsDialog(this, R.style.CustomDialog);
        MApplication.getInstance().addActivity(this);

        mNetChangeObserver = new NetChangeObserver() {
            @Override
            public void onNetConnected(NetUtils.NetType type) {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect() {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };
        NetStateReceiver.registerObserver(mNetChangeObserver);
        //NetStateReceiver.registerNetworkStateReceiver(getApplicationContext());
    }

    /**
     * 是否显示返回键
     *
     * @param isShow
     */
    public void showActionBarBack(boolean isShow) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(getResources().getColor(R.color.system_press));
        initView();
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
        //NetStateReceiver.unRegisterNetworkStateReceiver(getApplicationContext());
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        MApplication.getInstance().finishActivity(this);
        PreOnDestroy();
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


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isOverridePendingTransition() && getPendingTransitionMode() != null) {
            switch (getPendingTransitionMode()) {
                case RIGHT:
                    //http://blog.csdn.net/djun100/article/details/14053653
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    break;
                case TOP:
                    //overridePendingTransition(R.anim.in_from_buttom, R.anim.out_from_top);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (isOverridePendingTransition() && getPendingTransitionMode() != null) {
            switch (getPendingTransitionMode()) {
                case RIGHT:
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                    //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    break;
                case TOP:
                    //overridePendingTransition(R.anim.in_from_buttom, R.anim.out_from_top);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (isOverridePendingTransition() && getPendingTransitionMode() != null) {
            switch (getPendingTransitionMode()) {
                case RIGHT:
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
                    //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                    break;
                case TOP:
                    //overridePendingTransition(R.anim.in_from_buttom, R.anim.out_from_top);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
            }
        }
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public abstract boolean isOverridePendingTransition();

    protected abstract PendingTransitionMode getPendingTransitionMode();


    /**
     * network connected
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

    @Override
    public void onBackPressed() {
        finish();
        //super.onBackPressed();
    }
}
