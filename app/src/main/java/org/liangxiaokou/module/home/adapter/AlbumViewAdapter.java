package org.liangxiaokou.module.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.a.a.V;

import org.liangxiaokou.bean.Album;
import org.liangxiaokou.enum_.ExpressionManEnum;
import org.liangxiaokou.enum_.ExpressionWomanEnum;
import org.liangxiaokou.module.R;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.util.ScreenUtils;
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
        //holder.iv_line_ico.setImageDrawable(mContext.getResources().getDrawable(album.getResImg()));
//        holder.view_line.setBackgroundColor(mContext.getResources().getColor(album.getResColor()));
//        holder.view_line_bottom.setBackgroundColor(mContext.getResources().getColor(album.getResColor()));
        ImageUtils.loadChatUserImg(mContext, holder.iv_line_ico, album.getType() == 0 ? R.mipmap.boy : R.mipmap.gril);
        if (TextUtils.isEmpty(album.getContent())) {
            holder.epxand_text_view.setVisibility(View.GONE);
        } else {
            holder.epxand_text_view.setVisibility(View.VISIBLE);
            holder.epxand_text_view.setText(album.getContent().trim());
        }
        if (TextUtils.isEmpty(album.getAddress())) {
            holder.tv_album_address.setVisibility(View.GONE);
        } else {
            holder.tv_album_address.setVisibility(View.VISIBLE);
            holder.tv_album_address.setText(album.getAddress());
        }
        if(position == 0){
            holder.tv_line_time.setText(album.getDate());
            holder.tv_line_time.setVisibility(View.VISIBLE);
        }else{
            Album albumPrior = mDatas.get(position - 1);
            if (albumPrior.getDate().equals(album.getDate())) {
                holder.tv_line_time.setVisibility(View.GONE);
            } else {
                holder.tv_line_time.setText(album.getDate());
                holder.tv_line_time.setVisibility(View.VISIBLE);
            }
        }
        holder.tv__album_user.setText(album.getUsername());
        ViewGroup.LayoutParams params1 = holder.iv_album_one.getLayoutParams();
        ViewGroup.LayoutParams params2 = holder.iv_album_two.getLayoutParams();
        ViewGroup.LayoutParams params3 = holder.iv_album_three.getLayoutParams();
        ViewGroup.LayoutParams params4 = holder.iv_album_four.getLayoutParams();
        if (album.getPhotosUrl() != null) {
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
                    params1.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params1.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_one.setLayoutParams(params1);
                    params2.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params2.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_two.setLayoutParams(params2);
                    params3.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params3.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_three.setLayoutParams(params3);
                    params3.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params4.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_four.setLayoutParams(params4);
                    break;
                case 3:
                    ImageUtils.loadImg(mContext, holder.iv_album_three, album.getPhotosUrl().get(2));
                    ImageUtils.loadImg(mContext, holder.iv_album_two, album.getPhotosUrl().get(1));
                    ImageUtils.loadImg(mContext, holder.iv_album_one, album.getPhotosUrl().get(0));
                    holder.iv_album_four.setVisibility(View.GONE);
                    holder.iv_album_three.setVisibility(View.VISIBLE);
                    holder.iv_album_two.setVisibility(View.VISIBLE);
                    holder.iv_album_one.setVisibility(View.VISIBLE);
                    params1.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params1.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_one.setLayoutParams(params1);
                    params2.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params2.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_two.setLayoutParams(params2);
                    params3.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params3.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_three.setLayoutParams(params3);
                    break;
                case 2:
                    ImageUtils.loadImg(mContext, holder.iv_album_two, album.getPhotosUrl().get(1));
                    ImageUtils.loadImg(mContext, holder.iv_album_one, album.getPhotosUrl().get(0));
                    holder.iv_album_four.setVisibility(View.GONE);
                    holder.iv_album_three.setVisibility(View.GONE);
                    holder.iv_album_two.setVisibility(View.VISIBLE);
                    holder.iv_album_one.setVisibility(View.VISIBLE);
                    params1.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params1.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_one.setLayoutParams(params1);
                    params2.height = ScreenUtils.getScreenWidth(mContext) / 3;
                    params2.width = ScreenUtils.getScreenWidth(mContext) / 3;
                    holder.iv_album_two.setLayoutParams(params2);
                    break;
                case 1:
                    ImageUtils.loadImg(mContext, holder.iv_album_one, album.getPhotosUrl().get(0));
                    holder.iv_album_four.setVisibility(View.GONE);
                    holder.iv_album_three.setVisibility(View.GONE);
                    holder.iv_album_two.setVisibility(View.GONE);
                    holder.iv_album_one.setVisibility(View.VISIBLE);
                    params1.height = ScreenUtils.getScreenWidth(mContext) / 4 * 2;
                    params1.width = ScreenUtils.getScreenWidth(mContext) / 4 * 2;
                    holder.iv_album_one.setLayoutParams(params1);
                    break;
                default:
                    holder.iv_album_four.setVisibility(View.GONE);
                    holder.iv_album_three.setVisibility(View.GONE);
                    holder.iv_album_two.setVisibility(View.GONE);
                    holder.iv_album_one.setVisibility(View.GONE);
            }
            holder.iv_album_four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.iv_album_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.iv_album_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.iv_album_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else {
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
