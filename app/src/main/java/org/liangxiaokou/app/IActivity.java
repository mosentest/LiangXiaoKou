package org.liangxiaokou.app;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by moziqi on 2015/12/25.
 */
public interface IActivity  extends View.OnClickListener{

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
