package org.liangxiaokou.module.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jialin.chat.Message;
import com.jialin.chat.MessageAdapter;
import com.jialin.chat.MessageInputToolBox;
import com.jialin.chat.OnOperationListener;
import com.jialin.chat.Option;

import org.liangxiaokou.app.BackHandledFragment;
import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.module.R;
import org.liangxiaokou.util.VolleyLog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends BackHandledFragment {

    private BmobIMConversation conversation;

    private ListView messageListview;
    private MessageInputToolBox messageInputToolBox;
    private MessageAdapter adapter;


    public ChatActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void initView() {
        messageListview = findViewById(R.id.messageListview);
        messageInputToolBox = findViewById(R.id.messageInputToolBox);
        //设置处理显示listview
        messageInputToolBox.setOnOperationListener(new OnOperationListener() {
            @Override
            public void send(String content) {
                Message message = new Message(0, 1, "Tom", "avatar", "Jerry", "avatar", content, true, true, new Date());
                adapter.getData().add(message);
                messageListview.setSelection(messageListview.getBottom());
            }

            @Override
            public void selectedFace(String content) {

            }

            @Override
            public void selectedFuncation(int index) {

            }
        });
        //设置表情
        ArrayList<String> faceNameList = new ArrayList<>();
        for (int x = 1; x <= 10; x++) {
            faceNameList.add("big" + x);
        }
        ArrayList<String> faceNameList1 = new ArrayList<>();
        for (int x = 1; x <= 7; x++) {
            faceNameList1.add("cig" + x);
        }
        ArrayList<String> faceNameList2 = new ArrayList<>();
        for (int x = 1; x <= 24; x++) {
            faceNameList2.add("dig" + x);
        }
        Map<Integer, ArrayList<String>> faceData = new HashMap<Integer, ArrayList<String>>();
        faceData.put(R.drawable.em_cate_magic, faceNameList2);
        faceData.put(R.drawable.em_cate_rib, faceNameList1);
        faceData.put(R.drawable.em_cate_duck, faceNameList);
        messageInputToolBox.setFaceData(faceData);
        //设置视频和图片
        List<Option> functionData = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            Option takePhotoOption = new Option(getContext(), "Take", R.drawable.take_photo);
            Option galleryOption = new Option(getContext(), "Gallery", R.drawable.gallery);
            functionData.add(galleryOption);
            functionData.add(takePhotoOption);
        }
        messageInputToolBox.setFunctionData(functionData);
    }

    @Override
    public void initData() {
        List<Message> messages = new ArrayList<Message>();
        adapter = new MessageAdapter(getContext(), messages);
        messageListview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        messageListview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                messageInputToolBox.hide();
                return false;
            }
        });
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
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {

    }

    @Override
    public void PreOnPause() {

    }

    @Override
    public void PreOnStop() {

    }

    @Override
    public void PreOnDestroy() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onBackPressed() {
        if (messageInputToolBox.isVisible()) {
            messageInputToolBox.hide();
            return false;
        }
        return true;
    }
}
