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
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mRlOtherExist = (RelativeLayout) view.findViewById(R.id.rl_other_exist);
        mIvOtherHeader = (CircleImageView) view.findViewById(R.id.iv_other_header);
        mTvOtherName = (TextView) view.findViewById(R.id.tv_other_name);
        mTvOtherMood = (TextView) view.findViewById(R.id.tv_other_mood);
        mRlDoc = (RelativeLayout) view.findViewById(R.id.rl_doc);
        mRlCalc = (RelativeLayout) view.findViewById(R.id.rl_calc);
        mRlShopping = (RelativeLayout) view.findViewById(R.id.rl_shopping);
        mRlMusic = (RelativeLayout) view.findViewById(R.id.rl_music);
        mRlGame = (RelativeLayout) view.findViewById(R.id.rl_game);
        mRlSetting = (RelativeLayout) view.findViewById(R.id.rl_setting);
        mRlOtherExist.setOnClickListener(this);
    }

    @Override
    protected void onFirstUserVisible() {
        User currentUser = BmobUser.getCurrentUser(getActivity().getApplicationContext(), User.class);
        if (currentUser != null) {
            mTvOtherName.setText(TextUtils.isEmpty(currentUser.getNick()) ? getString(R.string.app_name) : currentUser.getNick() + "");
            mTvOtherMood.setText("账号:" + currentUser.getUsername());
            ImageUtils.loadImgResourceId(getContext(), mIvOtherHeader, currentUser.getSex() == 0 ? R.mipmap.boy : R.mipmap.gril);
        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

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
