package org.liangxiaokou.module.album;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.module.R;
import org.mo.netstatus.NetUtils;

public class AlbumActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        showActionBarBack(true);
    }

    @Override
    public boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    protected PendingTransitionMode getPendingTransitionMode() {
        return PendingTransitionMode.RIGHT;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

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

    @Override
    public void onClick(View v) {

    }
}
