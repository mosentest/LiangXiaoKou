package org.liangxiaokou.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.liangxiaokou.recevier.XLKReceiver;
import org.liangxiaokou.util.L;

public class XLKService extends Service {
    public final static String TAG = XLKService.class.getSimpleName();

    public XLKService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.e(TAG + ".onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.e(TAG + ".onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
