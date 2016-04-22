package org.liangxiaokou.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.baidu.mapapi.SDKInitializer;
import com.squareup.leakcanary.LeakCanary;

import org.liangxiaokou.bmob.BmobMessageHandler;

import java.util.List;
import java.util.Stack;

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

    private static Stack<Activity> activityStack;

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


    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {

        }
    }
}
