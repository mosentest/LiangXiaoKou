package org.liangxiaokou.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/12/17.
 */
public class Album extends BmobObject {
    private String username;//用户名称
    private String content;//内容
    private String address;//地址
    private List<Byte[]> photos;//照片，最多4张
    private String date;//日期
    private Integer type;//类型，表示男女

    private Integer ResImg;//表示左边显示的图
    private Integer ResColor;//左边显示的颜色


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Byte[]> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Byte[]> photos) {
        this.photos = photos;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getResImg() {
        return ResImg;
    }

    public void setResImg(Integer resImg) {
        ResImg = resImg;
    }

    public Integer getResColor() {
        return ResColor;
    }

    public void setResColor(Integer resColor) {
        ResColor = resColor;
    }
}
