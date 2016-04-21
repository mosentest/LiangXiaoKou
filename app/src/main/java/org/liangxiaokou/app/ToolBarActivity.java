package org.liangxiaokou.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import org.liangxiaokou.module.R;
import org.liangxiaokou.util.ThirdUtils;
import org.liangxiaokou.util.ToastUtils;
import org.liangxiaokou.widget.dialog.widget.MaterialDialog;

import dmax.dialog.SpotsDialog;


/**
 * 由于使用了ToolBar一些属性，暂时不是用这个,目前由SwipeBackActivity来处理
 * Created by moziqi on 2015/12/9.
 */
@Deprecated
public abstract class ToolBarActivity extends AppCompatActivity implements IActivity {
    private ToolBarHelper mToolBarHelper;
    protected Toolbar toolbar;
    protected MaterialDialog materialDialog;//对话框
    protected AlertDialog alertDialog;

    @Override
    protected void onStart() {
        super.onStart();
        PreOnStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialDialog = new MaterialDialog(this);
        alertDialog = new SpotsDialog(this, R.style.CustomDialog);
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
        ThirdUtils.statisticsInActivityPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreOnDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {
        //super.setContentView(layoutResID);
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        /*自定义的一些操作*/
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        initView();
        initData();
    }

    public void showBack(boolean isShow) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(isShow);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (PreOnKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showToast(String msg) {
        ToastUtils.toast(getApplicationContext(), msg);
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

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    protected void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }
}
