package org.liangxiaokou.module.home.fragment;

import android.content.Context;

import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bmob.BmobNetUtils;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/4/23.
 */
public class OtherPresenter {
    private IOtherView iHomeView;

    public OtherPresenter(IOtherView iHomeView) {
        this.iHomeView = iHomeView;
    }

    /**
     * 检查是否有好友
     *
     * @param context
     */
    public void checkHasFriend(Context context) {
//        BmobNetUtils.queryHasFriend(context.getApplicationContext(), new FindListener<Friend>() {
//            @Override
//            public void onSuccess(List<Friend> list) {
//                if (list.size() == 0) {
//                    iHomeView.noFriend();
//                } else {
//                    iHomeView.hasFriend();
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                iHomeView.onFailure(i, s);
//            }
//        });
    }
}
