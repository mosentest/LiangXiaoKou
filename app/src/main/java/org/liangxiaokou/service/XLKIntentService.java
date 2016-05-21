package org.liangxiaokou.service;

import android.app.IntentService;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;
import org.liangxiaokou.bean.Album;
import org.liangxiaokou.bmob.BmobIMNetUtils;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.module.album.Event;
import org.liangxiaokou.util.ToastUtils;
import org.liangxiaokou.util.VolleyLog;

import java.util.List;

import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;


public class XLKIntentService extends IntentService {
    public static final String ACTION_IM = "org.liangxiaokou.service.action.im";
    public static final String ACTION_UPLOAD_ALBUM = "org.liangxiaokou.service.action.upload_album";

    public static final String USER_ID = "org.liangxiaokou.service.extra.userId";

    public XLKIntentService() {
        super("BmobIMIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyLog.e("%s", "XLKIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        VolleyLog.e("%s", "XLKIntentService");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        VolleyLog.e("%s", "XLKIntentService");
        super.onDestroy();
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(true);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_IM.equals(action)) {
                final String userId = intent.getStringExtra(USER_ID);
                handleActionIM(userId);
            } else if (ACTION_UPLOAD_ALBUM.equals(action)) {
                //实现发布纪念册的内容
                final Album album = (Album) intent.getSerializableExtra("AlbumPresenter_album");
                final String[] albumPresenter_albumBeans = intent.getStringArrayExtra("AlbumPresenter_AlbumBeans");

                BmobNetUtils.updateFileBatch(getApplicationContext(), albumPresenter_albumBeans, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> files, List<String> urls) {
                        if (urls.size() == albumPresenter_albumBeans.length) {
                            //实现图片的上传功能。
                            album.setPhotos(files);
                            album.setPhotosUrl(urls);
                            handleActionUploadAlbum(album);
                        }
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {

                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        EventBus.getDefault().post(new Event(statuscode, errormsg, false));
                    }
                });
            }
        }
    }


    private void handleActionIM(final String userId) {
        BmobIMNetUtils.IMConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus connectionStatus) {
                switch (connectionStatus) {
                    case DISCONNECT:
                        connect(userId);
                    case CONNECTING:
                        break;
                    case CONNECTED:
                        break;
                    case NETWORK_UNAVAILABLE:
                        ToastUtils.toast(getApplicationContext(), "请打开网络");
                        break;
                    case KICK_ASS:
                        ToastUtils.toast(getApplicationContext(), "检查到在其他手机登录");
                        break;
                }
            }
        });
    }


    /**
     * 连接IM
     */
    private void connect(String userId) {
        BmobIMNetUtils.connect(userId, new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    VolleyLog.e("BmobIMIntentService %s", "bmob connect success");
                } else {
                    VolleyLog.e("BmobIMIntentService %s", e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
    }

    private void handleActionUploadAlbum(Album album) {
        //实现添加纪念册
        album.save(getApplicationContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                EventBus.getDefault().post(new Event("保存成功"));
            }

            @Override
            public void onFailure(int i, String s) {
                EventBus.getDefault().post(new Event(i, s, false));
            }
        });
    }
}
