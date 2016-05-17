package org.liangxiaokou.app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

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

    // 标志位，标志是否可见
    protected boolean isVisible;

    // 标志位，标志已经初始化完成。
    protected boolean isPrepared;


    @Override
    public void onStart() {
        PreOnStart();
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        alertDialog = new SpotsDialog(getActivity(), R.style.CustomDialog);
    }

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

    public <T extends View> T findViewById(int resId) {
        return (T) getView().findViewById(resId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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


    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }
    protected abstract void lazyLoad();//懒加载的方法,在这个方法里面我们为Fragment的各个组件去添加数据

    protected void onInvisible() {
    }

}
