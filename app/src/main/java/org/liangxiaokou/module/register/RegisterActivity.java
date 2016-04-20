package org.liangxiaokou.module.register;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.module.R;

import java.lang.ref.WeakReference;

public class RegisterActivity extends ToolBarActivity {

    private LinearLayout mLlRoot;
    private ScrollView mSvRoot;
    private TextInputLayout mTextInputUsername;
    private TextInputLayout mTextInputPassword;
    private TextInputLayout mTextInputRepassword;
    private TextInputLayout mTextInputEmail;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        showBack(true);
        handler = new StaticHandler(this);
    }

    @Override
    public void initView() {
        mLlRoot = (LinearLayout) findViewById(R.id.ll_root);
        mSvRoot = (ScrollView) findViewById(R.id.sv_root);
        mTextInputUsername = (TextInputLayout) findViewById(R.id.textInput_username);
        mTextInputPassword = (TextInputLayout) findViewById(R.id.textInput_password);
        mTextInputRepassword = (TextInputLayout) findViewById(R.id.textInput_repassword);
        mTextInputEmail = (TextInputLayout) findViewById(R.id.textInput_email);
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
        switch (v.getId()) {
            case R.id.btn_register: {

            }
            break;
        }
    }
}
