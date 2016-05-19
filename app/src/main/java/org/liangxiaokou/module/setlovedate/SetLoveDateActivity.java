package org.liangxiaokou.module.setlovedate;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.bigkoo.pickerview.TimePickerView;

import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.util.DateUtils;
import org.liangxiaokou.util.ToastUtils;
import org.mo.netstatus.NetUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SetLoveDateActivity extends ToolBarActivity implements TextWatcher, ISetLoveDateView {

    private static final java.lang.String TAG = "SetLoveDateActivity";

    private TextInputLayout textInputDate;

    private TimePickerView pvTime;

    private SetLoveDatePresenter setLoveDatePresenter = new SetLoveDatePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_love_date);
        showActionBarBack(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_set_love_date, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                boolean dateFormat = DateUtils.isDateFormat(textInputDate.getEditText().getText().toString());
                if (!dateFormat) {
                    showToast("格式不正确，例如2015-02-14");
                }
                setLoveDatePresenter.updateLove(getApplicationContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        textInputDate = (TextInputLayout) findViewById(R.id.textInput_date);
        EditText editTextDate = textInputDate.getEditText();
        editTextDate.setFocusable(false);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });
        editTextDate.addTextChangedListener(this);
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                textInputDate.getEditText().setText(getTime(date));
            }
        });
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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
        pvTime.dismiss();
        pvTime = null;
    }

    @Override
    public boolean PreOnKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public String getLoveDate() {
        return textInputDate.getEditText().getText().toString();
    }

    @Override
    public void showLoading() {
        alertDialog.show();
    }

    @Override
    public void hideLoading() {
        alertDialog.hide();
    }

    @Override
    public void onSuccess() {
        finish();
    }

    @Override
    public void onFailure(int code, String msg) {
        ToastUtils.toast(getApplicationContext(), msg);
    }
}
