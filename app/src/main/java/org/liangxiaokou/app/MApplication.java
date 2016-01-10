package org.liangxiaokou.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by moziqi on 2015/12/15 0015.
 */
public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }

    //在自己的Application中添加如下代码
    public static RefWatcher getRefWatcher(Context context) {
        MApplication application = (MApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    //在自己的Application中添加如下代码
    private RefWatcher refWatcher;

//    protected RefWatcher installLeakCanary() {
//        return RefWatcher.DISABLED;
//        return LeakCanary.install(MApplication.this, LeakUploadService.class);
//    }
}
