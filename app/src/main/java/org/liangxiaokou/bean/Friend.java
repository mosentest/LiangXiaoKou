package org.liangxiaokou.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/4/22.
 */
public class Friend extends BmobObject {
    private String currentUserId;//存储当前用户id
    private String friendUserId;//存储爱人的用户id
    private Integer isLove;//是否还是恋爱（0：是，1不是）

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

    public Integer getIsLove() {
        return isLove;
    }

    public void setIsLove(Integer isLove) {
        this.isLove = isLove;
    }
}
