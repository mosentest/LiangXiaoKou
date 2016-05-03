package org.liangxiaokou.app;

import android.support.v7.widget.Toolbar;

import org.liangxiaokou.module.R;


/**
 * 由于使用了ToolBar一些属性，暂时不是用这个,目前由SwipeBackActivity来处理
 * Created by moziqi on 2015/12/9.
 */
@Deprecated
public abstract class ToolBarActivity extends GeneralActivity implements IActivity {
    protected Toolbar toolbar;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
