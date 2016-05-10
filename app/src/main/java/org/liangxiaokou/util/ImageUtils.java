package org.liangxiaokou.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.liangxiaokou.module.R;

/**
 * http://limuzhi.com/2016/01/24/Android%E5%9B%BE%E7%89%87%E5%BA%93-Glide/
 * <p>
 * http://vardhan-justlikethat.blogspot.jp/2014/09/android-image-loading-libraries-picasso.html
 * <p>
 * Created by Administrator on 2016/5/10.
 */
public class ImageUtils {


    /**
     * 圆图
     *
     * @param source
     * @return
     */
    public static Bitmap getCircularBitmapImage(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    public void loadImg(Context context, ImageView imageView, String uri) {
        Glide.with(context.getApplicationContext())
                .load(uri)
                .centerCrop()
                .thumbnail(0.1f)//缩略图
                .placeholder(R.drawable.ic_placeholder)//设置加载中图片
                .error(R.drawable.ic_error)//设置错误图片
                .into(imageView);
    }

}
