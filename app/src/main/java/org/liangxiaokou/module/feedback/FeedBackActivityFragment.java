package org.liangxiaokou.module.feedback;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.liangxiaokou.module.R;
import org.liangxiaokou.module.setting.SettingActivity;
import org.liangxiaokou.app.GeneralFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class FeedBackActivityFragment extends GeneralFragment {

    private Button btn_fb;

    public FeedBackActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }

    @Override
    protected void initView() {
        btn_fb = (Button) findViewById(R.id.btn_fb);
        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettingActivity.class);
            }
        });
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
