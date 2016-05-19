package org.liangxiaokou.module.home.fragment;

import org.json.JSONObject;
import org.liangxiaokou.app.IView;
import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.LoveDate;

import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by Administrator on 2016/4/23.
 */
public interface IOtherView extends IView {
    public void noFriend();

    public void hasFriend(Friend friend);

    public void onSuccess(LoveDate loveDate);

    public String getTableName();

    public String getTableNameObjectId();

    public String toListenerData(JSONObject jsonObject);
}
