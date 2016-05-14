package org.liangxiaokou.service;

import android.app.IntentService;
import android.content.Intent;

import org.liangxiaokou.bmob.BmobIMNetUtils;
import org.liangxiaokou.util.ToastUtils;
import org.liangxiaokou.util.VolleyLog;

import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.exception.BmobException;


public class BmobIMIntentService extends IntentService {
    public static final String ACTION_IM = "org.liangxiaokou.service.action.IM";
    public static final String ACTION_BAZ = "org.liangxiaokou.service.action.BAZ";

    public static final String USER_ID = "org.liangxiaokou.service.extra.userId";
    public static final String EXTRA_PARAM2 = "org.liangxiaokou.service.extra.PARAM2";

    public BmobIMIntentService() {
        super("BmobIMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_IM.equals(action)) {
                final String userId = intent.getStringExtra(USER_ID);
                connect(userId);
                handleActionIM(userId);
            } else if (ACTION_BAZ.equals(action)) {
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param2);
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleActionIM(final String userId) {
        BmobIMNetUtils.IMConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus connectionStatus) {
                switch (connectionStatus) {
                    case DISCONNECT:
                        connect(userId);
                    case CONNECTING:
                        break;
                    case CONNECTED:
                        break;
                    case NETWORK_UNAVAILABLE:
                        ToastUtils.toast(getApplicationContext(), "请打开网络");
                        break;
                    case KICK_ASS:
                        ToastUtils.toast(getApplicationContext(), "检查到在其他手机登录");
                        break;
                }
            }
        });
    }


    /**
     * 连接IM
     */
    private void connect(String userId) {
        BmobIMNetUtils.connect(userId, new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    VolleyLog.e("BmobIMIntentService %s", "bmob connect success");
                } else {
                    VolleyLog.e("BmobIMIntentService %s", e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
    }

    private void handleActionBaz(String param2) {
    }
}
