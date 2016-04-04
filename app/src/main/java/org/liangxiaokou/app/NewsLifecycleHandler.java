package org.liangxiaokou.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class NewsLifecycleHandler implements Application.ActivityLifecycleCallbacks {

    // I use four separate variables here. You can, of course, just use two and  
    // increment/decrement them instead of using four and incrementing them all.
    private final static String TAG = NewsLifecycleHandler.class.getSimpleName();
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    public NewsLifecycleHandler() {
        resetVariables();
    }

    public void resetVariables() {
        resumed = 0;
        paused = 0;
        started = 0;
        stopped = 0;
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
        android.util.Log.e(activity.getClass().getSimpleName() + "--" + TAG, "application is in foreground: " + (resumed > paused));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        android.util.Log.e(activity.getClass().getSimpleName() + "--" + TAG, "application is visible: " + (started > stopped));
    }

    // If you want a static function you can use to check if your application is  
    // foreground/background, you can use the following:  


    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        return resumed > paused;
    }

    public static boolean isApplicationInBackground() {
        return started == stopped;
    }

}  