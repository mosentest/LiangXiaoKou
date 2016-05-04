package org.liangxiaokou.widget.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.Request;

import org.liangxiaokou.app.GeneralActivity;
import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.widget.view.MultiStateView;

/**
 * Created by moziqi on 2015/12/28.
 * TODO 自己修改父类
 */
public class BaseWebActivity extends ToolBarActivity implements ProgressWebView.ChangeTitle {
    protected ProgressWebView mWebView;
    private MultiStateView multiStateView;
    //private Button bnt_refresh_net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mo_activity_base_web);
        showActionBarBack(true);
    }


    @Override
    public void initView() {
        mWebView = (ProgressWebView) findViewById(R.id.baseweb_webview);
        //mWebView.onChange(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        multiStateView = (MultiStateView) findViewById(R.id.multiStateView);
        Button bnt_refresh_net = (Button) findViewById(R.id.btn_refresh_net);
        bnt_refresh_net.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        String baseWebActivity_url = intent.getStringExtra("BaseWebActivity_url");
        mWebView.loadUrl(baseWebActivity_url);
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
    protected void onDestroy() {
        super.onDestroy();
        mWebView.setVisibility(View.GONE);
        mWebView.destroy();
        mWebView = null;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
    public void onChangeTitle(String title) {
    }

    @Override
    public void onClick(View v) {

    }
}
