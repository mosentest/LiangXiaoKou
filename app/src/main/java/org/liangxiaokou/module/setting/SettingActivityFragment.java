package org.liangxiaokou.module.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingActivityFragment extends GeneralFragment {

    private Button btn_setting;

    public SettingActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_setting, container, false);
        isPrepared = true;
        lazyLoad();
        return inflate;
    }

    @Override
    public void initView() {
//        btn_setting = (Button) findViewById(R.id.btn_setting);
//        btn_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(FeedBackActivity.class);
//            }
//        });
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
    public void PreOnPause() {

    }

    @Override
    public void PreOnStop() {

    }

    @Override
    public void PreOnDestroy() {

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initView();
        initData();
    }

    @Override
    public void onClick(View v) {

    }
}
