package org.liangxiaokou.module.home.fragment;

import android.content.Context;

import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.util.VolleyLog;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

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
    public void checkHasFriend(final Context context) {
        BmobNetUtils.queryHasFriend(context.getApplicationContext(), new SQLQueryListener<Friend>() {
            @Override
            public void done(BmobQueryResult<Friend> bmobQueryResult, BmobException e) {
                if (e != null) {
                    iHomeView.onFailure(Constants.other_code, e.getMessage());
                    return;
                }
                if (bmobQueryResult != null) {
                    //获取当前用户
                    BmobUser currentUser = User.getCurrentUser(context.getApplicationContext());
                    String userId = currentUser.getObjectId();
                    //只能存在一个好友
                    List<Friend> results = bmobQueryResult.getResults();
                    //获取朋友
                    for (Friend friend : results) {
                        if (userId.equals(friend.getCurrentUserId())) {
                            iHomeView.hasFriend(friend);
                            break;
                        }
                    }
                } else {
                    iHomeView.noFriend();
                }
            }
        });
    }
}
