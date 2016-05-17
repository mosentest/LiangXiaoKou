package org.liangxiaokou.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.liangxiaokou.module.R;
import org.liangxiaokou.util.ThirdUtils;
import org.liangxiaokou.util.ToastUtils;

import dmax.dialog.SpotsDialog;

/**
 * http://blog.csdn.net/maosidiaoxian/article/details/38300627
 * Created by moziqi on 2015/9/13 0013.
 */
public abstract class GeneralFragment extends Fragment implements View.OnClickListener {
    public final static int LOGIN_CODE = 0x20;
    public String mTitle;
    public final static String TITLE = "title";

    protected AlertDialog alertDialog;

    private boolean isFirstVisible = true;//第一次可见状态

    private boolean isFirstInvisible = true;//第一次不可见状态

    private boolean isPrepared;


    @Override
    public void onStart() {
        PreOnStart();
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alertDialog = new SpotsDialog(getActivity(), R.style.CustomDialog);
        initViewsAndEvents(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    protected abstract int getContentViewLayoutID();


    protected abstract void initViewsAndEvents(View view);

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    protected abstract void onFirstUserVisible();

    protected abstract void onUserVisible();

    private void onFirstUserInvisible() {
    }

    protected abstract void onUserInvisible();


    @Override
    public void onResume() {
        PreOnResume();
        ThirdUtils.statisticsInFragmentResume(GeneralFragment.class);
        super.onResume();
    }

    @Override
    public void onPause() {
        PreOnPause();
        ThirdUtils.statisticsInFragmentPause(GeneralFragment.class);
        super.onPause();
    }

    @Override
    public void onStop() {
        PreOnStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        alertDialog = null;
        PreOnDestroy();
        super.onDestroy();
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
