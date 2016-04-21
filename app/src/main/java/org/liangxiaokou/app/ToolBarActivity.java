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
public abstract class ToolBarActivity extends GeneralActivity implements IActivity {
    private ToolBarHelper mToolBarHelper;
    protected Toolbar toolbar;


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

}
