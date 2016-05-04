package org.liangxiaokou.app;

import android.support.v7.widget.Toolbar;

import org.liangxiaokou.module.R;


public abstract class ToolBarActivity extends GeneralActivity {

    protected Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
