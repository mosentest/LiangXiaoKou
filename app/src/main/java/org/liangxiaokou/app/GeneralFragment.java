package org.liangxiaokou.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import org.liangxiaokou.util.ThirdUtils;

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
//        RefWatcher refWatcher = MApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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


    protected abstract void initView();

    protected abstract void initData();

    protected abstract void PreOnStart();

    protected abstract void PreOnResume();

    protected abstract void PreOnPause();

    protected abstract void PreOnStop();

    protected abstract void PreOnDestroy();


    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }
}
