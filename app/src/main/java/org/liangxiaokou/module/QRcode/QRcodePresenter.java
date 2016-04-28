package org.liangxiaokou.module.QRcode;

import android.content.Context;

import org.json.JSONObject;

import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by Administrator on 2016/4/26.
 */
public class QRcodePresenter implements ValueEventListener {
    private IQRcodeView inviteView;
    private BmobRealTimeData rtd;

    public QRcodePresenter(IQRcodeView inviteView) {
        this.inviteView = inviteView;
    }

    public void BmobRealTimeData(Context context) {
        rtd = new BmobRealTimeData();
        rtd.start(context.getApplicationContext(), this);
    }

    public void unsubTableDelete() {
        // 取消监听表删除
        rtd.unsubTableDelete(inviteView.getTableName());
    }

    @Override
    public void onConnectCompleted() {
        if (rtd.isConnected()) {
            // 监听表更新
            rtd.subTableUpdate(inviteView.getTableName());
        }
    }

    @Override
    public void onDataChange(JSONObject jsonObject) {
        inviteView.onSuccess(jsonObject);
    }
}
