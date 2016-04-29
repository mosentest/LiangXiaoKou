package org.liangxiaokou.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by moziqi on 16-4-9.
 */
public class User extends BmobUser {
    private Integer sex;//性别(0男，1女)
    private String nick;//别名
    private Boolean isOk;//是否完善个人信息
    private Boolean haveLove;//是否含有另一半

    public User() {
    }

    public User(Integer sex, String nick, Boolean isOk, Boolean haveLove) {
        this.sex = sex;
        this.nick = nick;
        this.isOk = isOk;
        this.haveLove = haveLove;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public Boolean getHaveLove() {
        return haveLove;
    }

    public void setHaveLove(Boolean haveLove) {
        this.haveLove = haveLove;
    }
}
