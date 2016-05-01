package org.liangxiaokou.module.home.fragment;

import android.content.Context;

import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.config.Constants;

import java.util.List;

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
    public void checkHasFriend(Context context) {
        BmobNetUtils.queryHasFriend(context.getApplicationContext(), new SQLQueryListener<Friend>() {
            @Override
            public void done(BmobQueryResult<Friend> bmobQueryResult, BmobException e) {
                if (e != null) {
                    iHomeView.onFailure(Constants.other_code, e.getMessage());
                    return;
                }
                if (bmobQueryResult != null) {
                    iHomeView.hasFriend(bmobQueryResult.getResults().get(0));
                } else {
                    iHomeView.noFriend();
                }
            }
        });
    }
}
