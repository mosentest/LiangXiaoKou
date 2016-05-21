package org.liangxiaokou.bean;

import org.liangxiaokou.util.AESUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2015/12/17.
 */
public class Album extends BmobObject {
    private String username;//用户名称
    private String content;//内容
    private String address;//地址
    private List<BmobFile> photos;//照片，最多4张
    private List<String> photosUrl;//照片，最多4张
    private String date;//日期
    private Integer type;//类型，表示男女

    private Integer ResImg;//表示左边显示的图
    private Integer ResColor;//左边显示的颜色

    private String loveDateObjectId;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return AESUtils.getDecryptString(content);
    }

    public void setContent(String content) {
        this.content = AESUtils.getEncryptString(content);
    }

    public String getAddress() {
        return AESUtils.getDecryptString(address);
    }

    public void setAddress(String address) {
        this.address = AESUtils.getEncryptString(address);
    }

    public List<BmobFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<BmobFile> photos) {
        this.photos = photos;
    }

    public List<String> getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(List<String> photosUrl) {
        this.photosUrl = photosUrl;
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


    public String getLoveDateObjectId() {
        return loveDateObjectId;
    }

    public void setLoveDateObjectId(String loveDateObjectId) {
        this.loveDateObjectId = loveDateObjectId;
    }
}
