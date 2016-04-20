package org.liangxiaokou.app;

/**
 * Created by Administrator on 2016/4/20.
 */
public interface IView {

    public void showLoading();

    public void hideLoading();

    public void onSuccess();

    public void onFailure(int code, String msg);
}
