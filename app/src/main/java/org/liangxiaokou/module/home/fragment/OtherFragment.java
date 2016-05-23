package org.liangxiaokou.module.home.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yalantis.starwars.Const;

import org.json.JSONException;
import org.json.JSONObject;
import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.LoveDate;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobIMNetUtils;
import org.liangxiaokou.bmob.BmobRealTimeDataUtils;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.chat.ChatActivity;
import org.liangxiaokou.module.contact.ContactActivity;
import org.liangxiaokou.module.invite.InviteActivity;
import org.liangxiaokou.module.setlovedate.SetLoveDateActivity;
import org.liangxiaokou.module.sleep.SleepActivity;
import org.liangxiaokou.module.timer.TimerActivity;
import org.liangxiaokou.util.AESUtils;
import org.liangxiaokou.util.DateUtils;
import org.liangxiaokou.util.PhotoUtils;
import org.liangxiaokou.util.ToastUtils;
import org.liangxiaokou.util.VolleyLog;
import org.liangxiaokou.widget.dialog.listener.OnOperItemClickL;
import org.liangxiaokou.widget.dialog.widget.NormalListDialog;
import org.liangxiaokou.widget.view.CircleImageView;
import org.liangxiaokou.widget.view.RedTipImageView;
import org.mo.glide.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ValueEventListener;


/**
 * http://www.2cto.com/kf/201506/406053.html
 * <p/>
 */
public class OtherFragment extends GeneralFragment implements IOtherView {

    public final static int update_Love_Date = 0x01;//更新恋爱日

    public final static int change_phone = 0x02;//更换纪念照


    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvOtherName;

    private TextView tvOtherMood;
    private TextView tvOtherLoveDate;
    private ImageView ivOtherPhoto;
    private ImageView ivOtherCamera;
    private CircleImageView ivOtherHeader;
    private RedTipImageView ivOtherSleep;

    private RedTipImageView ivOtherTimer;

    private RedTipImageView ivOtherContact;
    private RelativeLayout rlOtherExist;
    private LinearLayout llOtherSleep;
    private LinearLayout llOtherTimer;

    private LinearLayout llOtherContact;
    private NormalListDialog photoDialog;//拍照类型
    private BmobIMUserInfo bmobIMFriendUserInfo;

    private OtherPresenter otherPresenter = new OtherPresenter(this);

    public static class StaticHandler extends Handler {
        private final WeakReference<Fragment> weakReference;

        public StaticHandler(Fragment fragment) {
            this.weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            OtherFragment fragment = (OtherFragment) weakReference.get();
            if (fragment != null) {

                switch (msg.what) {
                    case Constants.REQUEST_CODE_PICK_IMAGE:
                        Uri uri = (Uri) msg.obj;
                        fragment.startImageZoom(PhotoUtils.convertUri(fragment.getActivity(), uri, PhotoUtils.getImageFile(Constants.SAVE_IMAGE_DIR_PATH, Constants.OTHER_FRAGMENT_PHONE)));
                        break;
                    case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                        Uri uri_camera = (Uri) msg.obj;
                        fragment.startImageZoom(uri_camera);
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }

    private static StaticHandler mHandler;


    public OtherFragment() {
    }

    public static OtherFragment getInstance(String title) {
        OtherFragment instance = new OtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        instance.setArguments(bundle);
        return instance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_other;
    }

    @Override
    protected void initViewsAndEvents(View view) {
//shimmerContent = (ShimmerFrameLayout) findViewById(R.id.shimmerContent);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
        tvOtherLoveDate = (TextView) view.findViewById(R.id.tv_other_love_date);
        tvOtherName = (TextView) view.findViewById(R.id.tv_other_name);
        tvOtherMood = (TextView) view.findViewById(R.id.tv_other_mood);
        //animTextView = (AnimTextView) findViewById(R.id.atv_day);
        ivOtherPhoto = (ImageView) view.findViewById(R.id.iv_other_photo);
        ivOtherCamera = (ImageView) view.findViewById(R.id.iv_other_camera);
        ivOtherHeader = (CircleImageView) view.findViewById(R.id.iv_other_header);

        rlOtherExist = (RelativeLayout) view.findViewById(R.id.rl_other_exist);

        llOtherSleep = (LinearLayout) view.findViewById(R.id.ll_other_sleep);
        llOtherTimer = (LinearLayout) view.findViewById(R.id.ll_other_timer);
        llOtherContact = (LinearLayout) view.findViewById(R.id.ll_other_contact);

        ivOtherSleep = (RedTipImageView) view.findViewById(R.id.iv_other_sleep);
        ivOtherTimer = (RedTipImageView) view.findViewById(R.id.iv_other_timer);
        ivOtherContact = (RedTipImageView) view.findViewById(R.id.iv_other_contact);

        swipeRefreshLayout.setRefreshing(false);
//        String sDate = "2015-07-14";
//        tvOtherLoveDate.setText("已恋爱" + DateUtils.getDaySub(sDate) + "天");
        BmobRealTimeDataUtils.getInstance().start(getActivity(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {

            }

            @Override
            public void onDataChange(JSONObject jsonObject) {

            }
        });
        tvOtherLoveDate.setOnClickListener(this);
        ivOtherCamera.setOnClickListener(this);
        rlOtherExist.setOnClickListener(this);
        llOtherSleep.setOnClickListener(this);
        llOtherTimer.setOnClickListener(this);
        llOtherContact.setOnClickListener(this);

        //设置红点
        ivOtherHeader.setTipVisibility(RedTipImageView.TipType.RED_TIP_VISIBLE);
        ivOtherSleep.setTipVisibility(RedTipImageView.TipType.RED_TIP_VISIBLE);
        ivOtherTimer.setTipVisibility(RedTipImageView.TipType.RED_TIP_VISIBLE);
        ivOtherContact.setTipVisibility(RedTipImageView.TipType.RED_TIP_VISIBLE);

        mHandler = new StaticHandler(this);

        otherPresenter.checkHasFriend(getActivity());

    }

    @Override
    protected void onFirstUserVisible() {
        otherPresenter.toListenerTable(getActivity());
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {

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
        otherPresenter.unListenerTable();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_other_love_date:
                startActivityForResult(SetLoveDateActivity.class, update_Love_Date);
                break;
            case R.id.iv_other_camera:
                photoDialog = new NormalListDialog(getContext(), new String[]{"拍照(建议横屏拍照，效果更好)", "相册"});
                photoDialog.titleBgColor(getContext().getResources().getColor(R.color.system_color));
                photoDialog.title("请选择");
                photoDialog.itemTextSize(16);
                photoDialog.itemPressColor(getContext().getResources().getColor(R.color.line));
                photoDialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(getImageByCamera, Constants.REQUEST_CODE_CAPTURE_CAMERA);
                                break;
                            case 1:
                                Intent pick_intent = new Intent(Intent.ACTION_GET_CONTENT);
                                pick_intent.addCategory(Intent.CATEGORY_OPENABLE);
                                pick_intent.setType("image/*");//相片类型
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    startActivityForResult(pick_intent, Constants.REQUEST_CODE_PICK_IMAGE_KITKAT);
                                } else {
                                    startActivityForResult(pick_intent, Constants.REQUEST_CODE_PICK_IMAGE);
                                }
                                break;
                            case 2:
                                break;
                        }
                        photoDialog.dismiss();
                    }
                });
                photoDialog.show();
                break;
            case R.id.rl_other_exist:
                if (bmobIMFriendUserInfo != null) {
                    BmobIMNetUtils.createConversation(bmobIMFriendUserInfo, new ConversationListener() {
                        @Override
                        public void done(BmobIMConversation bmobIMConversation, BmobException e) {
                            if (e == null) {
                                //在此跳转到聊天页面
                                Intent intent2 = new Intent(getActivity(), ChatActivity.class);
                                intent2.putExtra("OtherFragment_bmobIMConversation", bmobIMConversation);
                                intent2.putExtra("OtherFragment_bmobIMFriendUserInfo", bmobIMFriendUserInfo);
                                getActivity().startActivity(intent2);
                            } else {
                                ToastUtils.toast(getContext(), e.getMessage() + "(" + e.getErrorCode() + ")");
                            }
                        }
                    });
                }
                ivOtherHeader.setTipVisibility(RedTipImageView.TipType.RED_TIP_GONE);
                break;
            case R.id.ll_other_sleep:
                startActivity(SleepActivity.class);
                ivOtherSleep.setTipVisibility(RedTipImageView.TipType.RED_TIP_GONE);
                break;
            case R.id.ll_other_timer:
                startActivity(TimerActivity.class);
                ivOtherTimer.setTipVisibility(RedTipImageView.TipType.RED_TIP_GONE);
                break;
            case R.id.ll_other_contact:
                startActivity(ContactActivity.class);
                ivOtherContact.setTipVisibility(RedTipImageView.TipType.RED_TIP_GONE);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        Message message = null;
        switch (requestCode) {
            case Constants.REQUEST_CODE_PICK_IMAGE:
            case Constants.REQUEST_CODE_PICK_IMAGE_KITKAT:
                //相册
                if (data == null) {
                    return;
                }
                alertDialog.show();
                message = new Message();
                message.what = Constants.REQUEST_CODE_PICK_IMAGE;
                Uri uri = data.getData();
                message.obj = uri;
                mHandler.sendMessage(message);
                break;
            case Constants.REQUEST_CODE_CAPTURE_CAMERA:
                //拍照
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        alertDialog.show();
                        message = new Message();
                        message.what = Constants.REQUEST_CODE_CAPTURE_CAMERA;
                        Bitmap bm = extras.getParcelable("data");
                        message.obj = PhotoUtils.saveBitmap(bm, PhotoUtils.getImageFile(Constants.SAVE_IMAGE_DIR_PATH, Constants.OTHER_FRAGMENT_PHONE));
                        mHandler.sendMessage(message);
                    }
                }
                break;
            case Constants.CROP_REQUEST_CODE:
                alertDialog.hide();
                if (data == null) {
                    return;
                }//剪裁后的图片
                Bundle extras = data.getExtras();
                if (extras == null) {
                    return;
                }
                Bitmap bm = extras.getParcelable("data");
                ivOtherPhoto.setImageBitmap(bm);
                break;
        }
    }


    /**
     * 剪裁图片
     *
     * @param uri
     */
    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否裁剪
        intent.putExtra("crop", "true");
        //aspect 图片的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 2);
        //output 图片的大小
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 400);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constants.CROP_REQUEST_CODE);
    }

    @Override
    public void noFriend() {
        startActivity(InviteActivity.class);
    }

    @Override
    public void hasFriend(Friend friend) {
        bmobIMFriendUserInfo = new BmobIMUserInfo(new Long(0), friend.getFriendUserId(), friend.getFriendName(), "");
        User currentUser = User.getCurrentUser(getContext(), User.class);
        ImageUtils.loadImgResourceId(getContext(), ivOtherHeader, currentUser.getSex() != 0 ? R.mipmap.boy : R.mipmap.gril);
        tvOtherName.setText(friend.getFriendName());
    }

    @Override
    public void onSuccess(LoveDate loveDate) {
        tvOtherLoveDate.setText("已恋爱" + DateUtils.getDaySub(AESUtils.getDecryptString(loveDate.getLoveDate())) + "天");
    }

    @Override
    public String getTableName() {
        return "LoveDate";
    }

    @Override
    public String getTableNameObjectId() {
        User currentUser = User.getCurrentUser(getActivity(), User.class);
        if (currentUser != null) {
            return currentUser.getLoveDateObjectId();
        }
        return null;
    }

    @Override
    public String toListenerData(JSONObject jsonObject) {
        VolleyLog.e("%s", jsonObject);
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            data.getString("loveDate");
            //String sDate = "2015-07-14";
            tvOtherLoveDate.setText("已恋爱" + DateUtils.getDaySub(AESUtils.getDecryptString(data.getString("loveDate"))) + "天");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(OtherFragment.class.getName() + " code is " + code + " and msg is " + msg);
    }
}
