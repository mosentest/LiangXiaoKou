package org.liangxiaokou.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import org.liangxiaokou.module.R;
import org.liangxiaokou.util.ThirdUtils;
import org.liangxiaokou.util.ToastUtils;

/**
 * Created by moziqi on 2015/9/13 0013.
 */
public abstract class GeneralFragment extends Fragment implements View.OnClickListener {
    public final static int LOGIN_CODE = 0x20;
    public String mTitle;
    public final static String TITLE = "title";


    @Override
    public void onStart() {
        super.onStart();
        PreOnStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        PreOnResume();
        ThirdUtils.statisticsInFragmentResume(GeneralFragment.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        PreOnPause();
        ThirdUtils.statisticsInFragmentPause(GeneralFragment.class);
    }

    @Override
    public void onStop() {
        super.onStop();
        PreOnStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreOnDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void showToast(String msg) {
        ToastUtils.toast(getActivity().getApplicationContext(), msg);
    }

    public <T extends View> T findViewById(int resId) {
        return (T) getView().findViewById(resId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }


    public abstract void initView();

    public abstract void initData();

    public abstract void PreOnStart();

    public abstract void PreOnResume();

    public abstract void PreOnPause();

    public abstract void PreOnStop();

    public abstract void PreOnDestroy();

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        getActivity().startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        getActivity().startActivityForResult(intent, requestCode);
    }

}
