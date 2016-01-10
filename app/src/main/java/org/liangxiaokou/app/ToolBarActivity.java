package org.liangxiaokou.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import org.liangxiaokou.module.R;
import org.liangxiaokou.util.DensityUtils;
import org.liangxiaokou.util.StatusBarCompat;
import org.liangxiaokou.util.ThirdUtils;


/**
 * 由于使用了ToolBar一些属性，暂时不是用这个,目前由SwipeBackActivity来处理
 * Created by moziqi on 2015/12/9.
 */
@Deprecated
public abstract class ToolBarActivity extends AppCompatActivity implements IActivity {
    private ToolBarHelper mToolBarHelper;
    protected Toolbar toolbar;
    private SlidrConfig mConfig;

    @Override
    protected void onStart() {
        super.onStart();
        PreOnStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int primary = getResources().getColor(R.color.system_color);
        int secondary = getResources().getColor(R.color.system_press);
        // Build the slidr config
        mConfig = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .position(SlidrPosition.LEFT)
                .velocityThreshold(2400)
                .distanceThreshold(.25f)
                .edge(true)
                .touchSize(DensityUtils.dp2px(this, 32))
                .build();
        Slidr.attach(this, mConfig);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        PreOnRestart();
    }


    public void onResume() {
        super.onResume();
        PreOnResume();
        ThirdUtils.statisticsInActivityResume(this);
    }

    public void onPause() {
        super.onPause();
        PreOnPause();
        ThirdUtils.statisticsInActivityPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PreOnStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreOnDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {
        //super.setContentView(layoutResID);
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        //设置state
        StatusBarCompat.compat(this);
        initView();
        initData();
        //getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (PreOnKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);

    }
}
