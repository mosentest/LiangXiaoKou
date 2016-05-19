package org.liangxiaokou.bmob;

import cn.bmob.v3.BmobRealTimeData;

/**
 * Created by Administrator on 2016/5/19.
 */
public class BmobRealTimeDataUtils {

    //私有化构造方法
    private BmobRealTimeDataUtils() {

    }

    public static BmobRealTimeData getInstance() {
        return SingletonHolder.rtd;
    }


    private static class SingletonHolder {
        //静态初始化器，由JVM来保证线程安全
        private static BmobRealTimeData rtd = new BmobRealTimeData();
    }


}
