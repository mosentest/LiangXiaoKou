package org.liangxiaokou.module.album;

/**
 * Created by Administrator on 2016/5/20.
 */
public class Event {
    private int code;
    private String eventmsg;
    private boolean isSuccess;

    public Event(String event) {
        this.isSuccess = true;
        this.eventmsg = event;
    }

    public Event(int code, String eventmsg, boolean isSuccess) {
        this.code = code;
        this.eventmsg = eventmsg;
        this.isSuccess = isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getEventmsg() {
        return eventmsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
