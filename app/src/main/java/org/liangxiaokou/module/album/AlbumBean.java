package org.liangxiaokou.module.album;

/**
 * Created by Administrator on 2016/5/17.
 */
public class AlbumBean {
    private String filePath;
    private boolean isPick;//标识+

    public AlbumBean() {
    }

    public AlbumBean(String filePath) {
        this(filePath, false);
    }

    public AlbumBean(String filePath, boolean isPick) {
        this.filePath = filePath;
        this.isPick = isPick;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }
}
