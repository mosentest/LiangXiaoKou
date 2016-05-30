package org.liangxiaokou.module.register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.liangxiaokou.app.GeneralActivity;
import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.util.KeyBoardUtils;
import org.mo.netstatus.NetUtils;

import java.lang.ref.WeakReference;

public class RegisterActivity extends ToolBarActivity implements IRegisterView {

    private LinearLayout mLlRoot;
    private ScrollView mSvRoot;
    private TextInputLayout mTextInputUsername;
    private TextInputLayout mTextInputPassword;
    private TextInputLayout mTextInputRepassword;
    private Button mBtnRegister;

    private StaticHandler handler;

    private static class StaticHandler extends Handler {
        private final WeakReference<Activity> reference;

        public StaticHandler(Activity activity) {
            this.reference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }


    private RegisterPresenter registerPresenter = new RegisterPresenter(this);

    @Override
    public String getUsername() {
        return mTextInputUsername.getEditText().getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mTextInputPassword.getEditText().getText().toString().trim();
    }

    @Override
    public String getRePassword() {
        return mTextInputRepassword.getEditText().getText().toString().trim();
    }

    @Override
    public void showLoading() {
        alertDialog.show();
    }

    @Override
    public void hideLoading() {
        alertDialog.dismiss();
    }

    @Override
    public void onSuccess() {
        showToast("注册成功");
        finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(RegisterActivity.class.getName() + " code is " + code + " and msg is " + msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        showActionBarBack(true);
        handler = new StaticHandler(this);
    }

    @Override
    public boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    protected PendingTransitionMode getPendingTransitionMode() {
        return PendingTransitionMode.TOP;
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void initView() {
        getWindow().setBackgroundDrawable(null);
        mLlRoot = (LinearLayout) findViewById(R.id.ll_root);
        mSvRoot = (ScrollView) findViewById(R.id.sv_root);
        mTextInputUsername = (TextInputLayout) findViewById(R.id.textInput_username);
        mTextInputPassword = (TextInputLayout) findViewById(R.id.textInput_password);
        mTextInputRepassword = (TextInputLayout) findViewById(R.id.textInput_repassword);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
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
        KeyBoardUtils.closeKeybord(mTextInputUsername.getEditText(), getApplicationContext());
    }

    @Override
    public void PreOnDestroy() {
        KeyBoardUtils.closeKeybord(mTextInputUsername.getEditText(), getApplicationContext());
    }

    @Override
    public boolean PreOnKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register: {
                KeyBoardUtils.closeKeybord(mTextInputRepassword.getEditText(), getApplicationContext());
                registerPresenter.toRegister(this);
            }
            break;
        }
    }


}
