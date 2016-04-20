package org.liangxiaokou.module.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.home.HomeActivity;
import org.liangxiaokou.module.register.RegisterActivity;


public class LoginActivity extends ToolBarActivity implements ILoginView {

    private TextInputLayout mTextInputUsername;
    private TextInputLayout mTextInputPassword;
    private TextView mTvForgetPassword;
    private TextView mTvRegister;
    private Button mBtnLogin;

    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        mTextInputUsername = (TextInputLayout) findViewById(R.id.textInput_username);
        mTextInputPassword = (TextInputLayout) findViewById(R.id.textInput_password);
        mTvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
    }

    @Override
    public void initData() {
        mTvRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
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
            case R.id.tv_register: {
                startActivity(RegisterActivity.class);
            }
            break;
            case R.id.btn_login: {
                loginPresenter.toLogin(this);
            }
            break;
        }
    }

    @Override
    public String getUsername() {
        return mTextInputUsername.getEditText().getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mTextInputPassword.getEditText().getText().toString().trim();
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
        startActivity(HomeActivity.class);
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast("current code is " + code + " and msg is " + msg);
    }
}
