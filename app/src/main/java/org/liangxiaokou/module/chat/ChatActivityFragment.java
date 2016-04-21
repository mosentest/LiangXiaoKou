package org.liangxiaokou.module.chat;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobIMNetUtils;
import org.liangxiaokou.module.R;
import org.liangxiaokou.util.AppUtils;
import org.liangxiaokou.util.VolleyLog;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.exception.BmobException;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends GeneralFragment {

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
        //VolleyLog.e("%s", bmobIMConversation.getConversationId());
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
