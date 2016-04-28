package org.liangxiaokou.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/4/22.
 */
public class Friend extends BmobObject {
    private String currentUserId;//存储当前用户id
    private String friendUserId;//存储好友的用户id
    private Integer isLove;//是否还是恋爱（0：是，1不是）
    private String friendName;
    private int status;//状态，1:用户1关注用户2,2:用户1用户2互相关注，等等...
    private String currentName;

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

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

}
