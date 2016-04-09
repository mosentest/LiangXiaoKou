package org.liangxiaokou.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by moziqi on 16-4-9.
 */
public class User extends BmobUser {
    private Boolean sex;//性别(0男，1女)
    private String nick;//别名
    private String bitch;//出生日期

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getBitch() {
        return bitch;
    }

    public void setBitch(String bitch) {
        this.bitch = bitch;
    }
}
