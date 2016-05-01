package org.liangxiaokou.module.home.fragment;

import org.liangxiaokou.app.IView;
import org.liangxiaokou.bean.Friend;

import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by Administrator on 2016/4/23.
 */
public interface IOtherView extends IView {
    public void noFriend();

    public void hasFriend(Friend friend);
}
