package org.liangxiaokou.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;

import org.liangxiaokou.bmob.BmobMessageHandler;

import java.util.List;

import cn.bmob.newim.BmobIM;

/**
 * Created by moziqi on 2015/12/15 0015.
 */
public class MApplication extends Application {

    private NewsLifecycleHandler mNewsLifecycleHandler;

    private static MApplication instance = null;

    public static MApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (shouldInit()) {
            instance = this;
            mNewsLifecycleHandler = new NewsLifecycleHandler();
            registerActivityLifecycleCallbacks(mNewsLifecycleHandler);
            //bmob
            //NewIM初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new BmobMessageHandler(this));
            //检测代码问题
            LeakCanary.install(this);
            // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
            SDKInitializer.initialize(this);
            try {
                /**
                 * 添加网络权限，安卓4.03必须
                 */
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads().detectDiskWrites().detectNetwork()
                        .penaltyLog().build());
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                        .penaltyLog().penaltyDeath().build());
                /**
                 * 添加网络权限，安卓4.03必须
                 */

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public NewsLifecycleHandler getmNewsLifecycleHandler() {
        return mNewsLifecycleHandler;
    }
}
