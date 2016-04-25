package org.liangxiaokou.module.QRcode;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.zxing.Result;

import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.module.R;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends ToolBarActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mZXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        showBack(true);
    }

    @Override
    public void initView() {
        mZXingScannerView = (ZXingScannerView) findViewById(R.id.zXingScannerView);

    }

    @Override
    public void initData() {

    }

    @Override
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {
        mZXingScannerView.setResultHandler(this);
        mZXingScannerView.startCamera();
    }

    @Override
    public void PreOnRestart() {

    }

    @Override
    public void PreOnPause() {
        mZXingScannerView.stopCamera();
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

    @Override
    public void handleResult(Result result) {
        showToast(result.getText());
        //检查result.getText是否app所设定的数据
        //如果是其他的，做对应的处理

        //If you would like to resume scanning, call this method below:
        mZXingScannerView.resumeCameraPreview(this);
    }
}
