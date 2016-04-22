package org.liangxiaokou.module.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.module.R;
import org.liangxiaokou.util.VolleyLog;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends GeneralFragment {

    private BmobIMConversation conversation;

    public ChatActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = getActivity().getIntent();
        BmobIMConversation bmobIMConversation = (BmobIMConversation) intent.getSerializableExtra("OtherFragment_bmobIMConversation");
        //在聊天页面的onCreate方法中，通过如下方法创建新的会话实例
        conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), bmobIMConversation);
        BmobIMTextMessage bmobIMMessage = new BmobIMTextMessage();
        //oppo
//        bmobIMMessage.setFromId("021514db73");
//        bmobIMMessage.setToId("d84d4f1c3e");
        //huawei
//        bmobIMMessage.setFromId("d84d4f1c3e");
//        bmobIMMessage.setToId("021514db73");
        bmobIMMessage.setContent("mo");
        conversation.sendMessage(bmobIMMessage, new MessageSendListener() {

            @Override
            public void onStart(BmobIMMessage bmobIMMessage) {
                super.onStart(bmobIMMessage);
            }

            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                VolleyLog.e("%s", bmobIMMessage.getBmobIMUserInfo().getName());
            }
        });
    }

    @Override
    protected void PreOnStart() {

    }

    @Override
    protected void PreOnResume() {

    }

    @Override
    protected void PreOnPause() {

    }

    @Override
    protected void PreOnStop() {

    }

    @Override
    protected void PreOnDestroy() {

    }

    @Override
    public void onClick(View v) {

    }
}
