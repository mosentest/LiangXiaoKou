package org.liangxiaokou.module.invite;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.liangxiaokou.app.ToolBarActivity;
import org.liangxiaokou.bean.User;
import org.liangxiaokou.module.QRcode.QRcodeActivity;
import org.liangxiaokou.module.QRcode.ScannerActivity;
import org.liangxiaokou.module.R;
import org.liangxiaokou.module.login.LoginActivity;
import org.liangxiaokou.widget.dialog.listener.OnOperItemClickL;
import org.liangxiaokou.widget.dialog.widget.NormalListDialog;


public class InviteActivity extends ToolBarActivity implements InviteView {


    private RelativeLayout mRlAdd;
    private TextView mTvCurrentUserName;


    private NormalListDialog friendDialog;//扫一扫，还是显示二维码

    private InvitePresenter invitePresenter = new InvitePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
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
        mTvCurrentUserName = (TextView) findViewById(R.id.tv_currentUserName);
        mRlAdd = (RelativeLayout) findViewById(R.id.rl_add);
    }

    @Override
    public void initData() {
        initFriendDialog();
        User currentUser = User.getCurrentUser(getApplicationContext(), User.class);
        mTvCurrentUserName.setText(currentUser.getNick());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_invite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                invitePresenter.toLogout(getApplicationContext());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    public void showLoading() {
        alertDialog.show();
    }

    @Override
    public void hideLoading() {
        alertDialog.hide();
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void onSuccess(String logoutName) {
        startActivity(LoginActivity.class);
    }

    @Override
    public void onFailure(int code, String msg) {
        showToast(InviteActivity.class.getName() + " code is " + code + " and msg is " + msg);
    }
}
