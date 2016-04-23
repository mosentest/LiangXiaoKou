package org.liangxiaokou.module.QRcode;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import org.liangxiaokou.app.SwipeBackActivity;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.module.R;

public class QRcodeActivity extends SwipeBackActivity {

    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        showBack(true);
    }

    @Override
    public void initView() {

        mIv = (ImageView) findViewById(R.id.iv);
        User currentUser = User.getCurrentUser(getApplicationContext(), User.class);
        //设置二维码的参数
        String url = Constants.author + "." + Constants.APP_NAME + "." + currentUser.getObjectId();
        CreateQRImageUtils.createQRImage(url, mIv);
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
