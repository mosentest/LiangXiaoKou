package org.liangxiaokou.module.home;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.liangxiaokou.module.chat.ChatActivity;
import org.liangxiaokou.module.contact.ContactActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.setlovedate.SetLoveDateActivity;
import org.liangxiaokou.module.sleep.SleepActivity;
import org.liangxiaokou.module.timer.TimerActivity;
import org.liangxiaokou.widget.dialog.listener.OnOperItemClickL;
import org.liangxiaokou.widget.dialog.widget.NormalListDialog;
import org.liangxiaokou.widget.view.CircleImageView;
import org.liangxiaokou.widget.view.RedTipImageView;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.util.DateUtils;
import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.util.FileUtils;
import org.liangxiaokou.util.LogUtils;
import org.liangxiaokou.util.SDCardUtils;

import java.io.File;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends GeneralFragment {

    public final static int update_Love_Date = 0x01;//更新恋爱日
    public final static int change_phone = 0x02;//更换纪念照

    public final static int REQUEST_CODE_PICK_IMAGE = 0x03;//从相册中选图片

    public final static int REQUEST_CODE_CAPTURE_CAMERA = 0x04;//拍照

    private String capturePath = null;

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
    private NormalListDialog listDialog;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false);
    }


    @Override
    protected void initView() {
        //shimmerContent = (ShimmerFrameLayout) findViewById(R.id.shimmerContent);
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
    protected void initData() {
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
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_other_love_date:
                startActivityForResult(SetLoveDateActivity.class, update_Love_Date);
                break;
            case R.id.iv_other_camera:
                listDialog = new NormalListDialog(getContext(), new String[]{"拍照", "相册", "仿微信方式"});
                listDialog.titleBgColor(getContext().getResources().getColor(R.color.system_color));
                listDialog.title("请选择");
                listDialog.itemTextSize(16);
                listDialog.itemPressColor(getContext().getResources().getColor(R.color.line));
                listDialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                String state = Environment.getExternalStorageState();
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    StringBuilder out_file_path = new StringBuilder();
                                    out_file_path.append(SDCardUtils.getSDCardPath());
                                    out_file_path.append(Constants.APP_NAME);
                                    out_file_path.append(Constants.SAVE_IMAGE_DIR_PATH);
                                    FileUtils.makeDirs(out_file_path.toString());
                                    capturePath = System.currentTimeMillis() + ".jpg";
                                    getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(out_file_path.toString(), capturePath)));
                                    //getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                                    startActivityForResult(getImageByCamera, REQUEST_CODE_CAPTURE_CAMERA);
                                } else {
                                    Toast.makeText(getContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 1:
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");//相片类型
                                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                                break;
                            case 2:
                                startActivityForResult(MultiImageSelectorActivity.class, change_phone);
                                break;
                        }
                        listDialog.dismiss();
                    }
                });
                listDialog.show();
                break;
            case R.id.rl_other_exist:
                startActivity(ChatActivity.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:
                break;
            case REQUEST_CODE_CAPTURE_CAMERA:
                LogUtils.e(OtherFragment.class.getSimpleName(), "resultCode:" + resultCode + "-" + (data == null ? null : data));
                break;
        }
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == 0 && null != data) {
            Uri uri = data.getData();
            LogUtils.e("uri1", uri + "");
            //to do find the path of pic
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMERA) {
            Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    LogUtils.e("uri2", uri + "jin this???");
                    //spath :生成图片取个名字和路径包含类型
                    //saveImage(Bitmap photo, String spath);
                } else {
                    //Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                //to do find the path of pic by uri
            }
            LogUtils.e("uri2", uri + "");
        }
    }
}
