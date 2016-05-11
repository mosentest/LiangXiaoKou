package org.liangxiaokou.bmob;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.home.HomeActivity;

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
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(mContext);
        Intent notificationIntent = new Intent(mContext, HomeActivity.class);
        //该标志位表示如果Intent要启动的Activity在栈顶，则无须创建新的实例
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, Notification.FLAG_SHOW_LIGHTS, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent)
                .setDefaults(Notification.DEFAULT_ALL)//设置震动声音http://blog.csdn.net/wukunting/article/details/5661769，http://www.oschina.net/code/snippet_270292_14489
                .setSmallIcon(R.mipmap.ic_launcher)//设置状态栏里面的图标（小图标）
                //.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.i5))//下拉下拉列表里面的图标（大图标）
                //.setTicker("您有新的消息") //设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())//设置时间发生时间
                .setAutoCancel(true)//设置可以清除
                .setContentTitle("您有新的消息")//设置下拉列表里的标题
                .setContentText(event.getMessage().getContent() + "");//设置上下文内容
        Notification notification = builder.getNotification();
        mNotificationManager.notify(1, notification);

        //利用eventBus传信息
        EventBus.getDefault().post(event);
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用

    }
}