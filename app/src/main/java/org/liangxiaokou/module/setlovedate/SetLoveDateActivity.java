package org.liangxiaokou.module.setlovedate;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.liangxiaokou.app.SwipeBackActivity;
import org.liangxiaokou.app.SwipeBackLayout;
import org.liangxiaokou.module.R;
import org.liangxiaokou.util.DateUtils;
import org.liangxiaokou.util.LogUtils;

public class SetLoveDateActivity extends SwipeBackActivity implements SetLoveDateActivityFragment.LoveDateTextListener {

    private static final java.lang.String TAG = "SetLoveDateActivity";

    private String date = "";

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
                boolean dateFormat = DateUtils.isDateFormat(date);
                if (!dateFormat) {
                    showToast("格式不正确，例如2015-02-14");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    public void onTextChange(CharSequence text) {
        date = text.toString();
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
