package org.liangxiaokou.module.setlovedate;

import android.content.Context;

import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.util.AESUtils;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/5/19.
 */
public class SetLoveDatePresenter {

    private ISetLoveDateView iSetLoveDateView;

    public SetLoveDatePresenter(ISetLoveDateView iSetLoveDateView) {
        this.iSetLoveDateView = iSetLoveDateView;
    }

    public void updateLove(Context context) {
        iSetLoveDateView.showLoading();
        //对数据的加密
        BmobNetUtils.updateLove(context, AESUtils.getEncryptString(iSetLoveDateView.getLoveDate()), new UpdateListener() {
            @Override
            public void onSuccess() {
                iSetLoveDateView.onSuccess();
                iSetLoveDateView.hideLoading();
            }

            @Override
            public void onFailure(int i, String s) {
                iSetLoveDateView.onFailure(i, s);
                iSetLoveDateView.hideLoading();
            }
        });
    }
}
