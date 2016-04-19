package org.liangxiaokou.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ToastUtils {

    public static void toast(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg + "", Toast.LENGTH_SHORT).show();
    }
}
