package org.liangxiaokou.bmob;

import android.content.Context;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by moziqi on 16-4-9.
 */
public class BmobMessageHandler extends BmobIMMessageHandler {
    private Context mContext;

    public BmobMessageHandler(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }
}