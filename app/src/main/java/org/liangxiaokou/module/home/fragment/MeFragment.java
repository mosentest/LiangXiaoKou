package org.liangxiaokou.module.home.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.liangxiaokou.bean.User;
import org.liangxiaokou.module.R;
import org.liangxiaokou.app.GeneralFragment;
import org.liangxiaokou.module.person.PersonActivity;
import org.liangxiaokou.widget.view.CircleImageView;
import org.mo.glide.ImageUtils;

import cn.bmob.v3.BmobUser;


public class MeFragment extends GeneralFragment {

    private RelativeLayout mRlOtherExist;
    private CircleImageView mIvOtherHeader;
    private TextView mTvOtherName;
    private TextView mTvOtherMood;
    private RelativeLayout mRlDoc;
    private RelativeLayout mRlCalc;
    private RelativeLayout mRlShopping;
    private RelativeLayout mRlMusic;
    private RelativeLayout mRlGame;
    private RelativeLayout mRlSetting;


    public MeFragment() {
    }

    public static MeFragment getInstance(String title) {
        MeFragment instance = new MeFragment();
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
    public void initView() {
        mRlOtherExist = (RelativeLayout) findViewById(R.id.rl_other_exist);
        mIvOtherHeader = (CircleImageView) findViewById(R.id.iv_other_header);
        mTvOtherName = (TextView) findViewById(R.id.tv_other_name);
        mTvOtherMood = (TextView) findViewById(R.id.tv_other_mood);
        mRlDoc = (RelativeLayout) findViewById(R.id.rl_doc);
        mRlCalc = (RelativeLayout) findViewById(R.id.rl_calc);
        mRlShopping = (RelativeLayout) findViewById(R.id.rl_shopping);
        mRlMusic = (RelativeLayout) findViewById(R.id.rl_music);
        mRlGame = (RelativeLayout) findViewById(R.id.rl_game);
        mRlSetting = (RelativeLayout) findViewById(R.id.rl_setting);
    }

    @Override
    public void initData() {
        mRlOtherExist.setOnClickListener(this);
        User currentUser = BmobUser.getCurrentUser(getActivity().getApplicationContext(), User.class);
        if (currentUser != null) {
            mTvOtherName.setText(TextUtils.isEmpty(currentUser.getNick()) ? getString(R.string.app_name) : currentUser.getNick() + "");
            mTvOtherMood.setText("账号:" + currentUser.getUsername());
            ImageUtils.loadImgResourceId(getContext(), mIvOtherHeader, currentUser.getSex() == 0 ? R.mipmap.boy : R.mipmap.gril);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_other_exist: {
                startActivity(PersonActivity.class);
            }
            break;
        }
    }
}
