package org.liangxiaokou.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by moziqi on 16-4-9.
 */
public class User extends BmobUser {
    private int sex;//性别(0男，1女)
    private String nick;//别名

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

}
