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

import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.bean.Friend;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobIMNetUtils;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.chat.ChatActivity;
import org.liangxiaokou.module.contact.ContactActivity;
import org.liangxiaokou.module.invite.InviteActivity;
import org.liangxiaokou.module.setlovedate.SetLoveDateActivity;
import org.liangxiaokou.module.sleep.SleepActivity;
import org.liangxiaokou.module.timer.TimerActivity;
import org.liangxiaokou.util.DateUtils;
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


/**
 * http://www.2cto.com/kf/201506/406053.html
 * <p>
 */
public class OtherFragment extends GeneralFragment implements IOtherView {

    public final static int update_Love_Date = 0x01;//更新恋爱日

    public final static int change_phone = 0x02;//更换纪念照

    public final static int REQUEST_CODE_PICK_IMAGE = 0x03;//从相册中选图片

    public final static int REQUEST_CODE_PICK_IMAGE_KITKAT = 0x04;//从相册中选图片

    public final static int REQUEST_CODE_CAPTURE_CAMERA = 0x05;//拍照

    public final static int CROP_REQUEST_CODE = 0x06;

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
                    case REQUEST_CODE_PICK_IMAGE:
                        Uri uri = (Uri) msg.obj;
                        fragment.startImageZoom(fragment.convertUri(uri));
                        break;
                    case REQUEST_CODE_CAPTURE_CAMERA:
                        Uri uri_camera = (Uri) msg.obj;
                        fragment.startImageZoom(uri_camera);
                        break;
                }
            }
            super.handleMessage(msg);
        }
    }

    private static StaticHandler mHandler;

    //private ShimmerFrameLayout shimmerContent;

    //private AnimTextView animTextView;

    public OtherFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false);
    }


    @Override
    public void initView() {
        //shimmerContent = (ShimmerFrameLayout) findViewById(R.id.shimmerContent);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
        tvOtherLoveDate = findViewById(R.id.tv_other_love_date);
        tvOtherName = findViewById(R.id.tv_other_name);
        tvOtherMood = findViewById(R.id.tv_other_mood);
        //animTextView = (AnimTextView) findViewById(R.id.atv_day);
        ivOtherPhoto = findViewById(R.id.iv_other_photo);
        ivOtherCamera = findViewById(R.id.iv_other_camera);
        ivOtherHeader = findViewById(R.id.iv_other_header);

        rlOtherExist = findViewById(R.id.rl_other_exist);

        llOtherSleep = findViewById(R.id.ll_other_sleep);
        llOtherTimer = findViewById(R.id.ll_other_timer);
        llOtherContact = findViewById(R.id.ll_other_contact);

        ivOtherSleep = findViewById(R.id.iv_other_sleep);
        ivOtherTimer = findViewById(R.id.iv_other_timer);
        ivOtherContact = findViewById(R.id.iv_other_contact);

    }

    @Override
    public void initData() {
        //设置shimmer动画的时间间隔
        //shimmerContent.setDuration(5000);
        //设置shimmer动画重复类型
        //shimmerContent.setRepeatMode(ObjectAnimator.REVERSE);
        //设置shimmer闪光的倾斜角度
        //shimmerContent.setAngle(ShimmerFrameLayout.MaskAngle.CW_90);
        // 设置shimmer闪光的宽度
        //shimmerContent.setDropoff(0.5f);
        //设置shimmer闪光的形状
        //shimmerContent.setMaskShape(ShimmerFrameLayout.MaskShape.LINEAR);

        //animTextView.setText("162", true);
        swipeRefreshLayout.setRefreshing(false);
        String sDate = "2015-07-14";
        tvOtherLoveDate.setText("已恋爱" + DateUtils.getDaySub(sDate) + "天");
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

        otherPresenter.checkHasFriend(getContext());

        mHandler = new StaticHandler(this);
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
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_other_love_date:
                startActivityForResult(SetLoveDateActivity.class, update_Love_Date);
                break;
            case R.id.iv_other_camera:
                photoDialog = new NormalListDialog(getContext(), new String[]{"拍照", "相册"});
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
                                startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMERA);
                                break;
                            case 1:
                                Intent pick_intent = new Intent(Intent.ACTION_GET_CONTENT);
                                pick_intent.addCategory(Intent.CATEGORY_OPENABLE);
                                pick_intent.setType("image/*");//相片类型
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    startActivityForResult(pick_intent, REQUEST_CODE_PICK_IMAGE_KITKAT);
                                } else {
                                    startActivityForResult(pick_intent, REQUEST_CODE_PICK_IMAGE);
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
            case REQUEST_CODE_PICK_IMAGE:
            case REQUEST_CODE_PICK_IMAGE_KITKAT:
                //相册
                if (data == null) {
                    return;
                }
                alertDialog.show();
                message = new Message();
                message.what = REQUEST_CODE_PICK_IMAGE;
                Uri uri = data.getData();
                message.obj = uri;
                mHandler.sendMessage(message);
                break;
            case REQUEST_CODE_CAPTURE_CAMERA:
                //拍照
                if (data == null) {
                    return;
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        alertDialog.show();
                        message = new Message();
                        message.what = REQUEST_CODE_CAPTURE_CAMERA;
                        Bitmap bm = extras.getParcelable("data");
                        message.obj = saveBitmap(bm);
                        mHandler.sendMessage(message);
                    }
                }
                break;
            case CROP_REQUEST_CODE:
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
     * 获取图片的路径
     */
    private File getImageFile() {
        File tmpDir = new File(Environment.getExternalStorageDirectory(), Constants.SAVE_IMAGE_DIR_PATH);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        File outputImage = new File(tmpDir.getAbsolutePath() + File.separator + "other_home.png");
        if (outputImage.exists()) {
            outputImage.delete();
        }
        try {
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputImage;
    }

    /**
     * 转换uri
     *
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getActivity().getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 10;   //width，hight设为原来的十分一
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
            return saveBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }


    /**
     * 保存图片
     *
     * @param bm
     * @return
     */
    private Uri saveBitmap(Bitmap bm) {
        File imageFile = getImageFile();
        long time = System.currentTimeMillis();
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            VolleyLog.e("%s", "保存图片成功");
            return Uri.fromFile(imageFile);
        } catch (FileNotFoundException e) {
            VolleyLog.e("%s", e.getMessage());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            VolleyLog.e("%s", e.getMessage());
            return null;
        } finally {
            VolleyLog.e("%s", (System.currentTimeMillis() - time) + ".s");
        }
    }


    /**
     * 计算图片的缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩，返回bitmap用于显示
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置inJustDecodeBounds为true后，decodeFile并不分配空间，但可计算出原始图片的长度和宽度，即opts.width和opts.height
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        // 设置为true，画质更好一点，加载时间略长
        options.inPreferQualityOverSpeed = true;

        return BitmapFactory.decodeFile(filePath, options);
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
        startActivityForResult(intent, CROP_REQUEST_CODE);
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
