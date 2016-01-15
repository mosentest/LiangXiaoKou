package org.liangxiaokou.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

/**
 * SD卡相关的辅助类
 * * 增加android 4.0
 */
public class SDCardUtils {

    private SDCardUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     * <p/>
     * http://blog.csdn.net/chaoyue0071/article/details/47045629
     *
     * @return
     */

    public static ArrayList<String> getDevMountList() {
        String[] toSearch = FileUtils.readFile("/etc/vold.fstab", "UTF-8").toString().split(" ");
        //LogUtils.e("TAG", FileUtils.readFile("/etc/vold.fstab", "UTF-8").toString());
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    /**
     * 判断内置SDCard是否可用
     *
     * @return
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     * <p/>
     * 获取扩展SD卡存储目录
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    public static String getSDCardPath() {
        if (isSDCardEnable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        }
        String path = null;
        File sdCardFile = null;
        ArrayList<String> devMountList = getDevMountList();
        for (String devMount : devMountList) {
            File file = new File(devMount);
            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();
                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
                File testWritable = new File(path, "test_" + timeStamp);
                if (testWritable.mkdirs()) {
                    testWritable.delete();
                } else {
                    path = null;
                }
            }
        }
        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile.getAbsolutePath();
        }
        return "";
    }

    /**
     * http://wiki.xby1993.net/doku.php?id=projectdev:android_getsd_tf
     *
     * @return
     */
    public String getTFDir() {
        String path = "";
        try {
            InputStream ins = Runtime.getRuntime().exec("mount").getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("sdcard")) {
                    if (line.contains("vfat") || line.contains("fuse")) {
                        String split[] = line.split(" ");
                        path = split[1];
                    }
                }
            }
            reader.close();
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return path;
    }

    /**
     * http://www.oschina.net/question/157093_108899?fromerr=tMl8q6Al
     * @param context
     * @return
     */
//    public String[] getSDPath(Context context) {
//        StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
//        // 获取sdcard的路径：外置和内置
//        String[] paths = null;
//        try {
//            paths = (String[]) sm.getClass().getMethod("getVolumePaths", null).invoke(sm, null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        return paths;
//    }

    /**
     * 获取SD卡的剩余容量 单位byte
     *
     * @return
     */
    public static long getSDCardAllSize() {
        if (isSDCardEnable()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量  
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）  
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @param filePath
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量  
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    /**
     * 获取系统存储路径
     *
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * /data/data/com.xxx.xxx/files
     *
     * @param mContext
     * @return
     */
    public String getAppStoragePath(Context mContext) {
        String path = mContext.getApplicationContext().getFilesDir().getAbsolutePath();
        return path;
    }

}  