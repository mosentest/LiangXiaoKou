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
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    protected void initView() {
//        btn_setting = (Button) findViewById(R.id.btn_setting);
//        btn_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(FeedBackActivity.class);
//            }
//        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void PreOnStart() {

    }

    @Override
    protected void PreOnResume() {

    }

    @Override
    protected void PreOnPause() {

    }

    @Override
    protected void PreOnStop() {

    }

    @Override
    protected void PreOnDestroy() {

    }

    @Override
    public void onClick(View v) {

    }
}
