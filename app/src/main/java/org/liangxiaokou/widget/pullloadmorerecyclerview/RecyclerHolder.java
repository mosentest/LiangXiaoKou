package org.liangxiaokou.widget.pullloadmorerecyclerview;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
 
/**
 * 适配一切RecyclerView.ViewHolder
 *
 * @author moziqi on 8/27/15.
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
 
    public RecyclerHolder(View itemView) {
        super(itemView);
        //默认只有15,这里最好针对不同的item来重新设计一个
        this.mViews = new SparseArray<View>(15);
    }
 
    public SparseArray<View> getAllView() {
        return mViews;
    }
 
    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
 
    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerHolder setText(int viewId, String text) {
        TextView view = findViewById(viewId);
        view.setText(text);
        return this;
    }
 
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = findViewById(viewId);
        view.setImageResource(drawableId);
        return this;
    }
 
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public RecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = findViewById(viewId);
        view.setImageBitmap(bm);
        return this;
    }
 
    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param uri
     * @return
     */
    public RecyclerHolder setImageByUrl(int viewId,String uri) {
        //自己修改加载图片加载类
        ImageView view = findViewById(viewId);
        //ImageLoaderUtils.displayImage(uri, view);
        return this;
    }
}