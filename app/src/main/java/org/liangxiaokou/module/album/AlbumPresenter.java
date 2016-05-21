package org.liangxiaokou.module.album;

import android.app.Activity;
import android.content.Intent;

import org.liangxiaokou.service.XLKIntentService;

/**
 * Created by Administrator on 2016/5/20.
 */
public class AlbumPresenter {

    private IAlbumView iAlbumView;

    public AlbumPresenter(IAlbumView iAlbumView) {
        this.iAlbumView = iAlbumView;
    }


    /**
     * 实现发布的功能
     */
    public void toPublish(Activity activity) {
        iAlbumView.showLoading();
        Intent intent = new Intent(activity, XLKIntentService.class);
        intent.setAction(XLKIntentService.ACTION_UPLOAD_ALBUM);
        intent.putExtra("AlbumPresenter_album", iAlbumView.getAlbum());
        intent.putExtra("AlbumPresenter_AlbumBeans", iAlbumView.getAlbumBeans());
        activity.startService(intent);
        iAlbumView.onSuccess();
        //iAlbumView.hideLoading();
    }
}
