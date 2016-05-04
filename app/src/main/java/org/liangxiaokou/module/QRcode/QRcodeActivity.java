package org.liangxiaokou.module.QRcode;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.liangxiaokou.app.SwipeBackActivity;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.bmob.BmobNetUtils;
import org.liangxiaokou.config.Constants;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.home.HomeActivity;
import org.liangxiaokou.util.VolleyLog;

import cn.bmob.v3.listener.UpdateListener;

public class QRcodeActivity extends SwipeBackActivity implements IQRcodeView {

    private ImageView mIv;


    private QRcodePresenter invitePresenter = new QRcodePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        showActionBarBack(true);
    }

    @Override
    public boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    public void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        User currentUser = User.getCurrentUser(getApplicationContext(), User.class);
        //设置二维码的参数
        StringBuilder urlBuilder = new StringBuilder()
                .append(Constants.author)
                .append("&")
                .append(Constants.author)
                .append("&")
                .append(currentUser.getObjectId())
                .append("&")
                .append(currentUser.getNick())
                .append("&")
                .append(currentUser.getSex());
        CreateQRImageUtils.createQRImage(urlBuilder.toString(), mIv);
    }

    @Override
    public void initData() {
        //实时监听Friend表
        invitePresenter.BmobRealTimeData(getApplicationContext());
    }

    @Override
    public void PreOnStart() {

    }

    @Override
    public void PreOnResume() {

    }

    @Override
    public void PreOnRestart() {

    }

    @Override
    public void PreOnPause() {

    }

    @Override
    public void PreOnStop() {

    }

    @Override
    public void PreOnDestroy() {
        invitePresenter.unsubTableDelete();
    }

    @Override
    public boolean PreOnKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public String getTableName() {
        return "Friend";
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        VolleyLog.e("%s", "(" + jsonObject.optString("action") + ")" + "数据：" + jsonObject);
        try {
            if (jsonObject.has("data")) {
                JSONObject resultData = jsonObject.getJSONObject("data");
                //获取朋友表的信息
                String currentUserId = "";
                String friendUserId = "";
                if (resultData.has("currentUserId") && resultData.has("friendUserId")) {
                    //使用data 解析。。。哎妈的
                    currentUserId = resultData.getString("currentUserId");
                    friendUserId = resultData.getString("friendUserId");
                    User currentUser = User.getCurrentUser(getApplicationContext(), User.class);
                    if (currentUser.getObjectId().equals(currentUserId) || currentUser.getObjectId().equals(friendUserId)) {
                        //检查朋友
                        if (resultData.has("isLove")) {
                            //使用data 解析。。。哎妈的
                            int isLove = resultData.getInt("isLove");
                            if (isLove == 0) {
                                User user = new User();
                                user.setHaveLove(true);
                                BmobNetUtils.updateUserInfo(getApplicationContext(), user, currentUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        startActivity(HomeActivity.class);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        showToast("current code is " + i + " and msg is " + s);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            VolleyLog.e("%s", e.getMessage());
            e.printStackTrace();
        }

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
        showToast("current code is " + code + " and msg is " + msg);
    }
}
