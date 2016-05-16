package org.liangxiaokou.util;


/**
 * Created by moziqi on 16-4-2.
 */
public class BaiduLBSutils {

//    /**
//     * 启动定位
//     *
//     * @param context
//     * @param bdLocationListener
//     */
//    public static LocationClient locationStart(Context context, BDLocationListener bdLocationListener) {
//        // 定位相关，定位初始化
//        LocationClient mLocClient = new LocationClient(context.getApplicationContext());
//        //注册监听函数
//        if (bdLocationListener == null) {
//            throw new AssertionError("locationStart:bdLocationListener is null");
//        }
//        mLocClient.registerLocationListener(bdLocationListener);
//        LocationClientOption option = new LocationClientOption();
//        //option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000 * 0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        mLocClient.setLocOption(option);
//        mLocClient.start();
//        return mLocClient;
//    }
//
//    /**
//     * 停止定位
//     *
//     * @param locationClient
//     */
//    public static void locationStop(LocationClient locationClient) {
//        if (locationClient != null) {
//            locationClient.stop();
//        }
//    }
//
//    public static GeoCoder getInstance(LatLng latLng, OnGetGeoCoderResultListener listener) {
//        GeoCoder geoCoder = GeoCoder.newInstance();
//        // 设置地理编码检索监听者
//        if (listener == null) {
//            throw new AssertionError("getInstance:onGetGeoCoderResultListener is null");
//        }
//        geoCoder.setOnGetGeoCodeResultListener(listener);
//        //
//        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
//        return geoCoder;
//    }
}
