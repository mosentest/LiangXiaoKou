package org.liangxiaokou.module.invite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.module.QRcode.QRcodeActivity;
import org.liangxiaokou.module.QRcode.ScannerActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.databinding.ActivityInviteBinding;
import org.liangxiaokou.widget.dialog.listener.OnOperItemClickL;
import org.liangxiaokou.widget.dialog.widget.NormalListDialog;


public class InviteActivity extends ToolBarActivity {


    private RelativeLayout mRlAdd;
    private TextView mTvCurrentUserName;


    private NormalListDialog friendDialog;//扫一扫，还是显示二维码

    ActivityInviteBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_invite);
        setContentView(dataBinding.getRoot());
    }

    public void showMyName(View view) {
        User currentUser = User.getCurrentUser(getApplicationContext(), User.class);
        dataBinding.setUser(currentUser);
    }

    public void initFriendDialog() {
        //设置添加好友的对话框
        friendDialog = new NormalListDialog(this, new String[]{"扫一扫", "我的二维码"});
        friendDialog.titleBgColor(getApplicationContext().getResources().getColor(R.color.system_color));
        friendDialog.itemPressColor(getApplicationContext().getResources().getColor(R.color.line));
        friendDialog.title("请选择");
        friendDialog.itemTextSize(16);
        friendDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        //跳转扫一扫界面
                        startActivity(ScannerActivity.class);
                        friendDialog.dismiss();
                        break;
                    case 1:
                        startActivity(QRcodeActivity.class);
                        friendDialog.dismiss();
                        //我的二维码
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        //mTvCurrentUserName = (TextView) findViewById(R.id.tv_currentUserName);
        mRlAdd = (RelativeLayout) findViewById(R.id.rl_add);
    }

    @Override
    public void initData() {
        initFriendDialog();
        User currentUser = User.getCurrentUser(getApplicationContext(), User.class);
        //mTvCurrentUserName.setText(currentUser.getNick());
        mRlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!friendDialog.isShowing()) {
                    friendDialog.show();
                }
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
    }

    @Override
    public boolean PreOnKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }


}
