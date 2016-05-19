package org.liangxiaokou.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/5/19.
 */
public class PhotoUtils {


    /**
     * 获取图片的路径
     */
    public static File getImageFile(String path, String name) {
        File tmpDir = new File(Environment.getExternalStorageDirectory(), path);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        File outputImage = new File(tmpDir.getAbsolutePath() + File.separator + name);
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputImage;
    }

    /**
     * 转换uri
     *
     * @param uri
     * @return
     */
    public static Uri convertUri(Activity activity, Uri uri, File imageFile) {
        InputStream is = null;
        try {
            is = activity.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inPreferQualityOverSpeed = true;
            //1kb = 1024b,1mb = 1024kb
            if (is.available() > 1024 * 1024) {
                options.inSampleSize = 10;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
            return saveBitmap(bitmap, imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存图片
     *
     * @param bm
     * @return
     */
    public static Uri saveBitmap(Bitmap bm, File imageFile) {
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }


    /**
     * 计算图片的缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，即opts.width和opts.height
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // 设置为true，画质更好一点，加载时间略长
        options.inPreferQualityOverSpeed = true;

        return BitmapFactory.decodeFile(filePath, options);
    }


    public Bitmap getRotateBitmap(int type, String bitmapPath) { // type 1
        // 竖屏显示图片，2
        // 横屏显示图片

        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath);

        Bitmap resultBitmap = bitmap;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        //System.out.println("获取的bitmap 宽和高：" + width + "：" + height);

        switch (type) {
            case 1: // 竖屏显示图片,需要偏高
                if (height < width) {
                    // 如果长度小于宽度，则需要选择，否则不需要旋转，只要将option修改即可
                    Matrix mmMatrix = new Matrix();
                    mmMatrix.postRotate(90f);
                    resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mmMatrix, true);
                }
                break;
            case 2: // 横屏显示图片，需要偏长
                if (height > width) {
                    // 如果宽度小于长度
                    Matrix mmMatrix = new Matrix();
                    mmMatrix.postRotate(90f);
                    resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mmMatrix, true);
                }
                break;
            default:
                break;
        }
        return resultBitmap;
    }

}
