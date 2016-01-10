package org.liangxiaokou.module.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;


public class MeFragment extends GeneralFragment {

    public MeFragment() {
    }

    public static MeFragment getInstance(String title) {
        MeFragment instance = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        instance.setArguments(bundle);
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }


    @Override
    protected void initView() {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onClick(View v) {

    }
}
