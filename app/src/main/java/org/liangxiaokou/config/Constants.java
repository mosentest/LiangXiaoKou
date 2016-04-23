package org.liangxiaokou.config;

import android.provider.MediaStore;

/**
 * Created by moziqi on 2015/12/17 0017.
 */
public class Constants {
    public final static int man = 1;
    public final static int woman = 0;

    public static final String author = "moziqi";

    public static final String APP_NAME = "liangxiaokou";

    public static final String SAVE_IMAGE_DIR_PATH = "/image";

    public static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME,//显示的名字
            MediaStore.Images.Media.DATA,//显示路径
            MediaStore.Images.Media.LONGITUDE,//经度
            MediaStore.Images.Media._ID,//id
            MediaStore.Images.Media.BUCKET_ID,//dir id 目录
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,//dir name 目录名字
            MediaStore.Images.Media.ORIENTATION
    };

    public static final int _TIME = 100;
}
