package org.liangxiaokou.util;

import android.content.Context;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateStatus;

import cn.bmob.v3.Bmob;

/**
 * Created by moziqi on 2015/12/18 0018.
 * 第三方配置
 */
public class ThirdUtils {

    private final static String bmobAppKey = "ab3b4c13dc5dc2ddf06ed9d04041089f";

    /**
     * bmob配置
     *
     * @param mContext 主Activity
     */
    public static void bmobInit(Context mContext) {
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(mContext.getApplicationContext(), bmobAppKey);
    }

    /**
     * 友盟配置
     *
     * @param mContext
     * @param onlyWifi
     * @param checkConfig
     * @param umengUpdateListener
     */
    public static void umengInit(Context mContext,
                                 boolean onlyWifi,
                                 boolean checkConfig,
                                 UmengUpdateListener umengUpdateListener) {
        //恢复默认设置
        UmengUpdateAgent.setDefault();
        //更新提示的开关
        //其他网络环境下进行更新自动提醒
        UmengUpdateAgent.setUpdateOnlyWifi(onlyWifi);
        //设置回调接口
        UmengUpdateAgent.setUpdateListener(umengUpdateListener);
        //使用对话框
        UmengUpdateAgent.setUpdateUIStyle(UpdateStatus.STYLE_DIALOG);
        //http://dev.umeng.com/auto-update/android-doc/quick-start#1,集成检测
        UmengUpdateAgent.setUpdateCheckConfig(checkConfig);
        //友盟更新
        UmengUpdateAgent.update(mContext.getApplicationContext());

        /** 设置是否对日志信息进行加密, 默认false(不加密). */
        AnalyticsConfig.enableEncrypt(true);
    }

    public static void statisticsInActivityResume(Context mContext) {
        //友盟统计
        MobclickAgent.onResume(mContext.getApplicationContext());
    }

    public static void statisticsInActivityPause(Context mContext) {
        //友盟统计
        MobclickAgent.onPause(mContext.getApplicationContext());
    }

    public static void statisticsInFragmentResume(Class cls) {
        //友盟统计
        MobclickAgent.onPageStart(cls.getSimpleName());
    }

    public static void statisticsInFragmentPause(Class cls) {
        //友盟统计
        MobclickAgent.onPageEnd(cls.getSimpleName());
    }
}
