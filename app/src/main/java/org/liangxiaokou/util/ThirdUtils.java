package org.liangxiaokou.util;

import android.content.Context;


import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;

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
        //创建后，就注释这行代码，不然重复创建表
        //BmobUpdateAgent.initAppVersion(mContext);
    }

    /**
     * 更新配置
     *
     * @param mContext
     * @param onlyWifi
     */
    public static void updateInit(Context mContext,
                                  boolean onlyWifi) {
        BmobUpdateAgent.update(mContext);
        BmobUpdateAgent.setUpdateOnlyWifi(onlyWifi);
        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
            @Override
            public void onUpdateReturned(int i, UpdateResponse updateResponse) {

            }
        });
//        if (checkConfig) {
//            BmobUpdateAgent.setUpdateListener(updateListener);
//        }
    }

    public static void statisticsInActivityResume(Context mContext) {
        //统计
    }

    public static void statisticsInActivityPause(Context mContext) {
    }

    public static void statisticsInFragmentResume(Class cls) {
    }

    public static void statisticsInFragmentPause(Class cls) {
    }
}
