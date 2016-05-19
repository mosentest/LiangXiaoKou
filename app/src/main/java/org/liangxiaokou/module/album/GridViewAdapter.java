package org.liangxiaokou.module.album;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.liangxiaokou.module.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<AlbumBean> albumBeanList;
    private Context mContext;

    public GridViewAdapter(Context mContext) {
        albumBeanList = new ArrayList<>();
        albumBeanList.add(new AlbumBean("", true));
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return albumBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AlbumBean albumBean = albumBeanList.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_album_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (albumBean.isPick()) {
            viewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.marquee_add_photo_48));
        } else {
            //设置图片
            viewHolder.imageView.setImageURI(Uri.parse(albumBean.getFilePath()));
        }
        return convertView;
    }

    public void addData(AlbumBean albumBean) {
        switch (albumBeanList.size()) {
            case 1:
            case 2:
            case 3:
                //先移除最后那一个，在添加
                albumBeanList.remove(albumBeanList.size() - 1);
                albumBeanList.add(albumBean);
                albumBeanList.add(new AlbumBean("", true));
                break;
            case 4:
                //先移除最后那一个，在添加
                albumBeanList.remove(albumBeanList.size() - 1);
                albumBeanList.add(albumBean);
                break;
        }
        this.notifyDataSetInvalidated();
    }

    public void removeData(int position) {
        removeData(albumBeanList.get(position));
    }

    public void removeData(AlbumBean albumBean) {
        if (albumBeanList != null && albumBeanList.contains(albumBean)) {
            //判断当前的数量
            switch (albumBeanList.size()) {
                case 1:
                case 2:
                case 3:
                    albumBeanList.remove(albumBean);
                    break;
                case 4:
                    albumBeanList.remove(albumBean);
                    if (!albumBeanList.get(albumBeanList.size() - 1).isPick()) {
                        albumBeanList.add(new AlbumBean("", true));
                    }
                    break;
            }
            this.notifyDataSetInvalidated();
        }
    }

    public List<AlbumBean> getData() {
        return albumBeanList;
    }

    private static class ViewHolder {
        public ImageView imageView;
    }
}
