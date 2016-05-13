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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.liangxiaokou.app.BackHandledFragment;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.module.R;
import org.liangxiaokou.util.AESUtils;
import org.liangxiaokou.util.ToastUtils;
import org.liangxiaokou.util.VolleyLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.listener.MessagesQueryListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatActivityFragment extends BackHandledFragment implements OnOperationListener {

    private BmobIMConversation conversation;

    private ListView messageListview;
    private MessageInputToolBox messageInputToolBox;
    private MessageAdapter adapter;
    private BmobIMConversation bmobIMConversation;
    private BmobIMUserInfo bmobIMFriendUserInfo;
    private User currentUser;

    private final static String FACE_TAG = Constants.CHAT_FACE + Constants.APP_NAME + Constants.CHAT_FACE;//用于标识是否是表情

    private final static int LIMIT = 30;


    public ChatActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
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
        messageInputToolBox.setOnOperationListener(this);
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
        messageListview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                messageInputToolBox.hide();
                return false;
            }
        });

        currentUser = User.getCurrentUser(getContext(), User.class);

        Intent intent = getActivity().getIntent();
        bmobIMConversation = (BmobIMConversation) intent.getSerializableExtra("OtherFragment_bmobIMConversation");
        bmobIMFriendUserInfo = (BmobIMUserInfo) intent.getSerializableExtra("OtherFragment_bmobIMFriendUserInfo");
        //在聊天页面的onCreate方法中，通过如下方法创建新的会话实例
        conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), bmobIMConversation);
        conversation.queryMessages(null, LIMIT, new MessagesQueryListener() {
            @Override
            public void done(List<BmobIMMessage> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        ArrayList<Message> messageArrayList = new ArrayList<>();
                        for (BmobIMMessage bmobIMMessage : list) {
                            Message message = BmobIMMessage2Message(bmobIMMessage);
                            messageArrayList.add(message);
                        }
                        adapter.getData().addAll(messageArrayList);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    ToastUtils.toast(getContext(), e.getMessage());
                }

            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Message message = BmobIMMessage2Message(event.getMessage());
        adapter.getData().add(message);
        adapter.notifyDataSetChanged();
    }

    /**
     * 消息转换
     *
     * @param bmobIMMessage
     * @return
     */
    private Message BmobIMMessage2Message(BmobIMMessage bmobIMMessage) {
        String dateTime = "2016-05-09 17:19:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date = null;
        try {
            date = df.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int msgType = Message.MSG_TYPE_TEXT;
        String content = AESUtils.getDecryptString(bmobIMMessage.getContent());
        if (content.contains(FACE_TAG)) {
            msgType = Message.MSG_TYPE_FACE;
            content = content.split(Constants.CHAT_FACE)[content.split(Constants.CHAT_FACE).length - 1];
        } else {
            msgType = Message.MSG_TYPE_TEXT;
        }
        Message message = new Message(msgType,
                Message.MSG_STATE_SUCCESS,
                currentUser.getNick(),
                currentUser.getSex() == 0 ? R.mipmap.boy + "" : R.mipmap.gril + "",
                bmobIMFriendUserInfo.getName(),
                currentUser.getSex() != 0 ? R.mipmap.boy + "" : R.mipmap.gril + "",
                bmobIMMessage.getCreateTime() > date.getTime() ? content : bmobIMMessage.getContent(),
                bmobIMMessage.getSendStatus() != 4,
                bmobIMMessage.getSendStatus() != 3,
                new Date(bmobIMMessage.getCreateTime())
        );
        message.setId(bmobIMMessage.getId());
        return message;
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
        EventBus.getDefault().unregister(this);
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

    @Override
    public void send(final String content) {
        BmobIMTextMessage bmobIMMessage = new BmobIMTextMessage();
        try {
            bmobIMMessage.setContent(AESUtils.getEncryptString(content));
            conversation.sendMessage(bmobIMMessage, new MessageSendListener() {

                @Override
                public void onStart(BmobIMMessage bmobIMMessage) {
                    super.onStart(bmobIMMessage);
                    Message message = new Message(Message.MSG_TYPE_TEXT,
                            Message.MSG_STATE_SENDING,
                            "fromName",
                            currentUser.getSex() == 0 ? R.mipmap.boy + "" : R.mipmap.gril + "",
                            "toName",
                            currentUser.getSex() != 0 ? R.mipmap.boy + "" : R.mipmap.gril + "",
                            content,
                            true,
                            true,
                            new Date());
                    message.setId(bmobIMMessage.getId());
                    adapter.getData().add(message);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                    if (e == null) {
                        List<Message> data = adapter.getData();
                        for (Message message : data) {
                            //if (message.getId() == bmobIMMessage.getId()) {
                            //    VolleyLog.e("%s", "i'm coming here?");
                                message.setState(Message.MSG_STATE_SUCCESS);
                            //    break;
                            //}
                        }
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.toast(getContext(), e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectedFace(final String content) {
        BmobIMTextMessage bmobIMMessage = new BmobIMTextMessage();
        try {
            //设定表情的规则
            bmobIMMessage.setContent(AESUtils.getEncryptString(FACE_TAG + content));
            conversation.sendMessage(bmobIMMessage, new MessageSendListener() {

                @Override
                public void onStart(BmobIMMessage bmobIMMessage) {
                    super.onStart(bmobIMMessage);
                    Message message = new Message(Message.MSG_TYPE_FACE,
                            Message.MSG_STATE_SENDING,
                            "fromName",
                            currentUser.getSex() == 0 ? R.mipmap.boy + "" : R.mipmap.gril + "",
                            "toName",
                            currentUser.getSex() != 0 ? R.mipmap.boy + "" : R.mipmap.gril + "",
                            content,
                            true,
                            true,
                            new Date());
                    message.setId(bmobIMMessage.getId());
                    adapter.getData().add(message);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                    if (e == null) {
                        List<Message> data = adapter.getData();
                        for (Message message : data) {
                            //if (message.getId() == bmobIMMessage.getId()) {
                            //    VolleyLog.e("%s", "i'm coming here?");
                                message.setState(Message.MSG_STATE_SUCCESS);
                            //    break;
                            //}
                        }
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.toast(getContext(), e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectedFuncation(int index) {
        ToastUtils.toast(getContext(), "current is " + index);
    }
}
