package org.liangxiaokou.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.liangxiaokou.app.NewsLifecycleHandler;
import org.liangxiaokou.util.LogUtils;
import org.liangxiaokou.util.NetWorkUtil;

public class XLKReceiver extends BroadcastReceiver {
    public final static String TAG = XLKReceiver.class.getSimpleName();

    private boolean isWifi = false;
    private boolean isScreen = false;

    public XLKReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //http://www.open-open.com/lib/view/open1331187479249.html
        LogUtils.e(TAG, intent.getAction());
        LogUtils.e(TAG, "current is " + NewsLifecycleHandler.isApplicationInBackground());
        if ("org.liangxiaokou.receiver.xlk_action".equals(intent.getAction())) {
            //来自app启动，启动服务中的轮询进程
//            Intent intent1 = new Intent(context, XLKService.class);
//            context.startService(intent1);
        } else if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            boolean networkAvailable = NetWorkUtil.isNetworkAvailable(context);
            boolean wifiConnected = NetWorkUtil.isWifiConnected(context);
            if (networkAvailable && wifiConnected) {
                //启动服务（AlarmManager和Service）中的轮询进程
            } else {
                //停止服务中的轮询进程
            }
        } else if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) { // 开屏
            //mScreenStateListener.onScreenOn();
        } else if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) { // 锁屏
            //mScreenStateListener.onScreenOff();
        } else if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) { // 解锁
            //mScreenStateListener.onUserPresent();
        } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
            //当屏幕超时进行锁屏时,当用户按下电源按钮,长按或短按(不管有没跳出话框)，进行锁屏时,android系统都会广播此Action消息
        }
    }


//    <uses-permission android:name="android.permission.CHANGE_CONFIGURATION" />

//    else if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
//        int strength = getStrength(context);
//        LogUtils.e(TAG, "当前信号" + strength);
//    } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
//        LogUtils.e(TAG, "网络状态改变");
//        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
//        if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {//如果断开连接
//            LogUtils.e(TAG, "断网了");
//        }
//    } else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
//        //WIFI开关
//        int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
//        if (wifistate == WifiManager.WIFI_STATE_DISABLED) {//如果关闭
//            LogUtils.e(TAG, "WIFI断网了");
//        }
//    }
//    <action android:name="android.net.wifi.RSSI_CHANGED" />
//    <action android:name="android.net.wifi.STATE_CHANGE" />
//    <action android:name="android.net.wifi.WIFI_STATE_CHANGED" />

//    public int getStrength(Context context) {
//        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifiManager.getConnectionInfo();
//        if (info.getBSSID() != null) {
//            int strength = WifiManager.calculateSignalLevel(info.getRssi(), 5);
//            // 链接速度
//            // int speed = info.getLinkSpeed();
//            // // 链接速度单位
//            // String units = WifiInfo.LINK_SPEED_UNITS;
//            // // Wifi源名称
//            // String ssid = info.getSSID();
//            return strength;
//        }
//        return 0;
//    }
}
