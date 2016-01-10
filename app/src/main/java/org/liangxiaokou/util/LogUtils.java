package org.liangxiaokou.util;

import android.util.Log;

/**
 * 简单日志管理
 * Created by moziqi on 2015/12/23.
 */
public class LogUtils {

    private final static boolean isDebug = true;
    private final static String chaecha = "chaecha";

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        e(chaecha, msg);
    }
}
