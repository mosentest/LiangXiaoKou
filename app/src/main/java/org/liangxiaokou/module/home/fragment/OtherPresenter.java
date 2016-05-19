package org.liangxiaokou.module.home.fragment;

import android.content.Context;

import org.json.JSONObject;
import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.LoveDate;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.bmob.BmobRealTimeDataUtils;
import org.liangxiaokou.config.Constants;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * Created by Administrator on 2016/4/23.
 */
public class OtherPresenter {
    private IOtherView otherView;

    public OtherPresenter(IOtherView otherView) {
        this.otherView = otherView;
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
                    otherView.onFailure(Constants.other_code, e.getMessage());
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
                            otherView.hasFriend(friend);
                            break;
                        }
                    }
                } else {
                    otherView.noFriend();
                }
            }
        });
        BmobNetUtils.queryLove(context, new GetListener<LoveDate>() {
            @Override
            public void onSuccess(LoveDate loveDate) {
                if (loveDate != null && !"".equals(loveDate.getLoveDate())) {
                    otherView.onSuccess(loveDate);
                }
            }

            @Override
            public void onFailure(int i, String s) {
                otherView.onFailure(i, s);
            }
        });
    }

    public void toListenerTable(final Context context) {
        BmobRealTimeDataUtils.getInstance().start(context, new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                if (BmobRealTimeDataUtils.getInstance().isConnected()) {
                    // 监听表更新
                    BmobRealTimeDataUtils.getInstance().subRowUpdate(otherView.getTableName(), otherView.getTableNameObjectId());
                }
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                otherView.toListenerData(jsonObject);
            }
        });
    }

    public void unListenerTable() {
        BmobRealTimeDataUtils.getInstance().unsubRowUpdate(otherView.getTableName(), otherView.getTableNameObjectId());
    }
}
