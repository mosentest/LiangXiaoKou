package org.liangxiaokou.module.album;

import org.liangxiaokou.app.IView;
import org.liangxiaokou.bean.Album;

import java.util.List;

/**
 * Created by Administrator on 2016/5/19.
 */
public interface IAlbumView extends IView {
    public Album getAlbum();

    public String[] getAlbumBeans();
}
