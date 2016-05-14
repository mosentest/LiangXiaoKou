package org.liangxiaokou.bmob;

import android.content.Context;

import org.liangxiaokou.bean.User;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ConversationListener;

/**
 * Created by Administrator on 2016/4/21.
 */
public class BmobIMNetUtils {

    /**
     * 连接服务器
     *
     * @param userId
     * @param connectListener
     */
    public static void connect(String userId, ConnectListener connectListener) {
        BmobIM.connect(userId, connectListener);
    }

    public static void IMConnectStatusChangeListener(ConnectStatusChangeListener connectStatusChangeListener) {
        BmobIM.getInstance().setOnConnectStatusChangeListener(connectStatusChangeListener);
    }

    /**
     * 断开连接
     */
    public static void disConnect() {
        BmobIM.getInstance().disConnect();
    }

    /**
     * 创建会话
     *
     * @param bmobIMUserInfo       针对 对方（和xx聊天）
     * @param conversationListener
     */
    public static void createConversation(BmobIMUserInfo bmobIMUserInfo, ConversationListener conversationListener) {
        BmobIM.getInstance().startPrivateConversation(bmobIMUserInfo, conversationListener);
    }

    public static void clearAllConversation() {
        BmobIM.getInstance().clearAllConversation();
    }
}
