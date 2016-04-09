package org.liangxiaokou.module.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.liangxiaokou.bean.Album;
import org.liangxiaokou.enum_.ExpressionManEnum;
import org.liangxiaokou.enum_.ExpressionWomanEnum;
import org.liangxiaokou.module.R;
import org.liangxiaokou.config.Constants;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2015/12/17.
 */
public class AlbumViewAdapter extends RecyclerView.Adapter<AlbumViewHolder> {
    public Context mContext;
    public List<Album> mDatas;
    public LayoutInflater mLayoutInflater;

    public AlbumViewAdapter(Context mContext, List<Album> mDatas) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mLayoutInflater.inflate(R.layout.fragment_album_item, parent, false);
        AlbumViewHolder mViewHolder = new AlbumViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album album = mDatas.get(position);
        //取得随机数
        Random random = new Random();
        int result = random.nextInt(5);
        if (album.getType() == Constants.man) {
            ExpressionManEnum[] values = ExpressionManEnum.values();
            ExpressionManEnum value = values[result];
            holder.iv_line_ico.setImageDrawable(mContext.getResources().getDrawable(value.getResImg()));
            holder.view_line.setBackgroundColor(mContext.getResources().getColor(value.getResColor()));
            holder.view_line_bottom.setBackgroundColor(mContext.getResources().getColor(value.getResColor()));
        } else {
            ExpressionWomanEnum[] values = ExpressionWomanEnum.values();
            ExpressionWomanEnum value = values[result];
            holder.iv_line_ico.setImageDrawable(mContext.getResources().getDrawable(value.getResImg()));
            holder.view_line.setBackgroundColor(mContext.getResources().getColor(value.getResColor()));
            holder.view_line_bottom.setBackgroundColor(mContext.getResources().getColor(value.getResColor()));
        }
        holder.iv_line_ico.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.tv__album_user.setText(album.getUsername());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
