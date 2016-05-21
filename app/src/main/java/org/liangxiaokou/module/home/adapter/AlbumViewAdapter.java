package org.liangxiaokou.module.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.a.a.V;

import org.liangxiaokou.bean.Album;
import org.liangxiaokou.enum_.ExpressionManEnum;
import org.liangxiaokou.enum_.ExpressionWomanEnum;
import org.liangxiaokou.module.R;
import org.liangxiaokou.config.Constants;
import org.mo.glide.ImageUtils;

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
        holder.iv_line_ico.setImageDrawable(mContext.getResources().getDrawable(album.getResImg()));
        holder.view_line.setBackgroundColor(mContext.getResources().getColor(album.getResColor()));
        holder.view_line_bottom.setBackgroundColor(mContext.getResources().getColor(album.getResColor()));
        holder.iv_line_ico.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.tv__album_content.setText(album.getContent());
        holder.tv_album_address.setText(album.getAddress());
        holder.tv_line_time.setText(album.getDate());
        holder.tv__album_user.setText(album.getUsername());
        switch (album.getPhotosUrl().size()) {
            case 4:
                ImageUtils.loadImg(mContext, holder.iv_album_four, album.getPhotosUrl().get(3));
                ImageUtils.loadImg(mContext, holder.iv_album_three, album.getPhotosUrl().get(2));
                ImageUtils.loadImg(mContext, holder.iv_album_two, album.getPhotosUrl().get(1));
                ImageUtils.loadImg(mContext, holder.iv_album_one, album.getPhotosUrl().get(0));
                holder.iv_album_four.setVisibility(View.VISIBLE);
                holder.iv_album_three.setVisibility(View.VISIBLE);
                holder.iv_album_two.setVisibility(View.VISIBLE);
                holder.iv_album_one.setVisibility(View.VISIBLE);
                break;
            case 3:
                ImageUtils.loadImg(mContext, holder.iv_album_three, album.getPhotosUrl().get(2));
                ImageUtils.loadImg(mContext, holder.iv_album_two, album.getPhotosUrl().get(1));
                ImageUtils.loadImg(mContext, holder.iv_album_one, album.getPhotosUrl().get(0));
                holder.iv_album_four.setVisibility(View.GONE);
                holder.iv_album_three.setVisibility(View.VISIBLE);
                holder.iv_album_two.setVisibility(View.VISIBLE);
                holder.iv_album_one.setVisibility(View.VISIBLE);
                break;
            case 2:
                ImageUtils.loadImg(mContext, holder.iv_album_two, album.getPhotosUrl().get(1));
                ImageUtils.loadImg(mContext, holder.iv_album_one, album.getPhotosUrl().get(0));
                holder.iv_album_four.setVisibility(View.GONE);
                holder.iv_album_three.setVisibility(View.GONE);
                holder.iv_album_two.setVisibility(View.VISIBLE);
                holder.iv_album_one.setVisibility(View.VISIBLE);
                break;
            case 1:
                ImageUtils.loadImg(mContext, holder.iv_album_one, album.getPhotosUrl().get(0));
                holder.iv_album_four.setVisibility(View.GONE);
                holder.iv_album_three.setVisibility(View.GONE);
                holder.iv_album_two.setVisibility(View.GONE);
                holder.iv_album_one.setVisibility(View.VISIBLE);
                break;
            default:
                holder.iv_album_four.setVisibility(View.GONE);
                holder.iv_album_three.setVisibility(View.GONE);
                holder.iv_album_two.setVisibility(View.GONE);
                holder.iv_album_one.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void refreshData(List<Album> mDatas) {
        if (this.mDatas != null && this.mDatas.size() > 0) {
            this.mDatas.clear();
        }
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }
}
